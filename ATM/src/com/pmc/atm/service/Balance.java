package com.pmc.atm.service;

import com.pmc.atm.model.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Balance {
    Connection conn = DbConnection.getConnection("jdbc:mysql://localhost:3306/atm_db", "root", "Fk@000143");
    public Balance() throws SQLException {
    }

    void showCurrentBalance(int id) {
        String sql = "SELECT account.balance FROM atm_db.account where id=?";
        try (PreparedStatement showBal = conn.prepareStatement(sql)) {
            showBal.setInt(1, id);
            ResultSet rs = showBal.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " Rs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showBalance(String sql) {
        Scanner sc = new Scanner(System.in);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sc.nextLine());
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                System.out.println(rs.getString(1) + " Rs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAtmBalance() {
        System.out.print("Which Branch : ");
        String sql = "SELECT balance FROM atm_db.atm where atm_name=?";
        showBalance(sql);
    }

    public void showCustomerBalance() {

        System.out.print("Enter Your Account ID : ");
        String sql = "SELECT account.balance FROM atm_db.account where id=?";
        showBalance(sql);
    }


}
