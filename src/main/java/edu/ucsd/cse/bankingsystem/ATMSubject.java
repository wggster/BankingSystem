package edu.ucsd.cse.bankingsystem;

public interface ATMSubject<ModelType> {
    void register(ATMObserver<ModelType> observer);
}
