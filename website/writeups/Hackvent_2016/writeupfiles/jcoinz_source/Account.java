/*
 * Decompiled with CFR 0_118.
 */
public class Account {
    private String name;
    private int coins;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean payCoins(int amount) {
        int decreasedCoins;
        if (this.getCoins() <= 0) {
            IO.printStatus("-", "No more jcoinz!\n\n");
            return false;
        }
        if (amount < 0) {
            amount *= -1;
        }
        if ((decreasedCoins = this.getCoins() - amount - Shop.transactionTax) < 0) {
            IO.printStatus("-", "You cannot generate debts!\n\n");
            return false;
        }
        this.setCoins(decreasedCoins);
        IO.printStatus("-", "Decreased the account of \"" + this.getName() + "\" by " + String.valueOf(amount) + "\n");
        return true;
    }

    public Account(String name, int coins) {
        this.name = name;
        this.coins = coins;
    }
}

