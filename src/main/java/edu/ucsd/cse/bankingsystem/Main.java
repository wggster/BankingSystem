package edu.ucsd.cse.bankingsystem;

class Main {
    public static void main(String[] args) {
        final String WELLSFARGO = "Wells Fargo";
        final String USAA = "USAA";
        final String u1AcctNum = "12345678";
        final String u1PIN = "0000";
        final String w1AcctNum = "87654321";
        final String w1PIN = "9999";
        final String wrongPIN = "1111";
        final String badAcctNum = "11111111";

        BankingSystem bankingSystem = new BankingSystem();

        Bank wfrg = new Bank(WELLSFARGO, bankingSystem);
        Bank usaa = new Bank(USAA, bankingSystem);

        usaa.addAccount(u1AcctNum, new Account(u1AcctNum, u1PIN, 100.00));
        wfrg.addAccount(w1AcctNum, new Account(w1AcctNum, w1PIN, 200.00));

        bankingSystem.addBank(WELLSFARGO, wfrg);
        bankingSystem.addBank(USAA, usaa);

        ATM uATM = new ATM(usaa);

        System.out.println("\n// Test the local bank cases:");
        uATM.withdrawlRequest(u1AcctNum, u1PIN, 10.00);    // APPROVED
        uATM.withdrawlRequest(u1AcctNum, u1PIN, 100.00);   // INSUFFICIENT_FUNDS
        uATM.withdrawlRequest(u1AcctNum, wrongPIN, 10.00); // WRONG_PIN

        System.out.println("\n// Test the remote bank (BankingSystem) cases:");
        uATM.withdrawlRequest(w1AcctNum, w1PIN, 10.00);    // APPROVED
        uATM.withdrawlRequest(w1AcctNum, w1PIN, 200.00);   // INSUFFICIENT_FUNDS
        uATM.withdrawlRequest(w1AcctNum, wrongPIN, 10.00); // WRONG_PIN
        uATM.withdrawlRequest(badAcctNum, w1PIN, 10.00); // BAD_ACCOUNT
    }
}

