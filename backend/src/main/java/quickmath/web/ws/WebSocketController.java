package quickmath.web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import quickmath.dao.PlayerRepository;
import quickmath.dao.RoomRepository;
import quickmath.dao.RoomService;
import quickmath.dao.GameService;
import quickmath.model.Player;
import quickmath.model.Room;

import java.security.Principal;
import java.util.Optional;

@Controller
public class WebSocketController {
    private final PlayerRepository playerRepository;
    private final RoomService roomService;
    private final GameService gameService;
    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketController(PlayerRepository playerRepository, RoomService roomService, GameService gameService, SimpMessagingTemplate template) {
        this.playerRepository = playerRepository;
        this.roomService = roomService;
        this.template = template;
        this.gameService = gameService;
    }

    @MessageMapping("/turn")
    public void takeTurn(@Payload Player.Turn turn, Principal user) {
        System.out.println("hello");
        System.out.println(user);
        Optional<Player> playerOpt = playerRepository.findByName(user.getName());
        if (playerOpt.isEmpty())
            return;
        System.out.println("player exists");
        Player player = playerOpt.get();
        player.setTurn(turn);
        player.ready = true;
        playerRepository.save(player);
        Room room = player.room;
        if (roomService.getBothPlayersReady(player.room.id)) {
            roomService.resetReady(player.room.id);
            var gameState = gameService.doTurn(room.id);
            assert room.player2 != null;
            template.convertAndSendToUser(room.player1.name, "/queue/turn-end", gameState);
            template.convertAndSendToUser(room.player2.name, "/queue/turn-end", GameService.TurnGameStateDTO.flipped(gameState));
        }
    }
}
