package quickmath.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import quickmath.dao.PlayerRepository;
import quickmath.dao.RoomRepository;
import quickmath.dao.RoomService;
import quickmath.model.Player;
import quickmath.model.Room;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    final RoomRepository roomRepository;
    final RoomService roomService;
    final PlayerRepository playerRepository;
    final SimpMessagingTemplate messaging;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RoomController(RoomRepository roomRepository, RoomService roomService, PlayerRepository playerRepository, SimpMessagingTemplate template, SimpMessagingTemplate simpMessagingTemplate) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
        this.playerRepository = playerRepository;
        this.messaging = template;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    Player findOrCreatePlayer(String name) {
        return playerRepository.findByName(name).orElseGet(() -> {
            Player player = new Player(name);
            return playerRepository.save(player);
        });
    }

    @GetMapping("/current")
    public Player.StatusResponse getCurrentRoom(Principal user) {
        return new Player.StatusResponse(findOrCreatePlayer(user.getName()));
    }

    @PostMapping
    public void createRoom(@RequestParam("name") String roomName, Principal user) {
        Player player = findOrCreatePlayer(user.getName());
        player.room = roomRepository.save(new Room(roomName, findOrCreatePlayer(user.getName())));
        playerRepository.save(player);
    }

    @PostMapping("/{name}/join")
    public ResponseEntity<?> joinRoom(@PathVariable("name") String name, Principal user) {
        Optional<Room> roomOpt = roomRepository.findFirstByName(name);
        if (roomOpt.isEmpty()) {
            System.out.println();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Room room = roomOpt.get();
        if (room.player2 != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        room.player2 = findOrCreatePlayer(user.getName());
        room.player2.room = room;
        roomRepository.save(room);
        playerRepository.save(room.player2);
        return ResponseEntity.ok().build();
    }

    @PostMapping("start")
    public ResponseEntity<?> startBattle(Principal user) {
        Optional<Player> playerOpt = playerRepository.findByName(user.getName());
        if (playerOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        Player player = playerOpt.get();
        player.ready = true;
        playerRepository.save(player);
        Room room = player.room;
        if (roomService.getBothPlayersReady(room.id)) {
            assert room.player2 != null;
            simpMessagingTemplate.convertAndSendToUser(room.player1.name, "/queue/start", room.player2.name);
            simpMessagingTemplate.convertAndSendToUser(room.player2.name, "/queue/start", room.player1.name);
        }
        return ResponseEntity.ok().build();
    }
}
