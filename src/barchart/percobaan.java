/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barchart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class percobaan extends JPanel {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/db_name";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private int[] values;
    private String[] categories;

    public percobaan() {
        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "gilang", "010203")) {
            String query = "SELECT tanggal, COUNT(costomer) as jumlah from transaksi GROUP by tanggal;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int count = 0;
            while (resultSet.next()) {
                count++;
            }

            values = new int[count];
            categories = new String[count];

            resultSet.beforeFirst();
            int index = 0;
            while (resultSet.next()) {
                String category = resultSet.getString("tanggal");
                int value = resultSet.getInt("jumlah");
                categories[index] = category;
                values[index] = value;
                index++;
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLUE);

        int maxValue = getMaxValue();
        int xSpacing = getWidth() / (values.length - 1);
        int ySpacing = getHeight() / maxValue;

        for (int i = 0; i < values.length - 1; i++) {
            int x1 = i * xSpacing;
            int y1 = getHeight() - values[i] * ySpacing;
            int x2 = (i + 1) * xSpacing;
            int y2 = getHeight() - values[i + 1] * ySpacing;

            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();
    }

    private int getMaxValue() {
        int maxValue = Integer.MIN_VALUE;
        for (int value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
    
        percobaan chart = new percobaan();

        JFrame frame = new JFrame("Line Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chart);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}














