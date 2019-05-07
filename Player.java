class Player {
    private int money;

    private String playerName;

    Player(int startingMoney, String name){
        this.money = startingMoney;
        this.playerName = name;
    }
    void deduct(int amount){
        //need to implement bankruptcy logic here
        this.money -= amount;
    }
    void give(int amount){
        this.money += amount;
    }
    int getMoney(){
        return this.money;
    }
    String getPlayerName(){
        return this.playerName;
    }
}