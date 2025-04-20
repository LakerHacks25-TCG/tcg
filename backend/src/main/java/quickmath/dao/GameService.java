package quickmath.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quickmath.Config;
import quickmath.model.Creature;
import quickmath.model.Move;
import quickmath.model.Player;
import quickmath.model.Room;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final RoomRepository roomRepository;
    CreatureRepository creatureRepository;
    CreatureStatusRepository creatureStatusRepository;
    MoveRepository moveRepository;
    Config.CreatureSet creatureSet;

    @Autowired
    public GameService(CreatureRepository creatureRepository, CreatureStatusRepository creatureStatusRepository, MoveRepository moveRepository, Config.CreatureSet creatureSet, RoomRepository roomRepository) {
        this.creatureRepository = creatureRepository;
        this.creatureStatusRepository = creatureStatusRepository;
        this.moveRepository = moveRepository;
        this.creatureSet = creatureSet;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public TurnGameStateDTO doTurn(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Player player1 = room.player1, player2 = room.player2;
        assert player2 != null;
        System.out.println("Player 1 used move " + player1.turnMoveId + " with speed " + player1.turnSpeed + " and modifier " + player1.turnMultiplier);
        System.out.println("Player 2 used move " + player2.turnMoveId + " with speed " + player2.turnSpeed + " and modifier " + player2.turnMultiplier);
        return new TurnGameStateDTO(new Creature.CreatureStateDTO(player1.creature), new Creature.CreatureStateDTO(player2.creature));
    }

    @Transactional
    public Creature createCreature(long creatureId) throws IllegalArgumentException {
        Config.CreatureSettings settings = creatureSet.creatures().stream().filter(c -> c.id() == creatureId).findAny().orElseThrow(IllegalArgumentException::new);
        Set<Move> moves = settings.moveIds().stream().map(id -> moveRepository.getById(id)).collect(Collectors.toSet());
        return creatureRepository.save(new Creature(settings, moves));
    }

    public record InitGameStateDTO(Creature.FullCreatureStateDTO me, String myName, Creature.FullCreatureStateDTO you, String yourName) {
        public static InitGameStateDTO flipped(InitGameStateDTO gameState) {
            return new InitGameStateDTO(gameState.you, gameState.yourName, gameState.me, gameState.myName);
        }
    }

    public record TurnGameStateDTO(Creature.CreatureStateDTO me, Creature.CreatureStateDTO you) {
        public static TurnGameStateDTO flipped(TurnGameStateDTO gameState) {
            return new TurnGameStateDTO(gameState.you, gameState.me);
        }
    }
}
