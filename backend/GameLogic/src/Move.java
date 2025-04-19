public abstract class Move {
    private String name;
    private final CreatureType type;
    private boolean isStatus;
    private int difficulty; //An integer which describes the difficulty of the move (1-4)
    private final int id;

    public Move(int id, String name, CreatureType type, boolean isStatus, int difficulty) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isStatus = isStatus;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public int getId() {
        return id;
    }
    public CreatureType getType() {
        return type;
    }
    public boolean isStatus() {
        return isStatus;
    }
    public void setStatus(boolean status) {
        isStatus = status;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Do move takes the user creature and the enemy creature and applies the move
    public abstract void doMove(Creature user, Creature enemy);



}
