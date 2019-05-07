import java.util.HashMap;
import java.util.Scanner;

class UI {
    private Scanner input;

    private HashMap<String, Player> players = new HashMap<>();

    public UI(Scanner input){
        this.input = input;

        initGame();
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
    private void initGame(){
        int players = getAmountOfPlayers();
        int startingCash = getAmountStartingCash();
        for(var i = 1; i <= players; i++){
            //Get the player names and create the players
            System.out.print("\t" + i + ". player name: ");
            String name = this.input.nextLine().toLowerCase();
            this.players.put(name, new Player(startingCash, name));

            System.out.println();
        }
        for(String name : this.players.keySet()){
            System.out.println(name);
        }
    }
}
