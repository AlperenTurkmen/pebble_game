import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class PebbleGame {
    private int playerCount;
    private WhiteBag WhiteBagA;
    private WhiteBag WhiteBagB;
    private WhiteBag WhiteBagC;
    private static BlackBag BlackBagX;
    private static BlackBag BlackBagY;
    private static BlackBag BlackBagZ;


    // Initialising all bag objects
    public PebbleGame(int playerCount, List<Integer> x, List<Integer> y, List<Integer> z ) {
        this.playerCount = playerCount;
        WhiteBagA = new WhiteBag("A", new ArrayList<>());
        WhiteBagB = new WhiteBag("B", new ArrayList<>());
        WhiteBagC = new WhiteBag("C", new ArrayList<>());


        BlackBagX = new BlackBag("X", x);
        BlackBagY = new BlackBag("Y", y);
        BlackBagZ = new BlackBag("Z", z);

        BlackBagX.setPair(WhiteBagA);
        BlackBagY.setPair(WhiteBagB);
        BlackBagZ.setPair(WhiteBagC);


    }

    // Chooses a random bag out of X, Y and Z in a thread safe manner

    static BlackBag chooseBag() {
        BlackBag bag = null;
        int number = ThreadLocalRandom.current().nextInt(0, 3);
        switch (number) {
            case 0 -> bag = BlackBagX;
            case 1 -> bag = BlackBagY;
            case 2 -> bag = BlackBagZ;
        }
        System.out.println(number);
        System.out.println(bag.getName());
        return bag;


    }
    static ArrayList<Integer> loadBag(String fileName, int playerCount) {
        Scanner Scanner = null;
        boolean enoughPebbles = false;
        ArrayList<Integer> bag = new ArrayList<Integer>();

        // Tries to read data from file into a string array
        // Data from string array is parsed into an integer arraylist to remove whitespaces
        try {
            Scanner = new Scanner(new File(fileName));
            String[] pebbles = Scanner.nextLine().split(",");
            ArrayList<Integer> pebblesTrimmed = new ArrayList<Integer>();
            for (String p: pebbles) {
                pebblesTrimmed.add(Integer.parseInt(p.trim()));

            }
            // Loops until the arraylist is of length 11 x the number of players
            do {
                int number = ThreadLocalRandom.current().nextInt(0, 100);
                bag.add(number);
                if (bag.size() >= 11*playerCount) {
                    enoughPebbles = true;
                }


            } while (!enoughPebbles);


        // if file is not found in project directory an exception is thrown
        } catch (FileNotFoundException e) { System.out.println("file not found in directory"); }

        // returns an arraylist called bag
        return bag;
    }
    void loadHand() {
        BlackBag bag = chooseBag();

        for (int i = 0; i < 10; i++){

        }

    }
    void drawPebble() {

    }
    void discardPebble(){

    }

    static class Player implements Runnable{
        private String playerName;
        private List<Integer> hand;
        private int totalHandValue;
        private boolean winner;
        private boolean yourTurn;
        //

        public Player(String playerName, List<Integer> hand, int totalHandValue, boolean winner, boolean yourTurn) {
            this.playerName = playerName;
            this.hand = hand;
            this.totalHandValue = totalHandValue;
            this.winner = winner;
            this.yourTurn = yourTurn;
        }

        public String getPlayerName() { return playerName; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }

        public List<Integer> getHand() { return hand; }
        public void setHand(List<Integer> hand) { this.hand = hand; }
        public int getTotalHandValue() { return totalHandValue; }
        public void setTotalHandValue(int totalHandValue) { this.totalHandValue = totalHandValue; }

        public boolean isYourTurn() { return yourTurn; }
        public void setYourTurn(boolean yourTurn) { this.yourTurn = yourTurn; }

        public boolean isWinner() { return winner; }
        public void setWinner(boolean winner) { this.winner = winner; }



        @Override
        public void run() {

        }
    }
    // Method returns the total number of users in a game as an integer
    public static int getPlayerCount() {
        Scanner input = new Scanner(System.in);
        int totalPlayers;
        while (true) {
            try {
                System.out.println("Enter the number of players for this game");
                totalPlayers = input.nextInt();
                if (totalPlayers < 1) {
                    throw new NumberFormatException(); // Player count cannot be 0 or a negative value
                }
                break;
            }
            catch (NumberFormatException e) { System.out.println("Number of players must be greater than 0"); }
        }
        return totalPlayers;



    }
    public static String fileName() {
        String fileName;
        int choice;
        boolean quit = false;

        System.out.println("Enter the name of your file without any extensions");
        Scanner input = new Scanner(System.in);
        fileName = input.nextLine();

        System.out.println("Please select your file type from one of the options below");
        System.out.println("1) Text file");
        System.out.println("2) CSV file");
        System.out.println("3) Exit program");

        choice = input.nextInt();
        while (!quit) {
            switch (choice) {
                case 1 -> {
                    fileName = fileName.concat(".txt");
                    return fileName;
                }
                case 2 -> {
                    fileName = fileName.concat(".csv");
                    return fileName;
                }
                case 3 -> quit = true;
                default -> System.out.println("Invalid selection try again");
            }
        }
        return fileName;

    }
    public static void main(String[] args){

        int playerNumber = getPlayerCount();
        String nameOfFile = fileName();

        loadBag(nameOfFile, playerNumber);

        PebbleGame game = new PebbleGame(playerNumber, loadBag(nameOfFile, playerNumber), loadBag(nameOfFile, playerNumber),loadBag(nameOfFile, playerNumber));
        System.out.println("Contents of bag X:" + BlackBagX.getPebbles());
        System.out.println("Contents of bag Y:" + BlackBagY.getPebbles());
        System.out.println("Contents of bag Z:" + BlackBagZ.getPebbles());
        System.out.println("Number of pebbles in bag X:" + BlackBagX.size());
        System.out.println("Number of pebbles in bag Y:" + BlackBagX.size());
        System.out.println("Number of pebbles in bag Z:" + BlackBagX.size());

    }
}
