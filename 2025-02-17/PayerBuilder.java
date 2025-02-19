import java.util.Scanner;

public class PayerBuilder {
    private Scanner sc;

    public PayerBuilder(Scanner sc) { this.sc = sc; }

    private String readString(String message) {
        System.out.print(message);
        return this.sc.next();
    }

    private double readDouble(String message) {
        System.out.print(message);
        return this.sc.nextFloat();
    }

    private int readInt(String message) {
        System.out.print(message);
        return this.sc.nextInt();
    }

    private Individual readIndividual(String name, double anualIncome) {
        double healthExpenditures = readDouble("Health expenditures: ");
        return new Individual(name, anualIncome, healthExpenditures);
    }

    private Company readCompany(String name, double anualIncome) {
        int employees = readInt("Number of employees: ");
        return new Company(name, anualIncome, employees);
    }

    public Payer buildPayer(String payerType, Scanner sc) {
        String name = readString("Name: ");
        double anualIncome = readDouble("Anual income: ");

        switch (payerType) {
            case "i": return readIndividual(name, anualIncome);
            case "c": return readCompany(name, anualIncome);
            default: return new Individual("GENERIC-PAYER", 0, 0); // just to be consistent with this method, Exceptions have not been introduced yet.
        }      
    }
}