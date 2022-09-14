package com.pmc.atm.service;

import java.sql.*;
import java.util.Scanner;

public class Functions {
    Scanner sc = new Scanner(System.in);
    public Functions() {
        System.out.println("Hello, Welcome to ATM");
        System.out.println("1. Transaction");
        System.out.println("2. Display Balance");
        System.out.println("3. Add or Update");
        System.out.print("What you want to do? : ");
    }


    public void processHeading(String input) throws SQLException {

        PrintConsole console = new PrintConsole();

        switch (input) {
            case "1":
                Transaction ts = new Transaction();
                ts.transaction(new Balance());
                break;

            case "2":
                console.printDisplayBalance();
                processDisplayBalance(sc.nextLine());
                break;

            case "3":
                console.addOrUpdate();
                processAddOrUpdate(sc.nextLine());
                break;
            default:
                System.out.println("Enter a valid input");
        }
    }


    public void processDisplayBalance(String input) throws SQLException {
        Balance balance = new Balance();
        switch (input) {
            case "1":
                balance.showAtmBalance();
                break;

            case "2":
                balance.showCustomerBalance();
                break;

            default:
                System.out.println("Enter a valid input");
        }
    }

    public void processAddOrUpdate(String input) throws SQLException {

        AddOrUpdate addOrUpdate = new AddOrUpdate();
        switch (input) {
            case "1":
                addOrUpdate.addAtm();
                break;

            case "2":
                addOrUpdate.updateAtmBal();
                break;

            case "3":
                addOrUpdate.addBank();
                break;
            case "4":
                addOrUpdate.updateBank();
                break;
            case "5":
                addOrUpdate.addCustomer();
                break;
            case "6":
                addOrUpdate.updateCustomer();
                break;

            default:
                System.out.println("Enter a valid input");
        }
    }


}
