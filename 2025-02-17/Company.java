public class Company extends Payer {
    private int employees;

    public Company(String name, double anualIncome, int employees) {
        super(name, anualIncome);
        this.employees = employees;
    }

    public int getEmployees() { return this.employees; }

    public double getTaxPaid() {
        double tax = (this.getEmployees() > 10) ? 0.14 : 0.16;
        return this.getAnualIncome() * tax;
    }
}