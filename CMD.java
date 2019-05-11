import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//The command line which take care of all the commands the user inputs
class CMD {
    private Scanner input;

    private HashMap<String, Player> players = new HashMap<>();

    private boolean isGameStarted = false;
    private boolean isGameDone = false;

    CMD(Scanner reader){
        this.input = reader;

        showHelpDialog();

        while(!isGameDone){
            String userInput = this.input.nextLine();
            ArrayList<String> inputAsArray = convertStringToArray(userInput);

            //Arraylist keeps its order. The first word will always be the command.
            //Arraylist params: (0)->command
            //                  (1)->player
            //                  (2)->amount
            //                  (3)->other
            switch (inputAsArray.get(0)){
                case "help":
                    showHelpDialog();
                    break;
                case "start":
                    if(this.isGameStarted){
                        //if game already started, ask user to confirm new game
                        System.out.println("Game already started, restart game? (Y/N)");
                        if (this.input.nextLine().toLowerCase().equals("y")) {
                            this.players.clear();
                            initGame();
                        }
                    }else{
                        initGame();
                    }
                    this.isGameStarted = true;
                    break;
                case "money":
                    //expecting 1 param: name of person whose money will be printed
                    if(this.isGameStarted) {
                        printMoney(inputAsArray.get(1));
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "moneyall":
                    if(this.isGameStarted) {
                        printMoneyAll();
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "exit":
                    return;
                case "deduct":
                    if(this.isGameStarted) {
                        //expecting 2 params: name of person who to deduct, amount to deduct
                        int amount = 0;
                        try {
                            amount = Integer.parseInt(inputAsArray.get(2));
                        } catch (Exception e) {
                            System.out.println("Invalid amount, please try again");
                        } finally {
                            if (this.players.containsKey(inputAsArray.get(1))) {
                                //If the amount is a Integer, and the player exists, deduct his money.
                                deduct(inputAsArray.get(1), amount);
                                printMoney(inputAsArray.get(1));
                            }
                        }
                        if(this.players.get(inputAsArray.get(1)).isBankrupt()) {
                            bankrupt(this.players.get(inputAsArray.get(1)));
                        }
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "give":
                    if(isGameStarted) {
                        //expecting 2 params: name of person who to give, amount to give
                        int amount = 0;
                        try {
                            amount = Integer.parseInt(inputAsArray.get(2));
                        } catch (Exception e) {
                            System.out.println("Invalid amount, please try again");
                        } finally {
                            if (this.players.containsKey(inputAsArray.get(1))) {
                                //If the amount is a Integer, and the player exists, deduct his money.
                                give(inputAsArray.get(1), amount);
                                printMoney(inputAsArray.get(1));
                            }
                            else{
                                System.out.println("Unknown player");
                            }
                        }
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "pay":
                    //expecting 3 parameters, person who pays, person who receives, amount
                    int amountToPay = 0;
                    String payingPlayer = "";
                    String receivingPlayer = "";
                    try{
                        amountToPay = Integer.parseInt(inputAsArray.get(2));
                        payingPlayer = inputAsArray.get(1);
                        receivingPlayer = inputAsArray.get(3);
                    }
                    catch(Exception e){
                        System.out.println("Error, try again");
                    }
                    try{
                        pay(payingPlayer, amountToPay, receivingPlayer);
                    }
                    catch(Exception f){
                        System.out.println("Unknown player");
                    }
                    if(this.players.get(inputAsArray.get(1)).isBankrupt()) {
                        bankrupt(this.players.get(inputAsArray.get(1)));
                    }
                    break;
                case "remove":
                    String removep = inputAsArray.get(1);

                    if(this.players.containsKey(removep)) {
                        removePlayer(this.players.get(removep));
                        System.out.println("Player removed");
                    }
                    else{
                        System.out.println("Player doesn't exist");
                    }
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }
    private void showHelpDialog(){
        System.out.println("\nList of commands\n" +
                "HELP\t-Prints this dialog\n" +
                "START\t-Start a new game\n" +
                "EXIT\t-End the game\n" +
                "MONEY [player]\t-Show the amount of money that [player] currently has\n" +
                "MONEYALL\t-Show the amount of money that all players currently have\n" +
                "DEDUCT [player] [amount]\t-Deduct [amount] of dollars from [player]\n" +
                "GIVE [player] [amount]\t-Give [amount] of dollars to [player]\n" +
                "PAY [player1] [amount] [player2]\t-[player1] loses [amount] of money, [player2] gets [amount] of money\n" +
                "REMOVE [player]\t-Remove [player] from the game\n");
    }
    private ArrayList convertStringToArray(String string){
        ArrayList<String> converted = new ArrayList<>();

        String currentWord = "";
        for(var i = 0; i < string.length(); i++){
            if(string.charAt(i) == ' '){
                converted.add(currentWord.toLowerCase());
                currentWord = "";
            }
            else{
                currentWord += string.charAt(i);
            }
            if(i == string.length() - 1){
                //If the command is a single word, this will prevent the program from not reading the command
                converted.add(currentWord.toLowerCase());
                currentWord = "";
            }
        }
        return converted;
    }
    private void printMoney(String player){
        if(this.players.containsKey(player)){
            System.out.println(this.players.get(player).getPlayerName()
                    + "\t"
                    + this.players.get(player).getMoney()
                    + "$");
        }
        else{
            System.out.println("Unknown player");
        }
    }
    private void printMoneyAll(){
        if(this.isGameStarted){
            for(String name : this.players.keySet()){
                printMoney(name);
            }
        }
        else{
            System.out.println("Game isn't started yet, can't do that.");
        }
    }
    private void deduct(String player, int amount){
        this.players.get(player).deduct(amount);
    }
    private void give(String player, int amount){
        this.players.get(player).give(amount);
    }
    private void pay(String payingPlayer, int amountToPay, String receivingPlayer){
        this.players.get(payingPlayer).deduct(amountToPay);
        this.players.get(receivingPlayer).give(amountToPay);
        printMoney(payingPlayer);
        printMoney(receivingPlayer);
    }
    private int getAmountOfPlayers(){
        System.out.print("Enter the amount of players: ");
        String userInput = this.input.nextLine();
        int amountOfPlayers;

        //try to convert the entered string into a number, if it fails, call function again until user enters valid input
        try{
            amountOfPlayers = Integer.parseInt(userInput);
        }
        catch(Exception e){
            System.out.println("Invalid number");
            amountOfPlayers = getAmountOfPlayers();
        }
        return amountOfPlayers;
    }
    private int getAmountStartingCash(){
        System.out.print("Enter the amount of starting cash: ");
        String userInput = this.input.nextLine();
        int startingCash;

        //try to convert the entered string into a number, if it fails, call function again until user enters valid input
        try{
            startingCash = Integer.parseInt(userInput);
        }
        catch(Exception e){
            System.out.println("Invalid number");
            startingCash = getAmountStartingCash();
        }
        return startingCash;
    }
    private void initGame() {
        //Start the game
        int playersA = getAmountOfPlayers();
        int startingCash = getAmountStartingCash();
        for (var i = 1; i <= playersA; i++) {
            //Get the player names and create the players
            System.out.print("\t" + i + ". player name: ");
            String name = this.input.nextLine();
            this.players.put(name.toLowerCase(), new Player(startingCash, name));

            System.out.println();
        }
    }
    private void removePlayer(Player player){
        this.players.remove(player.getPlayerName().toLowerCase());
    }
    private void bankrupt(Player player){
        System.out.println(player.getPlayerName() + " went bankrupt! Remove player from game?(Y/N)");
        if(this.input.nextLine().toLowerCase().equals("y")){
            removePlayer(player);
        }
    }
}