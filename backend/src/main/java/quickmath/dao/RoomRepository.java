package quickmath.dao;

import org.springframework.data.repository.ListCrudRepository;
import quickmath.model.Room;

import java.util.Optional;

public interface RoomRepository extends ListCrudRepository<Room, Long> {
    Optional<Room> findFirstByName(String name);
}
