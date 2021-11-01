import java.util.List;

public class PebbleGame {









    static class  Player implements Runnable{
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
    public static void main(String[] args){

    }
}
