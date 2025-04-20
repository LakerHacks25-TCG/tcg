package quickmath.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@IdClass(CreatureStatus.CSId.class)
public class CreatureStatus {
    public static class CSId implements Serializable {
        public Long creatureId, statusId;
    }
    protected CreatureStatus() {}
    public CreatureStatus(Creature creature, Move statusEffect) {
        this.creature = creature;
        this.status = statusEffect;
    }

    @Id
    public Long creatureId;
    @Id
    public Long statusId;

    @ManyToOne
    @MapsId("creatureId")
    public Creature creature;

    @ManyToOne
    @MapsId("statusId")
    public Move status;

    public int stacks;
}
