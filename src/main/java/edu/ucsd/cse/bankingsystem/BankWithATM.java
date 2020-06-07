package edu.ucsd.cse.bankingsystem;

import static edu.ucsd.cse.bankingsystem.Result.*;

/*
 * This is a pseudo-Decorator e.g., preserves the full Bank functionality
 * with the addition of knowing which ATM to dispense to.
 *
 * Another solution to this problem would be for the initiating bank to simply remember
 * which ATM was active at the time and all active calls refer to it.  Dynamic scoping.
 *
 */
public class BankWithATM implements IBank {
    IBank bank;
    ATM atm;
    BankWithATM(IBank bank, ATM atm) {
        this.bank = bank;
        this.atm = atm;
    }

    public void onWithdrawalRequest(ATM atm, String acctNumber, String PIN, double amount) {
        bank.onWithdrawalRequest(atm, acctNumber, PIN, amount);
    }
    public void register(ATMObserver<IBank> atmObserver) {
        bank.register( atmObserver);
    }

    public boolean accountExists(String acctNumber) {
        return bank.accountExists(acctNumber);
    }

    public void addAccount(String acctNumber, Account account) {
        bank.addAccount(acctNumber, account);
    }

    public void addATM(ATM anATM) {
        bank.addATM(anATM);
    }

    public void withdraw(String acctNumber, double amount) {
        bank.withdraw(acctNumber, amount);
    }

    // decorated
    public void transfer(double amount) {
        bank.transfer(amount);
        atm.dispense(amount);
    }

    // decorated
    public void denied(String acctNumber, double amount, Result result) {
        bank.denied(acctNumber, amount, result);
        atm.denied(amount, result);
    }

    public Result pinWithdrawalRequest(String acctNumber, String PIN, double amount) {
        return bank.pinWithdrawalRequest(acctNumber, PIN, amount);
    }

}
