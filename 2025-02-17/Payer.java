public abstract class Payer {
    private String name;
    private double anualIncome;

    public Payer(String name, double anualIncome) {
        this.name = name;
        this.anualIncome = anualIncome;
    }

    public String getName() { return this.name; }
    public double getAnualIncome() { return this.anualIncome; }
    public abstract double getTaxPaid();
}