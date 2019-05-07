import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//The command line which take care of all the commands the user inputs
class CMD {
    private Scanner input;

    private Logic logic;

    private HashMap<String, Player> players;

    CMD(Scanner reader){
        this.input = reader;
        this.logic = new Logic(reader);
        showHelpDialog();

        while(true){
            String userInput = this.input.nextLine();
            ArrayList<String> inputAsArray = convertStringToArray(userInput);

            //Arraylist keeps its order. The first word will always be the command.
            switch (inputAsArray.get(0)){
                case "help":
                    showHelpDialog();
                    break;
                case "start":
                    this.logic.initGame();
                    getPlayers();
                    break;
                case "money":
                    //expecting 1 param: name of person whose money will be printed
                    if(this.players.containsKey(inputAsArray.get(1))){
                        System.out.println(this.players.get(inputAsArray.get(1)).getMoney());
                    }
                    else{
                        System.out.println("Unknown player, try again");
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
                "HELP\tPrints this dialog\n" +
                "START\tStart a new game\n");
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
}