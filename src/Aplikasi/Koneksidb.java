/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

/**
 *
 * @author gprak
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Koneksidb {

    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/dbbengkel";
                String user = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
                System.out.println("berhasil");
            } catch (SQLException e) {
                System.out.println("error");
            }
        }
        return koneksi;
    }
    public static void main(String args[]){
        getKoneksi();
    }
}
