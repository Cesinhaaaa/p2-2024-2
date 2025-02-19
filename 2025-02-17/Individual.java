public class Individual extends Payer {
    private double healthExpenditures;

    public Individual(String name, double anualIncome, double healthExpenditures) {
        super(name, anualIncome);
        this.healthExpenditures = healthExpenditures;
    }

    public double getHealthExpenditures() { return this.healthExpenditures; }

    public double getTaxPaid() {
        double anualIncome = this.getAnualIncome();
        double healthExpenditures = this.getHealthExpenditures();

        double tax = (anualIncome < 20000.0) ? 0.15 : 0.25;
        double healthTaxDiscount = (healthExpenditures > 0) ? 0.5 : 0;

        return (anualIncome * tax) - (healthExpenditures * healthTaxDiscount);
    }
}