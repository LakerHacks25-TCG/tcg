package quickmath.dao;

import org.springframework.data.repository.CrudRepository;
import quickmath.model.Player;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
