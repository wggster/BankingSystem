package edu.ucsd.cse.bankingsystem;

/*
 * This interface isolates the "up" behavior required from entities that
 * want/need to dispense funds.  By making an interface for it, we are
 * achieving Dependency Inversion, while also freeing the clients of
 * Grantor from having to deal with the rest of their interface/behavior.
 */
public interface Grantor {
    Result pinWithdrawal(String acctNumber, String PIN, double amount);
}
