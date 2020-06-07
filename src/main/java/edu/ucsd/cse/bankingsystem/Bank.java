package edu.ucsd.cse.bankingsystem;

import java.util.HashMap;
import java.util.Map;
import static edu.ucsd.cse.bankingsystem.Result.*;


// @invariant accountExists(String acctNumber) @implies getAccountBalance(acctNumber) >= 0
class Bank {
    private BankingSystem bankingSystem;
    private double fundsOnHand = 0;
    private String bankID;
    private Map<String, Account> accounts = new HashMap<String, Account>();

    Bank(String bankID, BankingSystem bankingSystem) { this.bankingSystem = bankingSystem; }

    public boolean accountExists(String acctNumber) { return accounts.containsKey(acctNumber); }

    // @requires !accountExists(acctNumber);
    // @ensures  accountExists(acctNumber);
    public void addAccount(String acctNumber, Account account) {
        accounts.put(acctNumber, account);
    }

    // @requires accountExists(acctNumber);
    private Account retrieveAccount(String acctNumber) { return accounts.get(acctNumber); }

    // @requires accountExists(acctNumber);
    private boolean validatePINRequest(String acctNumber, String PIN) {
        return retrieveAccount(acctNumber).validatePIN(PIN); // not a violation of LoD
    }

    // @requires accountExists(acctNumber);
    private double getAccountBalance(String acctNumber) {
        return retrieveAccount(acctNumber).getBalance();
    }

    // missing contract: funds on hand change by amount
    public void transfer(double amount) { fundsOnHand += amount; }

    Result pinWithdrawal(String acctNumber, String PIN, double amount) {
        if (!accountExists(acctNumber)) {
            Result result = bankingSystem.pinWithdrawal(acctNumber, PIN, amount);
            if (result == APPROVED)  // Does transfer bookkeeping, not minding own business!
                transfer(amount);    // Shouldn't be trusting bank to do this honestly.
            return result;
        }
        else if (!validatePINRequest(acctNumber, PIN))
            return WRONG_PIN;
        else if (getAccountBalance(acctNumber) < amount)
            return INSUFFICIENT_FUNDS;
        else {
            retrieveAccount(acctNumber).withdraw(amount);
            return APPROVED;
        }
    }
}

