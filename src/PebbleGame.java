import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class PebbleGame {
    private int playerCount;
    private WhiteBag WhiteBagA;
    private WhiteBag WhiteBagB;
    private WhiteBag WhiteBagC;
    private static BlackBag BlackBagX;
    private static BlackBag BlackBagY;
    private static BlackBag BlackBagZ;
    private static Player[] players;


    // Initialising all bag objects
    public PebbleGame(int playerCount, List<Integer> x, List<Integer> y, List<Integer> z ) {
        this.playerCount = playerCount;

        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player(i+1);
        }

        // Initialising whitebags A, B, and C as objects
        WhiteBagA = new WhiteBag("A", new ArrayList<>());
        WhiteBagB = new WhiteBag("B", new ArrayList<>());
        WhiteBagC = new WhiteBag("C", new ArrayList<>());

        // Initialising blackbags X, Y, and Z as objects
        BlackBagX = new BlackBag("X", x);
        BlackBagY = new BlackBag("Y", y);
        BlackBagZ = new BlackBag("Z", z);

        // Pairing each blackbag to whitebag
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
        return bag;
    }

    // Loads a bag with pebbles from the specified file until bag has 11x pebbles where x is the number of players
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
                int number = ThreadLocalRandom.current().nextInt(1, pebblesTrimmed.size());
                bag.add(number);
                if (bag.size() >= 11*playerCount) {
                    enoughPebbles = true;
                }
            } while (!enoughPebbles);

        // if file is not found in project directory an exception is thrown
        } catch (FileNotFoundException e) { System.out.println("file not found in directory"); return null;}

        // returns an arraylist called bag
        return bag;
    }

    static class Player implements Runnable{
        private int playerNumber;
        private ArrayList<Integer> hand;
        private int totalHandValue;
        private boolean winner;
        private WhiteBag PairedBag;

        public Player(int playerNumber) {
            this.playerNumber = playerNumber;
            hand = new ArrayList<Integer>();
            winner = false;
        }

        public int getPlayerNumber() { return playerNumber; }
        public void setPlayerNumber(int playerNumber) { this.playerNumber = playerNumber; }

        public List<Integer> getHand() { return hand; }
        public void setHand(ArrayList<Integer> hand) { this.hand = hand; }

        public boolean isWinner() { return winner; }
        public void setWinner(boolean winner) { this.winner = winner; }

        // Loads hand of players at turn 1 with 10 pebbles each drawn at random from the three bags
        List<Integer> loadHand() {
            BlackBag bag = chooseBag();
            int drawnPebble;
            for (int i = 0; i < 10; i++){
                // prevents other threads from using this method
                // whilst a thread is executing this method
                synchronized(bag) {
                    // Chooses the position of a pebble from the list at random
                    int index = ThreadLocalRandom.current().nextInt( 0, bag.size() );
                    drawnPebble = bag.get(index);
                    bag.remove(index);
                    // Adds the drawn pebble to current player's hand
                    hand.add(drawnPebble);
                    totalHandValue += drawnPebble;
                }
            }

            System.out.println("Player " + getPlayerNumber() + "'s starting hand: " + hand);
            System.out.println("Player " + getPlayerNumber() + "'s hand value: " + totalHandValue);
            checkHand();

            PairedBag = bag.getPair();
            return hand;

        }
        // draws a pebble from a random bag
        void drawPebble() {
            BlackBag bag = null;
            int drawnPebble;

            // Loads a bag from choose bag function when bag object is empty
            while (bag == null) {
                bag = chooseBag();
            }
            // prevents other threads from using this method
            // whilst a thread is executing this method
            synchronized (bag){
                // Chooses the position of a pebble from the list at random
                int index = ThreadLocalRandom.current().nextInt( 0, bag.size());
                drawnPebble = bag.get(index);
                hand.add(drawnPebble);
                totalHandValue += drawnPebble;
                bag.remove(index);

                System.out.println("Player " + getPlayerNumber() + " has drawn a " + drawnPebble + " from Bag " + bag.getName());
                System.out.println("Player " + getPlayerNumber() + "'s current hand: " + hand);
                System.out.println("Player " + getPlayerNumber() + "'s hand value: " + totalHandValue);
                checkHand();
            }

            // Transfer pebbles from paired white bag if bag is empty
            if (bag.size() == 0) {
                bag.transferPebbles();

            }
            PairedBag = bag.getPair();

            // File handling
            String filename = "player" + getPlayerNumber() + "_output.txt";

            try(FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw))
            {
                pw.println("Player " + getPlayerNumber() + " has drawn a " + drawnPebble + " from Bag " + bag.getName());
                pw.println("Player " + getPlayerNumber() + "'s current hand: " + hand);
            } catch (IOException e) {
                System.out.println("An error occurred");
            }

        }

        // Discards a pebble to the corresponding paired white bag
        void discardPebble(){
            int index = ThreadLocalRandom.current().nextInt( 0, hand.size() );
            int drawnPebble = hand.get(index);

            // prevents other threads from using this method
            // whilst a thread is executing this method
            synchronized (PairedBag) {
                hand.remove(index);
                totalHandValue -= drawnPebble;
                PairedBag.append(drawnPebble);

                System.out.println("Player " + getPlayerNumber() + " has discard pebble of value " + drawnPebble + " to Bag " + PairedBag.getName());
                System.out.println("Player " + getPlayerNumber() + "'s current hand: " + hand);
                System.out.println("Player " + getPlayerNumber() + "'s hand value: " + totalHandValue);
                checkHand();
            }

            // File handling
            String filename = "player" + getPlayerNumber() + "_output.txt";
            try(FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw))
            {
                pw.println("Player " + getPlayerNumber() + " has discard pebble of value " + drawnPebble + " to Bag " + PairedBag.getName());
                pw.println("Player " + getPlayerNumber() + "'s current hand: " + hand);
            } catch (IOException e) {
                System.out.println("An error occurred");
            }

        }
        void checkHand() {
            if (totalHandValue == 100 && !winner) {
                setWinner(true);
                System.out.println("Player " + getPlayerNumber() + " has a winning hand!");
            }
        }
        @Override
        public void run() {
            loadHand();
            while (!isWinner()) {
                discardPebble();
                drawPebble();
            }
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
        Scanner Scanner = new Scanner(System.in);

        int totalPlayers = getPlayerCount();
        String nameOfFile = fileName();
        PebbleGame game = new PebbleGame(totalPlayers, loadBag(nameOfFile, totalPlayers), loadBag(nameOfFile, totalPlayers),loadBag(nameOfFile, totalPlayers));

        // Deletes previous files from previous simulations
        for (int i = 0; i < totalPlayers; i++) {
            String filePath = "player" + i+1 + "_output.txt";
            File file = new File(filePath);
            if (file.exists()){
                file.delete();
            }
        }
        ExecutorService service = Executors.newFixedThreadPool(totalPlayers);
        for (Player p : players) {
            service.submit(new Thread(p));
            // Forces threads to shut down when a winning hand exists
            if (p.isWinner()) {
                service.shutdownNow();
            }
        }
        service.shutdown();

    }
}