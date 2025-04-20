public class Stats {
    int hp, defense, attack, maxHp;

    public Stats(int hp, int defense, int attack) {
        this.hp = hp;
        this.maxHp = hp;
        this.defense  = defense;
        this.attack = attack;
    }

    public Stats(Stats stats) {
        this.hp = stats.hp;
        this.maxHp = stats.maxHp;
        this.defense  = stats.defense;
        this.attack = stats.attack;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        if(attack < 1)
            this.attack = 1;
        else {
            this.attack = attack;
        }
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        if(defense < 1)
            this.defense = 1;
        else {
            this.defense = defense;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if(hp < 0)
            this.hp = 0;
        else {
            this.hp = hp;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "hp=" + hp +
                ", defense=" + defense +
                ", attack=" + attack +
                ", maxHp=" + maxHp +
                '}';
    }
}
