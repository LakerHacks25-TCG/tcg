package quickmath.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    @OneToOne
    @JoinColumn(name = "player1_id")
    public Player player1;

    @OneToOne
    @JoinColumn(name = "player2_id")
    public @Nullable Player player2;

    protected Room() {}

    public Room(String name, Player player1) {
        this.name = name;
        this.player1 = player1;
    }
}
