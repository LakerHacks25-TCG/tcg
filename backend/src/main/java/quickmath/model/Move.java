package quickmath.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.Pair;

@Entity
public class Move {
    @Id
    public Long id;
    public String name;
    public String description;
    public CreatureType type;
    public int difficultyLevel;
    public boolean isStatus;

    public int heal, baseDamage;
    float userDefenseMultiplier, userAttackMultiplier;
    float otherDefenseMultiplier, otherAttackMultiplier;

    @ManyToOne
    public @Nullable Move userStatusEffect;
    @ManyToOne
    public @Nullable Move otherStatusEffect;

    protected Move() {}
    public Move(Long id,
                String name,
                String description,
                CreatureType t,
                int difficulty,
                boolean isStatus,
                int heal,
                int dmg,
                float usrDefMul,
                float usrAtkMul,
                float othDefMul,
                float othAtkMul,
                @Nullable Move userStat,
                @Nullable Move otherStat) {
        this.id = id;
        this.name = name;
        this.description = description;
        type = t;
        difficultyLevel = difficulty;
        this.isStatus = isStatus;

        this.heal = heal;
        baseDamage = dmg;
        userAttackMultiplier = usrAtkMul;
        userDefenseMultiplier = usrDefMul;
        otherAttackMultiplier = othAtkMul;
        otherDefenseMultiplier = othDefMul;

        userStatusEffect = userStat;
        otherStatusEffect = otherStat;
    }

    public Pair<Creature, Creature> doMove(final Creature user, final Creature other, float multiplier) {
        user.setHp(user.getHp() + (int) (heal * multiplier));
        if (!isStatus) {
            // usrAtk / 100 * 75 / emyDef -> 4 / 3 * usrAtk / emyDef
            float damage = (int) (baseDamage * multiplier * 1.33f * user.getAttack() / other.getDefense());
            other.setHp(other.getHp() - (int)(damage * CreatureType.calculateAdvantage(other.type, type)));
            other.setAttack((int) (other.getAttack() * otherAttackMultiplier));
            other.setDefense((int) (other.getDefense() * otherDefenseMultiplier));
        }
        user.setAttack((int) (user.getAttack() * userAttackMultiplier));
        user.setDefense((int) (user.getDefense() * userDefenseMultiplier));
        return new Pair<>(user, other);
    }
}
