public class Refrigerator extends Product {
    private int size;

    public Refrigerator(String name, double price, int size) {
        super(name, price);
        this.size = size;
    }

    public int getSize() { return this.size; }
}