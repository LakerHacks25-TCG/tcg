package quickmath.dao;

import org.springframework.data.repository.ListCrudRepository;
import quickmath.model.Move;

public interface MoveRepository extends ListCrudRepository<Move, Long> {
    Move getById(Long id);
}
