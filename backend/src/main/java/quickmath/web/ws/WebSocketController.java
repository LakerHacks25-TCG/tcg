package quickmath.web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import quickmath.dao.PlayerRepository;
import quickmath.dao.RoomService;
import quickmath.model.Player;
import quickmath.model.Room;

import java.security.Principal;
import java.util.Optional;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate template;
    private final PlayerRepository playerRepository;
    private final RoomService roomService;

    @Autowired
    WebSocketController(PlayerRepository playerRepository, RoomService roomService, SimpMessagingTemplate template) {
        this.playerRepository = playerRepository;
        this.roomService = roomService;
        this.template = template;
    }

    @MessageMapping("/turn")
    public void takeTurn(Player.Turn turn, Principal user) {
        Optional<Player> playerOpt = playerRepository.findByName(user.getName());
        if (playerOpt.isEmpty())
            return;
        Player player = playerOpt.get();
        player.setTurn(turn);
        Room room = player.room;
        if (roomService.getBothPlayersReady(player.room.id)) {
            roomService.resetReady(player.room.id);
            Player player1 = room.player1;
            Player player2 = room.player2;
            assert player2 != null;
            System.out.println("Player 1 used move " + player1.turnMoveId + " with speed " + player1.turnSpeed + " and modifier " + player1.turnMultiplier);
            System.out.println("Player 2 used move " + player2.turnMoveId + " with speed " + player2.turnSpeed + " and modifier " + player2.turnMultiplier);
        }
    }
}
