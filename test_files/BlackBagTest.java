import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackBagTest {
    List<Integer> testBlackList = new ArrayList<>();
    List<Integer> testWhiteList = new ArrayList<>(Arrays.asList(1, 2, 3));


    BlackBag testBlackBag = new BlackBag("X", testBlackList);
    WhiteBag testWhiteBag = new WhiteBag("A", testWhiteList);


    @Test
    void testGetPair() {
        BlackBag[] testEmptyBags = new BlackBag[0];
        for (BlackBag testBag : testEmptyBags) {
            assertNotSame("A bag can not be its own pair.",
                    testBag.getName(),
                    testBag.getPair().getName());
        }

    }

    @Test
    void testSetPair() {
        testBlackBag.setPair(testWhiteBag);
        assertEquals(testBlackBag.getPair(), testWhiteBag, "A pair of a black bag should be set with this method.");
    }

    @Test
    void testTransferPebbles() {
        testBlackBag.setPair(testWhiteBag);
        testBlackBag.transferPebbles();
        assertTrue(testWhiteBag.getPebbles().isEmpty(),"Pebbles from white bag transferred to the paired black bag");
        assertEquals(testBlackBag.getPebbles(), testWhiteList);
    }


}
