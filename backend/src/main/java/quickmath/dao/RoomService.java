package quickmath.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quickmath.model.Room;

@Service
public class RoomService {
    final RoomRepository roomRepository;
    final PlayerRepository playerRepository;

    @Autowired
    RoomService(RoomRepository repository, PlayerRepository playerRepository) {
        this.roomRepository = repository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public boolean getBothPlayersReady(Long id) {
        Room room = roomRepository.findById(id).orElseThrow();
        return room.player2 != null && room.player1.ready && room.player2.ready;
    }

    @Transactional
    public void resetReady(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        room.player1.ready = false;
        assert room.player2 != null;
        room.player2.ready = false;
        playerRepository.save(room.player1);
        playerRepository.save(room.player2);
        roomRepository.save(room);
    }
}
