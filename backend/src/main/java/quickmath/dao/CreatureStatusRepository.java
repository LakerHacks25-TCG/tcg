package quickmath.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import quickmath.model.CreatureStatus;

import java.util.Optional;

public interface CreatureStatusRepository extends ListCrudRepository<CreatureStatus, CreatureStatus.CSId> {
    @Query("select cs from CreatureStatus cs where cs.creatureId = :creatureId and cs.statusId = :statusId")
    Optional<CreatureStatus> getByIds(Long creatureId, Long statusId);
}
