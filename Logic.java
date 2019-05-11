import java.util.HashMap;
import java.util.Scanner;
/*
//This class is supposed to take care of the Logic of the game itself
class Logic {
    private Scanner input;

    HashMap<String, Player> players = new HashMap<>();

    Logic(Scanner input){
        this.input = input;
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
    void initGame(){
        //Start the game
        int players = getAmountOfPlayers();
        int startingCash = getAmountStartingCash();
        for(var i = 1; i <= players; i++){
            //Get the player names and create the players
            System.out.print("\t" + i + ". player name: ");
            String name = this.input.nextLine();
            this.players.put(name.toLowerCase(), new Player(startingCash, name));

            System.out.println();
        }
    }
}*/