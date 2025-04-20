//Record which stores a copy of two creatures
public class MoveResultRec {
    Creature user;
    Creature enemy;

    public MoveResultRec (Creature user, Creature enemy){
        this.user = user;
        this.enemy = enemy;
    }

    public Creature getEnemy() {
        return enemy;
    }

    public Creature getUser() {
        return user;
    }
}
