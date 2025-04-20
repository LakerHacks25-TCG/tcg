package quickmath.model;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;
    public long turnMoveId;
    public float turnSpeed;
    public float turnMultiplier;
    public boolean ready;

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room room;

    public void setTurn(Turn turn) {
        turnMoveId = turn.moveId;
        turnSpeed = turn.speed;
        turnMultiplier = turn.multiplier;
    }

    protected Player() {}
    public Player(String name) {
        this.name = name;
    }

    public record Turn(long moveId, float speed, float multiplier) {}
    public record StatusResponse(String username, String roomName) {
        public StatusResponse(Player player) {
            this(player.name, player.room != null ? player.room.name: null);
        }
    }
}
