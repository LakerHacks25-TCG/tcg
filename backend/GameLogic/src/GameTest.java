import java.util.Scanner;

public class GameTest {
    public static void main(String[] args) {

        Move zap = new Move(
                0,
                "Zap",
                CreatureType.ELECTRIC,
                0, 1, 1, null,
                40, 1, 1, null,
                2,
                false
        );

        Move bigPunch = new Move(
                1,
                "Big Punch",
                CreatureType.NORMAL,
                10, 1, 1, null,
                90, 1, 1, null,
                3,
                false
        );

        Move veryAngry = new Move(
                2,
                "Very Angry",
                CreatureType.NORMAL,
                0, 1, 1.25f, null,
                0, 1, 1, null,
                1,
                false
        );

        Move tidalWave = new Move(
                3,
                "Tidal Wave",
                CreatureType.WATER,
                0, 1, 1f, null,
                100, 1, 1, null,
                4,
                false
        );

        Move statusPoison = new Move(
                4,
                "Poison",
                CreatureType.NORMAL,
                10, 1, 1f, null,
                0, 1, 1, null,
                4,
                false
        );

        Move poisonPunch = new Move(
                5,
                "Poison Punch",
                CreatureType.NORMAL,
                0, 1, 1f, null,
                50, 1, 1, null,
                3,
                false
        );



        //Create two test logibeasts
        Creature freddy = new Creature(
                new CreatureType[]{
                        CreatureType.ELECTRIC
                },
                0, "Freddy", new Stats(100, 50, 30),
                new Move[]{
                        zap,bigPunch,veryAngry,poisonPunch
                }
        );

        Creature robert = new Creature(
                new CreatureType[]{
                        CreatureType.FIRE,
                        CreatureType.ICE
                },
                1, "Robert", new Stats(90, 60, 30),
                new Move[]{
                        zap,bigPunch,veryAngry,tidalWave
                }
        );

        System.out.println(freddy);
        System.out.println(robert);

        boolean battleOver = false;
        while(!battleOver) {

            Scanner scanner = new Scanner(System.in);

            int i = 0;
            for(Move move : robert.getMoves()) {
                System.out.println(move.getName() + " (" + i + ")");
                i++;
            }
            Integer moveNum1 = Integer.parseInt(scanner.nextLine());

            i = 0;
            for(Move move : freddy.getMoves()) {
                System.out.println(move.getName() + " (" + i + ")");
                i++;
            }
            Integer moveNum2 = Integer.parseInt(scanner.nextLine());

            System.out.println("Robert used " + robert.getMoves()[moveNum1].getName());
            MoveResultRec rec = robert.getMove(moveNum1).doMove(robert,1f,freddy, 1f);
            robert = rec.getUser();
            freddy = rec.getEnemy();

            System.out.println(freddy);
            System.out.println(robert);
            System.out.println();

            System.out.println("Freddy used " + freddy.getMoves()[moveNum2].getName());
            rec = freddy.getMove(moveNum2).doMove(freddy,1f,robert, 1f);
            freddy = rec.getUser();
            robert = rec.getEnemy();

            System.out.println(freddy);
            System.out.println(robert);
            System.out.println();
        }
    }
}
