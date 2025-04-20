package quickmath.model;

import jakarta.persistence.*;
import quickmath.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Creature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    public CreatureType type;

    public int maxHp;
    private int hp, defense, attack;

    @OneToMany(mappedBy = "creature")
    public List<CreatureStatus> statuses = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "creature_moves",
            joinColumns = @JoinColumn(name = "move_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "creature_id", referencedColumnName = "id"))
    public Set<Move> moves;

    protected Creature() {}
    public Creature(String name, CreatureType t,int maxHp, int initialDefense, int initialAttack, Set<Move> moves) {
        this.name = name;
        type = t;
        this.maxHp = hp = maxHp;
        defense = initialDefense;
        attack = initialAttack;
        this.moves = moves;
    }

    public Creature(Config.CreatureSettings settings, Set<Move> moves) {
        this(settings.name(), settings.type(), settings.maxHp(), settings.defense(), settings.attack(), moves);
    }

    public int getHp() { return hp; }
    public int getDefense() { return defense; }
    public int getAttack() { return attack; }

    public void setHp(int newHp) { hp = Math.min(Math.max(newHp, 0), maxHp); }
    public void setDefense(int newDefense) { defense = Math.max(newDefense, 1); }
    public void setAttack(int newAttack) { attack = Math.max(newAttack, 1); }

    public record FullCreatureStateDTO(String name, CreatureType type, int maxHp, int defense, int attack, Set<Long> moveIds) {
        public FullCreatureStateDTO(Creature creature) {
            this(creature.name, creature.type, creature.maxHp, creature.defense, creature.attack, creature.moves.stream().map(move -> move.id).collect(Collectors.toSet()));
        }
    }
    public record CreatureStateDTO(int hp, int defense, int attack, List<Move> statuses) {
        public CreatureStateDTO(Creature creature) {
            this(creature.hp, creature.defense, creature.attack, creature.statuses.stream().map(cs -> cs.status).toList());
        }
    }
}
