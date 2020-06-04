package edu.ucsd.cse.bankingsystem;

import java.util.Date;

public class Auditor {
    logWithdrawal(String acctNumber, double amount) {
        System.out.println("Audit record: attempted withdrawal at " + new Date() + ": "
                           + "account: " + acctNumber + ", $" + amount);

    }
}
