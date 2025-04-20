package quickmath;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import quickmath.model.CreatureType;

import java.io.IOException;
import java.util.List;

@Configuration
public class Config {
    public static final String FRONTEND_URL = "http://localhost:5173";
    public static final String MOVE_SET_JSON = "moves.json";
    public static final String CREATURES_JSON = "creatures.json";

    @Bean
    public MoveSet readMoveSet(ObjectMapper mapper) throws IOException {
        Resource resource = new ClassPathResource(MOVE_SET_JSON);
        return mapper.readValue(resource.getInputStream(), MoveSet.class);
    }

    @Bean
    public CreatureSet readCreatureSettings(ObjectMapper mapper) throws IOException {
        Resource resource = new ClassPathResource(CREATURES_JSON);
        return mapper.readValue(resource.getInputStream(), CreatureSet.class);
    }

    public record MoveSet(List<StatusSettings> statuses, List<MoveSettings> moves) {}
    public static class StatusSettings {
        public long id;
        public String name;
        public String description;
        public int heal;
        public float usrDefMul = 1;
        public float usrAtkMul = 1;
        public float othDefMul = 1;
        public float othAtkMul = 1;
    }
    public static class MoveSettings extends StatusSettings {
        public CreatureType type;
        public int difficulty;
        public int damage;
        public @Nullable Long usrStatId;
        public @Nullable Long othStatId;
    }
    public record CreatureSet(List<CreatureSettings> creatures) {}
    public record CreatureSettings(long id,
                            String name,
                            CreatureType type,
                            int maxHp,
                            int defense,
                            int attack,
                            List<Long> moveIds) {}
}
