import java.util.ArrayList;

//Represents a creature in the game
public class Creature {
    private static final int NUM_MOVES = 4;

    private CreatureType type;

    private int id;

    private String name;

    private Stats stats;

    //The list of the creature's status effects
    private final ArrayList<Move> statusEffect;

    Move[] moves;

    boolean isDead = true; //default true

    public Creature(CreatureType type, int id,
                    String name, Stats stats,
                    ArrayList<Move> statusEffect,
                    Move[] moves) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.statusEffect = new ArrayList<>();
        this.moves = moves;
    }

    public Creature(CreatureType type, int id,
                    String name, Stats stats,
                    ArrayList<Move> statusEffect,
                    Move[] moves,
                    boolean isDead) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.statusEffect = statusEffect;
        this.moves = moves;
        this.isDead = isDead;
    }

    //Copy constructor
    public Creature(Creature other) {
        this.type = other.type;
        this.id = other.id;
        this.name = other.name;
        this.stats = other.stats;
        this.statusEffect = other.statusEffect;
        this.moves = other.moves;
        this.isDead = other.isDead;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

