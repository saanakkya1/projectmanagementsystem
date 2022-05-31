package projectManagementSystem;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;
import java.io.Console;

import projectManagementSystem.Admin.Admin;
import projectManagementSystem.Manage.Manager;
import projectManagementSystem.User.User;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;

public class Main {
    static Connection con;

    static {
        try {
            con = ConnectDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
* nbeves0@uol.com.br --> manager
* pdowbakinj@scribd.com --> Admin
* kfaullh@yolasite.com --> user
*
* */

    public static String read_password() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Testing password%n");
        char[] passwordArray = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: %s%n", new String(passwordArray));
        return new String(passwordArray);
    }
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        boolean passw = true;
        while (true) {
            if(passw)System.out.println("Enter Email-Id:");
            passw = true;
String email ="nbeves0@uol.com.br";// sc.nextLine();
            String sql = "Select * from user where email=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.next());
            if (rs.next()) {
                while (true) {
                    System.out.println("Enter password");
String pass = "Beves@Nevile";//1sc.nextLine();//read_password();
                    String sql1 = "Select * from user where email=? and password=?";
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    stmt1.setString(1, email);
                    stmt1.setString(2, pass);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        System.out.println("Logged In as:\t" + rs1.getString("first_name"));
                        switch (rs1.getInt("role_id")) {
                            case 1 -> {
                                User.main();
                            }
                            case 2 -> {
                                Manager.main(rs1.getInt("user_id"));
                            }
                            case 3 -> {
                                Admin.main();
                            }
                        }

                        break;
                    } else {
                        System.out.println("Incorrect password Re-Enter Password Again...");
                    }
                }

            }
                else{
                    System.out.println("Enter a valid Email-Id:");
                   passw =false;
                }


        }
    }


    }

