import java.util.ArrayList;
import java.util.Arrays;

//Represents a creature in the game
public class Creature {
    private static final int NUM_MOVES = 4;

    private CreatureType[] type;

    private int id;

    private String name;

    private Stats stats;

    //The list of the creature's status effects
    private final ArrayList<Move> statusEffect;

    Move[] moves;

    public Creature(CreatureType[] type, int id,
                    String name, Stats stats,
                    Move[] moves) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.statusEffect = new ArrayList<>();
        this.moves = moves;
    }

    public Creature(CreatureType[] type, int id,
                    String name, Stats stats,
                    ArrayList<Move> statusEffect,
                    Move[] moves){
        this.type = type;
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.statusEffect = statusEffect;
        this.moves = moves;
    }

    //Copy constructor
    public Creature(Creature other) {
        this.type = other.type;
        this.id = other.id;
        this.name = other.name;
        this.stats = new Stats(other.stats);
        this.statusEffect = other.statusEffect;
        this.moves = other.moves;

    }

    //TODO: implement
    public boolean isDead() {
        return false;
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

    public Move[] getMoves() {
        return moves;
    }
    public Move getMove(int index) {
        return moves[index];
    }

    public void addStatusEffect(Move status) {
        this.statusEffect.add(status);
    }

    public void multiplyDefense(float multiplier) {
        stats.setDefense((int) (stats.getDefense()*multiplier));
    }

    public void multiplyAttack(float multiplier) {
        stats.setAttack((int) (stats.getAttack()*multiplier));
    }

    public void applyDamage(int dmg) {
            stats.setHp(stats.getHp() - (int)(dmg));
    }


    @Override
    public String toString() {

        String[] names = new String[moves.length];
        int i = 0;
        for(Move move : moves) {
            names[i] = move.getName();
            i++;
        }

        return "Creature{" +
                ", moves=" + Arrays.toString(names) +
                ", statusEffect=" + statusEffect +
                ", stats=" + stats +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", type=" + Arrays.toString(type) +
                '}';
    }
}

