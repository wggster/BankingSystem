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
        System.out.println("Expecting APPROVED: ");
        uATM.withdrawalRequest(u1AcctNum, u1PIN, 10.00);    // APPROVED
        System.out.println("Expecting INSUFFICIENT_FUNDS: ");
        uATM.withdrawalRequest(u1AcctNum, u1PIN, 100.00);   // INSUFFICIENT_FUNDS
        System.out.println("Expecting WRONG_PIN: ");
        uATM.withdrawalRequest(u1AcctNum, wrongPIN, 10.00); // WRONG_PIN

        System.out.println("\n// Test the remote bank (BankingSystem) cases:");
        System.out.println("Expecting APPROVED: ");
        uATM.withdrawalRequest(w1AcctNum, w1PIN, 10.00);    // APPROVED
        System.out.println("Expecting INSUFFICIENT_FUNDS: ");
        uATM.withdrawalRequest(w1AcctNum, w1PIN, 200.00);   // INSUFFICIENT_FUNDS
        System.out.println("Expecting WRONG_PIN: ");
        uATM.withdrawalRequest(w1AcctNum, wrongPIN, 10.00); // WRONG_PIN
        System.out.println("Expecting BAD_ACCOUNT: ");
        uATM.withdrawalRequest(badAcctNum, w1PIN, 10.00); // BAD_ACCOUNT
    }
}

