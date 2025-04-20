package quickmath.dao;

import org.springframework.data.repository.ListCrudRepository;
import quickmath.model.CreatureStatus;

public interface CreatureStatusRepository extends ListCrudRepository<CreatureStatus, CreatureStatus.CSId> {
}
