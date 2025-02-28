public class Account {
    private int number;
    private String holder;
    private double balance = 0;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.deposit(balance);
        this.withdrawLimit = withdrawLimit;
    }

    public double getBalance() { return this.balance; }

    public void deposit(double amount) { this.balance += amount; }

    public void withdraw(double amount) throws WithdrawException {
        if (amount > this.withdrawLimit) {
            throw new WithdrawException("The amount exceeds withdraw limit");
        }

        if (amount > this.balance) {
            throw new WithdrawException("Not enough balance");
        }

        this.balance -= amount;
    }
}