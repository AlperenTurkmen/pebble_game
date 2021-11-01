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
}

class WhiteBag extends Bag {
    public WhiteBag(String name, List<Integer> pebbles) {
        super(name, pebbles);
    }

}

class BlackBag extends Bag{

    public BlackBag(String name, List<Integer> pebbles) {
        super(name, pebbles);
    }
}
