import static org.junit.jupiter.api.Assertions.*;

class PebbleGameTest {



    @org.junit.jupiter.api.Test
    void testGetPlayerCount() {
        PebbleGame.Player testPlayer = new PebbleGame.Player(0);
        PebbleGame.Player testPlayer1 = new PebbleGame.Player(1);
        PebbleGame.Player testPlayer2 = new PebbleGame.Player(2);

        assert(PebbleGame.getPlayerCount()==3);

    }


}
