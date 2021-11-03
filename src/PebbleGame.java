import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PebbleGame {
    private int playerCount;
    private WhiteBag WhiteBagA;
    private WhiteBag WhiteBagB;
    private WhiteBag WhiteBagC;
    private BlackBag BlackBagX;
    private BlackBag BlackBagY;
    private BlackBag BlackBagZ;

    // Initialising all bag objects
    public PebbleGame(int playerCount, List<Integer> x, List<Integer> y, List<Integer> z ) {
        this.playerCount = playerCount;
        WhiteBagA = new WhiteBag("A", new ArrayList<>(BlackBagX.size()));
        WhiteBagB = new WhiteBag("B", new ArrayList<>(BlackBagY.size()));
        WhiteBagC = new WhiteBag("C", new ArrayList<>(BlackBagZ.size()));


        BlackBagX = new BlackBag("X", x);
        BlackBagY = new BlackBag("Y",y );
        BlackBagZ = new BlackBag("Z",z);

        BlackBagX.setPair(WhiteBagA);
        BlackBagY.setPair(WhiteBagB);
        BlackBagZ.setPair(WhiteBagC);
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
    public int getPlayerCount() {
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


    }
}
