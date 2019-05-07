class Player {
    private int money;

    private String playerName;

    Player(int startingMoney, String name){
        this.money = startingMoney;
        this.playerName = name;
    }
    int getMoney(){
        return this.money;
    }
}