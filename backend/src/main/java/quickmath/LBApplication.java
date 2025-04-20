package quickmath;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import quickmath.dao.CreatureRepository;
import quickmath.dao.MoveRepository;
import quickmath.model.Creature;
import quickmath.model.CreatureType;
import quickmath.model.Move;

@SpringBootApplication
public class LBApplication {
    public static void main(String[] args) {
        SpringApplication.run(LBApplication.class, args);
    }

    @Bean
    public CommandLineRunner autoPopulate(Config.MoveSet moveSet, MoveRepository moveRepository) {
        return args -> {
            for (Config.StatusSettings statusSettings : moveSet.statuses()) {
                Move status = new Move(statusSettings.id,
                        statusSettings.name,
                        statusSettings.description,
                        CreatureType.NORMAL,
                        0,
                        true,
                        statusSettings.heal,
                        0,
                        statusSettings.usrDefMul,
                        statusSettings.usrAtkMul,
                        statusSettings.othDefMul,
                        statusSettings.othAtkMul,
                        null,
                        null);
                moveRepository.save(status);
            }
            for (Config.MoveSettings moveSettings : moveSet.moves()) {
                Move userStatus = moveSettings.usrStatId != null ? moveRepository.getById(moveSettings.usrStatId) : null;
                Move otherStatus = moveSettings.othStatId != null ? moveRepository.getById(moveSettings.othStatId) : null;
                Move move = new Move(moveSettings.id,
                        moveSettings.name,
                        moveSettings.description,
                        moveSettings.type,
                        moveSettings.difficulty,
                        false,
                        moveSettings.heal,
                        moveSettings.damage,
                        moveSettings.usrDefMul,
                        moveSettings.usrAtkMul,
                        moveSettings.othDefMul,
                        moveSettings.othAtkMul,
                        userStatus,
                        otherStatus);
                moveRepository.save(move);
            }
        };
    }
}
