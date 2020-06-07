package edu.ucsd.cse.bankingsystem;

import java.util.ArrayList;
import java.util.List;

class ATM implements ATMSubject<ATM> {
    List<ATMObserver<ATM>> observers = new ArrayList<ATMObserver<ATM>>();

    public void register(ATMObserver<ATM> o) { observers.add(o); }

    private void notifyObservers(ATM atm, String acctNumber, String PIN, double amount) {
        for (ATMObserver<ATM> o : observers) {
            o.onWithdrawalRequest(atm, acctNumber, PIN, amount);
        }
    }

    /*
     * I deleted withdrawalRequest, as it wasn't minding it's own business.
     * It knew how to respond to transaction result codes.  That's the
     * bank's job.
     *
     * This method is now the "user interface" to the ATM.
     */
    public void request(String acctNumber, String PIN, double amount) {
        notifyObservers(this, acctNumber, PIN, amount);
    }

    /*
     * These two methods, now package private, are called by a mediator (Bank).
     */
    void dispense(double amount) {
        System.out.println("Here's your $" + String.format("%.2f", amount) + "!");
    }

    void denied(double amount, Result result) {
        System.out.println("Your request for $" + String.format("%.2f", amount) + " has been denied because of " + result);
    }
}