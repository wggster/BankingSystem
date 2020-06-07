package edu.ucsd.cse.bankingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static edu.ucsd.cse.bankingsystem.Result.*;


// @invariant accountExists(String acctNumber) @implies getAccountBalance(acctNumber) >= 0
class Bank implements IBank {
    private String bankID;
    private double fundsOnHand = 0;
    private Map<String, Account> accounts = new HashMap<String, Account>();

    Bank(String bankID) { this.bankID = bankID; }

    /*
     * Observer field and methods
     */
    List<ATMObserver<IBank>> observers = new ArrayList<ATMObserver<IBank>>();

    public void register(ATMObserver<IBank> o) { observers.add(o); }

    private void notifyObservers(IBank bank, String acctNumber, String PIN, double amount) {
        for (ATMObserver<IBank> o : observers) {
            o.onWithdrawalRequest(bank, acctNumber, PIN, amount);
        }
    }

    public boolean accountExists(String acctNumber) { return accounts.containsKey(acctNumber); }

    // @requires !accountExists(acctNumber);
    // @ensures  accountExists(acctNumber);
    public void addAccount(String acctNumber, Account account) { accounts.put(acctNumber, account); }

    // @requires accountExists(acctNumber);
    private Account retrieveAccount(String acctNumber) { return accounts.get(acctNumber); }

    // missing contract: should not be in Bank yet, in Bank after
    public void addATM(ATM anATM) {
        anATM.register(this);
    }

    // @requires accountExists(acctNumber);
    private boolean validateATMRequest(String acctNumber, String PIN) {
        return retrieveAccount(acctNumber).validatePIN(PIN); // not a violation of LoD
    }

    // @requires accountExists(acctNumber);
    private double getAccountBalance(String acctNumber) {
        return retrieveAccount(acctNumber).getBalance();
    }

    // @requires accountExists(acctNumber) && getAccountBalance() >= amount;
    // @ensures getAccountBalance(acctNumber) == getAccountBalance(acctNumber)@pre - amount;
    public void withdraw(String acctNumber, double amount) {
         retrieveAccount(acctNumber).withdraw(amount);
    }

    public Result pinWithdrawalRequest(String acctNumber, String PIN, double amount) {
        if (!validateATMRequest(acctNumber, PIN)) {
            return WRONG_PIN;
        }
        else if (getAccountBalance(acctNumber) < amount) {
            return INSUFFICIENT_FUNDS;
        }
        else {
            withdraw(acctNumber, amount);
            return APPROVED;
        }
    }

    // missing contract: funds on hand change by amount
    public void transfer(double amount) { fundsOnHand += amount; }

    /*
     * This would create a log entry.
     */
    public void denied(String acctNumber, double amount, Result result) { }

    public void onWithdrawalRequest(ATM atm, String acctNumber, String PIN, double amount) {
        if (!accountExists(acctNumber)) {
            notifyObservers(new BankWithATM(this, atm), acctNumber, PIN, amount);
        }
        else {
            Result result = pinWithdrawalRequest(acctNumber, PIN, amount);
            if (result == APPROVED) {
                atm.dispense(amount);
            }
            else {
                atm.denied(amount, result);
            }
        }
    }
}

