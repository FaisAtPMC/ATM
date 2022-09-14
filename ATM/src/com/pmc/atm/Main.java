package com.pmc.atm;

import com.pmc.atm.service.Functions;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Functions fn = new Functions();
        fn.processHeading(sc.nextLine());
    }

}
