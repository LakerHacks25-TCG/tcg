//Record which stores a copy of two creatures
public record MoveResultRec(Creature user, Creature enemy) {
    public MoveResultRec {
        this(user.copy(), enemy.copy());
    }
}
