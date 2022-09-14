package com.pmc.atm.service;

public class PrintConsole {

    public void printDisplayBalance() {
        System.out.println("1. ATM Balance");
        System.out.println("2. Customer Account Balance");
        System.out.print("What you want to do : ");
    }

    public void addOrUpdate() {
        System.out.println("1. Add ATM");
        System.out.println("2. Update ATM Balance");
        System.out.println("3. Add Bank");
        System.out.println("4. Update Bank");
        System.out.println("5. Add Customer");
        System.out.println("6. Update Customer");
        System.out.print("What you want to do : ");
    }

}
