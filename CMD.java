import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//The command line which take care of all the commands the user inputs
class CMD {
    private Scanner input;

    private Logic logic;

    private HashMap<String, Player> players;

    private boolean isGameStarted = false;
    private boolean isGameDone = false;

    CMD(Scanner reader){
        this.input = reader;
        this.logic = new Logic(reader);
        showHelpDialog();

        while(!isGameDone){
            String userInput = this.input.nextLine();
            ArrayList<String> inputAsArray = convertStringToArray(userInput);

            //Arraylist keeps its order. The first word will always be the command.
            //Arraylist params: (0)->command
            //                  (1)->player
            //                  (2)->amount
            switch (inputAsArray.get(0)){
                case "help":
                    showHelpDialog();
                    break;
                case "start":
                    this.logic.initGame();
                    getPlayers();
                    this.isGameStarted = true;
                    break;
                case "money":
                    //expecting 1 param: name of person whose money will be printed
                    if(isGameStarted) {
                        printMoney(inputAsArray.get(1));
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "moneyall":
                    if(isGameStarted) {
                        printMoneyAll();
                    }
                    else{
                        System.out.println("Game isn't started yet, can't do that.");
                    }
                    break;
                case "exit":
                    this.isGameDone = true;
                    break;
                case "deduct":
                    if(isGameStarted) {
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
                        System.out.println("Error");
                    }
                    try{
                        pay(payingPlayer, amountToPay, receivingPlayer);
                    }
                    catch(Exception f){
                        System.out.println("Unknown player");
                    }
                    break;
                case "remove":
                    String removep = inputAsArray.get(1);

                    if(this.players.containsKey(removep)) {
                        this.players.remove(removep);
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
    private void getPlayers(){
        this.players = this.logic.players;
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
}