package edu.ucsd.cse.bankingsystem;

import java.util.HashMap;
import java.util.Map;
import static edu.ucsd.cse.bankingsystem.Result.*;

class BankingSystem implements ATMObserver<IBank> {
    private Map<String,IBank> banks = new HashMap<String,IBank>();

    public boolean bankExists(String bankID) { return banks.containsKey(bankID); }

    public boolean accountExists(String acctNumber) {
        for (IBank bank : banks.values()) {  // TODO - inefficient
            if (bank.accountExists(acctNumber)) return true;
        }
        return false;
    }

    // @requires !bankExists(bankID);
    // @ensures  bankExists(bankID);
    public void addBank(String bankID, IBank bank) {
        banks.put(bankID, bank);
        bank.register(this);
    }

    // @requires accountExists(acctNumber);
    private IBank retrieveBank(String acctNumber) {
        for (IBank bank : banks.values()) {  // TODO - inefficient
            if (bank.accountExists(acctNumber)) return bank;
        }
        return null; // not reachable if contracts are observed, but required by compiler
    }

    /*
     * We could have an ATM here, but calling it directly would violate the Law of Demeter.
     * The one thing we _could_ do with a "non-friend" reference is pass it back down.
     */
    public void onWithdrawalRequest(IBank bank, String acctNumber, String PIN, double amount) {
        if (!accountExists(acctNumber)) {
            bank.denied(acctNumber, amount, BAD_ACCOUNT);
        }
        else {
            Result result = retrieveBank(acctNumber).pinWithdrawalRequest(acctNumber, PIN, amount);
            if (result == APPROVED)
                bank.transfer(amount);
            else
                bank.denied(acctNumber, amount, result);
        }
    }
}
