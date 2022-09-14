package com.pmc.atm.service;

import com.pmc.atm.model.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddOrUpdate {

    Connection conn = DbConnection.getConnection("jdbc:mysql://localhost:3306/atm_db", "root", "Fk@000143");
    Scanner sc = new Scanner(System.in);
    public AddOrUpdate() throws SQLException {

    }

    public void updateCustomer() {
        System.out.println("Customer Updated");
    }

    public void addCustomer() {
        System.out.print("Enter the Customer ID : ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the Customer name : ");
        String customerName = sc.nextLine();
        System.out.print("Enter Account Id: ");
        int accId = sc.nextInt();
        System.out.print("Enter Bank Id of that customer: ");
        int bankId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the account type : ");
        String accType = sc.nextLine();
        System.out.print("Enter the account status : ");
        String accStatus = sc.nextLine();
        System.out.print("Enter Account Balance: ");
        int accBal = sc.nextInt();


        try( PreparedStatement stmt = conn.prepareStatement("insert into atm_db.account values(?,?,?,?,?)") ){
            stmt.setInt(1,accId);
            stmt.setInt(2,bankId);
            stmt.setString(3,accType);
            stmt.setString(4,accStatus);
            stmt.setInt(5,accBal);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};

        try( PreparedStatement stmt = conn.prepareStatement("insert into atm_db.customer values(?,?,?)") ){
            stmt.setInt(1,id);
            stmt.setString(2,customerName);
            stmt.setInt(3,accId);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};
    }

    public void updateBank() {
        System.out.println("Enter the Bank Id which you want to delete");
        int id = sc.nextInt();
        try( PreparedStatement stmt = conn.prepareStatement("DELETE FROM atm_db.bank WHERE id = ?")) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};
    }

    public void addBank() {
        System.out.print("Enter the Bank ID : ");
        int bankId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the Bank name : ");
        String bankName = sc.nextLine();
        try( PreparedStatement stmt = conn.prepareStatement("insert into atm_db.bank values(?,?)") ){
            stmt.setInt(1,bankId);
            stmt.setString(2,bankName);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};
    }

    public void updateAtmBal() {
        System.out.print("Enter the atm name : ");
        String atmName = sc.nextLine();
        System.out.print("Enter the amount you want to add to the atm: ");
        int amount = sc.nextInt();
        try( PreparedStatement stmt = conn.prepareStatement("UPDATE atm_db.atm SET balance = balance + ? WHERE atm_name = ?;")) {
            stmt.setInt(1,amount);
            stmt.setString(2,atmName);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};
    }

    public void addAtm() throws SQLException {
        System.out.print("Enter the Atm ID : ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the atm name : ");
        String str = sc.nextLine();
        System.out.print("Enter the balance of the ATM : ");
        int bal = sc.nextInt();
        System.out.print("Enter the Maximum Withdrawal limit : ");
        int maxLimit = sc.nextInt();
        try( PreparedStatement stmt = conn.prepareStatement("insert into atm_db.atm values(?,?,?,?)");) {
            stmt.setInt(1,id);
            stmt.setString(2,str);
            stmt.setInt(3,bal);
            stmt.setInt(4,maxLimit);
            stmt.executeUpdate();
        }
        catch(Exception e) { System.out.println(e);};
    }
}
