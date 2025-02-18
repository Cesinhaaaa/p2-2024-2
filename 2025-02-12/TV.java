public class TV extends Product {
    private int inches;

    public TV(String name, double price, int inches) {
        super(name, price);
        this.inches = inches;
    }

    public int getInches() { return this.inches; }
}