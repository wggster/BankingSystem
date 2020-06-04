package edu.ucsd.cse.bankingsystem;

import java.util.HashMap;
import java.util.Map;
import static edu.ucsd.cse.bankingsystem.Result.BAD_ACCOUNT;

class BankingSystem {
    private Map<String,Bank> banks = new HashMap<String,Bank>();

    public boolean bankExists(String bankID) { return banks.containsKey(bankID); }

    public boolean accountExists(String acctNumber) {
        for (Bank bank : banks.values()) {  // TODO - inefficient
            if (bank.accountExists(acctNumber)) return true;
        }
        return false;
    }

    // @requires !bankExists(bankID);
    // @ensures  bankExists(bankID);
    public void addBank(String bankID, Bank bank) {
        banks.put(bankID, bank);
    }

    // @requires accountExists(acctNumber);
    private Bank retrieveBank(String acctNumber) {
        for (Bank bank : banks.values()) {  // TODO - inefficient
            if (bank.accountExists(acctNumber)) return bank;
        }
        return null; // not reachable if contracts are observed, but required by compiler
    }

    // @ensures !accountExists(acctNumber) @implies @return == BAD_ACCOUNT
    // @ensures accountExists(acctNumber) @implies @return == retrieveBank().atmWithdrawl(acctNumber)
    Result atmWithdrawal(String acctNumber, String PIN, double amount) {
        if (!accountExists(acctNumber))
            return BAD_ACCOUNT;
        else
            return retrieveBank(acctNumber).atmWithdrawal(acctNumber, PIN, amount);
    }
}
