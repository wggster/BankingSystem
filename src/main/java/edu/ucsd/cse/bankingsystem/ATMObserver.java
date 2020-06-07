package edu.ucsd.cse.bankingsystem;

public interface ATMObserver<ModelType> {
    void onWithdrawalRequest(ModelType model, String acctNumber, String PIN, double amount);
}
