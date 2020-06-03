package edu.ucsd.cse.bankingsystem;

import static edu.ucsd.cse.bankingsystem.Result.APPROVED;

class ATM {
    private Bank bank;

    ATM(Bank bank) { this.bank = bank; }

    private Result request(String acctNumber, String PIN, double amount) {
        return bank.atmWithdrawl(acctNumber, PIN, amount);
    }

    private void dispense(double amount) {
        System.out.println("Here's your $" + String.format("%.2f",amount) + "!");
    }

    private void denied(double amount, Result result) {
        System.out.println("Your request for $" + String.format("%.2f",amount) + " has been denied because of " + result);
    }

    public void withdrawlRequest(String acctNumber, String PIN, double amount) {
        Result result = request(acctNumber, PIN, amount);
        if (result == APPROVED)
            dispense(amount);
        else
            denied(amount, result);
    }
}