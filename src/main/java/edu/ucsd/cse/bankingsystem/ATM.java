package edu.ucsd.cse.bankingsystem;

import static edu.ucsd.cse.bankingsystem.Result.APPROVED;

class ATM {
    private Grantor grantor;

    /*
     * Notice that ATM does not know about Banks, per se.
     */
    ATM(Grantor grantor) { this.grantor = grantor; }

    private Result request(String acctNumber, String PIN, double amount) {
        return grantor.pinWithdrawal(acctNumber, PIN, amount);
    }

    private void dispense(double amount) {
        System.out.println("Here's your $" + String.format("%.2f",amount) + "!");
    }

    private void denied(double amount, Result result) {
        System.out.println("Your request for $" + String.format("%.2f",amount) + " has been denied because of " + result);
    }

    public void withdrawalRequest(String acctNumber, String PIN, double amount) {
        Result result = request(acctNumber, PIN, amount);
        if (result == APPROVED)
            dispense(amount);
        else
            denied(amount, result);
    }
}