import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PayerBuilder pb = new PayerBuilder(sc);
        ArrayList<Payer> payers = new ArrayList<Payer>();

        System.out.print("Enter the number of tax payers: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println(String.format("Tax payer #%d data:", i));
            System.out.print("Individual or company (i/c)? ");

            String payerType = sc.next();

            Payer newPayer = pb.buildPayer(payerType, sc);

            payers.add(newPayer);
        }

        double totalTaxPaid = 0;
        System.out.println("\nTAXES PAID:");

        for (Payer payer : payers) {
            double taxPaid = payer.getTaxPaid();
            totalTaxPaid += taxPaid;

            System.out.println(String.format("%s: $ %.2f", payer.getName(), taxPaid));
        }

        System.out.println(String.format("\nTOTAL TAXES: $ %.2f", totalTaxPaid));        

        sc.close();
    }
}