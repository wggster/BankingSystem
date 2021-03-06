package edu.ucsd.cse.bankingsystem;

// @invariant getBalance() >= 0
class Account {
    private String acctNumber;  // unused at this time
    private double balance;
    private String PIN;
    Account(String acctNumber, String PIN, double balance) {
        this.acctNumber = acctNumber;
        this.PIN = PIN;
        this.balance = balance;
    }

    public double getBalance() { return balance; }

    // @requires getBalance() >= amount
    // @ensures  getBalance() == getBalance()@pre - amount
    public void withdraw(double amount) { balance -= amount; }

    // @requires PIN != null
    public boolean validatePIN(String PIN) { return PIN.equals(this.PIN); }
}

