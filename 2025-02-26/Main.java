import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String[] fields = {
            "Number",
            "Holder",
            "Initial balance",
            "Withdraw limit",
            "\nEnter amount for withdraw"
        };
        
        String[] inputs = new String[fields.length];

        System.out.println("Enter account data");
        for (int i = 0; i < fields.length; i++) {
            System.out.print(fields[i] + ": ");
            inputs[i] = sc.nextLine();
        }
        
        sc.close();
        
        int number = Integer.parseInt(inputs[0]);
        String holder = inputs[1];
        double initialBalance = Double.parseDouble(inputs[2]);
        double withdrawLimit = Double.parseDouble(inputs[3]);

        Account account = new Account(number, holder, initialBalance, withdrawLimit);

        double withdrawValue = Double.parseDouble(inputs[4]);

        try {
            account.withdraw(withdrawValue);
            System.out.println("New balance: " + account.getBalance());
        } catch (WithdrawException e) {
            System.out.println("Withdraw error: " + e.getMessage());
        }
    }
}