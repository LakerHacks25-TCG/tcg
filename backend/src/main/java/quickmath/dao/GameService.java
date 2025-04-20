package quickmath.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quickmath.Config;
import quickmath.model.*;

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
    void applyStatus(Move status, Creature creature) {
        var cs = creatureStatusRepository.getByIds(creature.id, status.id).orElse(new CreatureStatus(creature, status));
        cs.stacks++;
        creatureStatusRepository.save(cs);
    }

    @Transactional
    void doMove(Move move, Creature user, Creature other, float multiplier) {
        move.doMove(user, other, multiplier);
        if (move.userStatusEffect != null)
            applyStatus(move.userStatusEffect, user);
        if (move.otherStatusEffect != null)
            applyStatus(move.otherStatusEffect, other);
        creatureRepository.save(user);
        creatureRepository.save(other);
    }

    @Transactional
    public TurnGameStateDTO doTurn(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        Player player1 = room.player1, player2 = room.player2;
        assert player2 != null;

        Player first = player1.turnSpeed < player2.turnSpeed ? player1 : player2;
        Player second = player1.turnSpeed < player2.turnSpeed ? player2 : player1;
        Move firstMove = first.creature.moves.stream().filter(m -> m.id == first.turnMoveId).findFirst().orElseThrow();
        Move secondMove = second.creature.moves.stream().filter(m -> m.id == second.turnMoveId).findFirst().orElseThrow();

        doMove(firstMove, first.creature, second.creature, first.turnMultiplier);
        doMove(secondMove, second.creature, first.creature, second.turnMultiplier);
        for (var creatureStatus : second.creature.statuses)
            doMove(creatureStatus.status, second.creature, first.creature, second.turnMultiplier);
        for (var creatureStatus : first.creature.statuses)
            doMove(creatureStatus.status, first.creature, second.creature, first.turnMultiplier);
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
