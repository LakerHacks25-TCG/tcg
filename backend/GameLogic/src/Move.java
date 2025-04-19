public class Move {
    private String name;
    private final CreatureType type;
    private boolean isStatus;
    private int difficulty; //An integer which describes the difficulty of the move (1-4)
    private final int id;

    int userBaseDmg;
    float userDefenseStatMult;
    float userAttackStatMult;
    Move userNewStatusEffect;

    int enemyBaseDmg;
    float enemyDefenseStatMult;
    float enemyAttackStatMult;
    Move enemyNewStatusEffect;

    public Move(int id,
                String name,
                CreatureType type,
                int userBaseDmg,
                float userDefenseStatMult,
                float userAttackStatMult,
                Move userNewStatusEffect,
                int enemyBaseDmg,
                float enemyDefenseStatMult,
                float enemyAttackStatMult,
                Move enemyNewStatusEffect,
                int difficulty,
                boolean isStatus) {
        this.userBaseDmg = userBaseDmg;
        this.userDefenseStatMult = userDefenseStatMult;
        this.userAttackStatMult = userAttackStatMult;
        this.userNewStatusEffect = userNewStatusEffect;
        this.enemyBaseDmg = enemyBaseDmg;
        this.enemyDefenseStatMult = enemyDefenseStatMult;
        this.enemyAttackStatMult = enemyAttackStatMult;
        this.enemyNewStatusEffect = enemyNewStatusEffect;

        this.difficulty = difficulty;
        this.id = id;
        this.isStatus = isStatus;
        this.name = name;
        this.type = type;
    }

    public Move(Move move) {
        this.userBaseDmg = move.userBaseDmg;
        this.userDefenseStatMult = move.userDefenseStatMult;
        this.userAttackStatMult = move.userAttackStatMult;
        this.userNewStatusEffect = move.userNewStatusEffect;
        this.enemyBaseDmg = move.enemyBaseDmg;
        this.enemyDefenseStatMult = move.enemyDefenseStatMult;
        this.enemyAttackStatMult = move.enemyAttackStatMult;
        this.enemyNewStatusEffect = move.enemyNewStatusEffect;

        this.difficulty = move.difficulty;
        this.id = move.id;
        this.isStatus = move.isStatus;
        this.name = move.name;
        this.type = move.type;
    }

    public Move getEnemyNewStatusEffect() {
        return enemyNewStatusEffect;
    }

    public float getEnemyAttackStatMult() {
        return enemyAttackStatMult;
    }

    public float getEnemyDefenseStatMult() {
        return enemyDefenseStatMult;
    }

    public int getEnemyBaseDmg() {
        return enemyBaseDmg;
    }

    public int getUserBaseDmg() {
        return userBaseDmg;
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

    public int getBaseDmg() {
        return userBaseDmg;
    }

    public float getUserDefenseStatMult() {
        return userDefenseStatMult;
    }

    public float getUserAttackStatMult() {
        return userAttackStatMult;
    }

    public Move getUserNewStatusEffect() {
        return userNewStatusEffect;
    }

    //Do move takes the user creature and the target creature and applies the move
    public MoveResultRec doMove(final Creature user, float userDmgMult, final Creature enemy, float enemyDmgMult) {
        Creature newUser = new Creature(user);
        Creature newEnemy = new Creature(user);

        // calculate new mult based on type advantages
        //TODO: implement type advantages
        float typeMult = 1.0f;

        //statuses cannot have type advantage
        if(isStatus) {
            typeMult = 1.0f;
        }

        int enemyDamage = (int)(enemyBaseDmg*enemyDmgMult*((float)user.getStats().getAttack()/100)*((float)75/enemy.getStats().getDefense())*typeMult);
        int userDamage = (int)(userBaseDmg*userDmgMult*((float)enemy.getStats().getAttack()/100)*((float)75/user.getStats().getDefense())*typeMult);


        newUser.multiplyDefense(userDefenseStatMult);
        newUser.multiplyAttack(userAttackStatMult);
        newUser.applyDamage(userDamage);
        if(userNewStatusEffect != null)
            newUser.addStatusEffect(userNewStatusEffect);

        newEnemy.multiplyDefense(enemyDefenseStatMult);
        newEnemy.multiplyAttack(enemyAttackStatMult);
        newEnemy.applyDamage(enemyDamage);
        if(enemyNewStatusEffect != null)
            newEnemy.addStatusEffect(enemyNewStatusEffect);

        return new MoveResultRec(newUser, newEnemy);
    }


}
