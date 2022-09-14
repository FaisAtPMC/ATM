package com.pmc.atm.service;

import com.pmc.atm.model.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Transaction {
    Scanner sc;
    Connection conn = DbConnection.getConnection("jdbc:mysql://localhost:3306/atm_db", "root", "Fk@000143");

    public Transaction() throws SQLException {
    }

    public void transaction(Balance balance) throws SQLException {
        sc = new Scanner(System.in);
        System.out.print("Select ATM : ");
        String atmName = sc.nextLine();
        System.out.print("Enter Account Number : ");
        int accountNo = sc.nextInt();
        System.out.println("1. Credit\n2. Debit\n3. Show Balance");
        System.out.print("What you want to do: ");
        int task = sc.nextInt();
        switch (task) {
            case 1:
                System.out.print("Enter the amount you want to deposit : ");
                credit(atmName, accountNo);
                break;
            case 2:
                System.out.print("Enter the amount you want to cash out : ");
                debit(atmName, accountNo);
                break;
            case 3:
                System.out.print("Your current balance is: ");
                balance.showCurrentBalance(accountNo);
                break;
            default:
                System.out.println("Enter a valid Input.");
        }
        System.out.println("Thanks for using ATM");

    }

    void credit(String atmName, int id) throws SQLException {

        sc = new Scanner(System.in);
        int creditAmount = sc.nextInt();

        String sql = "update account set balance = balance + ? where id = ?;";
        try (PreparedStatement updateAmount = conn.prepareStatement(sql)) {
            updateAmount.setInt(1, creditAmount);
            updateAmount.setInt(2, id);
            updateAmount.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (PreparedStatement updateAtmBal = conn.prepareStatement("update atm set balance = balance + ? where atm_name = ?;")) {
            updateAtmBal.setInt(1, creditAmount);
            updateAtmBal.setString(2, atmName);
            updateAtmBal.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement stmt = conn.prepareStatement("select id from atm_db.atm where atm_name = ?");
        stmt.setString(1, atmName);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int atmId = rs.getInt("id");
        updateTransactionTable(id, atmId, "credit", creditAmount);

    }

    void debit(String atmName, int id) throws SQLException {
        int debitAmount = sc.nextInt();
        String sql = "update account set balance = balance - ? where id = ?;";
        if (validateAtmBal(debitAmount, atmName) && validateAccBal(debitAmount,id)) {
            System.out.println("Debit is happening");
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, debitAmount);
                ps.setInt(2, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement updateAtmBal = conn.prepareStatement("update atm set balance = balance - ? where atm_name = ?;")) {
                updateAtmBal.setInt(1, debitAmount);
                updateAtmBal.setString(2, atmName);
                updateAtmBal.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            PreparedStatement stmt = conn.prepareStatement("select id from atm_db.atm where atm_name = ?");
            stmt.setString(1, atmName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int atmId = rs.getInt("id");

            updateTransactionTable(id, atmId, "debit", debitAmount);
        }
        else System.out.println("Please Enter Valid Amount");



    }

    void updateTransactionTable(int accId, int atmId, String type, int amount) {
        String sql = "insert into atm_db.transaction values(0,?,?,?,?);";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accId);
            ps.setInt(2, atmId);
            ps.setString(3, type);
            ps.setInt(4, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateAtmBal(int amount, String atmName) {
        // if atmBalance > amount && amount < max limit
        int limit = 0;
        String atmBalSql = "SELECT balance FROM atm_db.atm where atm_name=?";
        int bal = 0;
        try (PreparedStatement ps = conn.prepareStatement(atmBalSql)) {
            ps.setString(1, atmName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            bal = rs.getInt("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String maxLimitSql = "SELECT max_withdraw FROM atm_db.atm where atm_name=?";
        try (PreparedStatement ps = conn.prepareStatement(maxLimitSql)) {
            ps.setString(1, atmName);
            ResultSet rs = ps.executeQuery();

            rs.next();
            limit = rs.getInt("max_withdraw");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (amount <= bal && amount <= limit);

    }

    public boolean validateAccBal(int amount, int id) {
        // if account balance >=  amount && amount == active
        String accBalSql = "SELECT balance FROM atm_db.account where id=?";
        int bal = 0;
        try (PreparedStatement ps = conn.prepareStatement(accBalSql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            bal = rs.getInt("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String accStatus = "SELECT acc_status FROM atm_db.account where id=?";
        String status = null;
        try (PreparedStatement ps = conn.prepareStatement(accStatus)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();
            status = rs.getString("acc_status");

        } catch (SQLException e) {
           e.printStackTrace();
        }

        return (amount <= bal && Objects.equals(status, "active"));

    }



}
