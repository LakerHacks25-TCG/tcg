package quickmath.dao;

import org.springframework.data.repository.ListCrudRepository;
import quickmath.model.Creature;

public interface CreatureRepository extends ListCrudRepository<Creature, Long> {
}
