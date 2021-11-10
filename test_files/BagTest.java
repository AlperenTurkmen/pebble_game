import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void getName() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(3);
        list.add(4);

        Bag b = new Bag("A", list);
        assertEquals( b.getName(), list);
    }

    @Test
    public void setName() {
    }

    @Test
    public void getPebbles() {
    }

    @Test
    public void setPebbles() {
    }

    @Test
    public void size() {
    }

    @Test
    public void append() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void get() {
    }
}