import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    List<Integer> testBlackList = new ArrayList<>();
    List<Integer> testWhiteList = new ArrayList<>(Arrays.asList(1, 2, 3));


    BlackBag testBlackBag = new BlackBag("X", testBlackList);
    WhiteBag testWhiteBag = new WhiteBag("A", testWhiteList);
    @Test
    void testGetName() {
        assertEquals("A",testWhiteBag.getName(),"The name of a bag should be seen by this getter method.");
    }

    @Test
    void testSetName() {
        testBlackBag.setName("A");
        assertEquals(testBlackBag.getName(),"A","The name of a bag should be set by this setter method.");
    }

    @Test
    void testGetPebbles() {
        assertEquals(Arrays.asList(1,2,3),testWhiteBag.getPebbles(), "Should get pebbles of white bag using the getter method");
    }

    @Test
    void testSetPebbles() {
        testBlackBag.setPebbles(Arrays.asList(1,2,3));
        assertEquals(Arrays.asList(1,2,3),testBlackBag.getPebbles(),"The pebbles should be set by this method.");
    }

    @Test
    void testSize() {
        assertEquals(3,testWhiteBag.size(), "Should get the total no. of pebbles using the size() method");

    }

    @Test
    void testAppend() {
        testBlackBag.append(1872);
        assertEquals(Arrays.asList(1872),testBlackBag.getPebbles(),"Should be able to append an element to the pebbles of a bag.");

    }

    @Test
    void testRemove() {
        testWhiteBag.remove(2);
        assertEquals(Arrays.asList(1,2),testWhiteBag.getPebbles(),"Should get the list of pebbles inside a bag.");
    }

    @Test
    void testGet() {
        assertEquals(1,testWhiteBag.get(0),"Should get the value of the given index.");
    }
}
