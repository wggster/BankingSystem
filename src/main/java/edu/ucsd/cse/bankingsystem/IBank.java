package edu.ucsd.cse.bankingsystem;

public interface IBank extends ATMObserver<ATM>, ATMSubject<IBank> {
    boolean accountExists(String acctNumber);

    // @requires !accountExists(acctNumber);
    // @ensures  accountExists(acctNumber);
    void addAccount(String acctNumber, Account account);

    // missing contract: should not be in Bank yet, in Bank after
    void addATM(ATM anATM);

    // @requires accountExists(acctNumber) && getAccountBalance() >= amount;
    // @ensures getAccountBalance(acctNumber == getAccountBalance()@pre - amount;
    void withdraw(String acctNumber, double amount);

    void transfer(double amount);
    void denied(String acctNumber, double amount, Result result);

    Result pinWithdrawalRequest(String acctNumber, String PIN, double amount);
}
