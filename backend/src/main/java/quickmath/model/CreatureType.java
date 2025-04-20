package quickmath.model;

public enum CreatureType {
    NORMAL(0), WATER(1), FIRE(2), ICE(3), ELECTRIC(4);
    final int id;
    CreatureType(int id) { this.id = id; }

    static final float ADVANTAGE = 2f, DISADVANTAGE = 0.5f;
    public static float calculateAdvantage(CreatureType user, CreatureType other) {
        // rock paper scissors lizard spock
        return switch ((other.id - user.id + 5) % 5) {
            case 1, 3 -> ADVANTAGE;
            case 2, 4 -> DISADVANTAGE;
            default -> 1;
        };
    }
}
