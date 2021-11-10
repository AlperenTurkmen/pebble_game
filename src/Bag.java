import java.util.List;


public class Bag {
    private String name;
    private List<Integer> pebbles;

    public Bag(String name, List<Integer> pebbles) {
        this.name = name;
        this.pebbles = pebbles;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Integer> getPebbles() { return pebbles; }
    public void setPebbles(List<Integer> pebbles) { this.pebbles = pebbles; }

    public int size() { return pebbles.size(); }
    public void append(int pebble) { pebbles.add(pebble); }

    public void remove(int index) { pebbles.remove(index); }
    public int get(int index) { return pebbles.get(index); }
}

class WhiteBag extends Bag {
    public WhiteBag(String name, List<Integer> pebbles) {
        super(name, pebbles);
    }

    // Sets the value of white bag to 0 when all the stored pebbles are transferred to the mapped black bag
    public void resetPebbles(){
        this.getPebbles().clear();
    }
}

class BlackBag extends Bag{
    private WhiteBag pair;

    public BlackBag(String name, List<Integer> pebbles) {
        super(name, pebbles);
    }


    public WhiteBag getPair() { return pair; }
    public void setPair(WhiteBag pair) { this.pair = pair; }





    public void transferPebbles() {
        setPebbles(getPair().getPebbles());
        getPair().getPebbles().clear();
    }
}
