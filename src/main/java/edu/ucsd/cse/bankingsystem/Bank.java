package edu.ucsd.cse.bankingsystem;

import java.util.HashMap;
import java.util.Map;
import static edu.ucsd.cse.bankingsystem.Result.*;


// @invariant accountExists(String acctNumber) @implies getAccountBalance(acctNumber) >= 0
class Bank {
    private BankingSystem bankingSystem;
    private String bankID;
    private Map<String, edu.ucsd.cse.bankingsystem.Account> accounts = new HashMap<String, edu.ucsd.cse.bankingsystem.Account>();

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
    private boolean validateATMRequest(String acctNumber, String PIN) {
        return retrieveAccount(acctNumber).validatePIN(PIN); // not a violation of LoD
    }

    // @requires accountExists(acctNumber);
    public double getAccountBalance(String acctNumber) {
        return retrieveAccount(acctNumber).getBalance();
    }

    Result atmWithdrawl(String acctNumber, String PIN, double amount) {
        if (!accountExists(acctNumber))
            return bankingSystem.atmWithdrawl(acctNumber, PIN, amount);
        else if (!validateATMRequest(acctNumber, PIN))
            return WRONG_PIN;
        else if (retrieveAccount(acctNumber).getBalance() < amount)
            return INSUFFICIENT_FUNDS;
        else {
            retrieveAccount(acctNumber).withdraw(amount);
            return APPROVED;
        }
    }
}

