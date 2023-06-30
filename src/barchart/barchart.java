/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barchart;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;


/**
 *
 * @author gprak
 */
public class barchart extends JPanel{
    public barchart(){
        this.setPreferredSize(new Dimension(750, 360));
        this.setBackground(Color.WHITE);
    }
    protected void paintComponent(Graphics c) {
        super.paintComponent(c);
        Graphics2D c2 = (Graphics2D) c;
        
        GeneralPath cv = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        
        //garis tegak atau Y
        cv.moveTo(500, 10);
        cv.lineTo(500, 265);
        
        //garis X

        cv.moveTo(30, 250);
        cv.lineTo(500, 250);
        cv.moveTo(30, 10);
        cv.lineTo(500, 10);
        cv.moveTo(30, 50);
        cv.lineTo(500, 50);
        cv.moveTo(30, 90);
        cv.lineTo(500, 90);
        cv.moveTo(30, 130);
        cv.lineTo(500, 130);
        cv.moveTo(30, 170);
        cv.lineTo(500, 170);
        cv.moveTo(30, 210);
        cv.lineTo(500, 210);

        
        cv.closePath();
        c2.draw(cv);
        
        //membuat no skala
        
        c2.drawString("30", 13, 10);
        c2.drawString("25", 13, 50);
        c2.drawString("20", 13, 90);
        c2.drawString("15", 13, 130);
        c2.drawString("10", 13, 170);
        c2.drawString("5", 13, 210);
        c2.drawString("0", 13, 250);
        
        
        //no urut grafik
        
        c2.drawString("2 juni", 75, 265);
        c2.drawString("3 juni", 150, 265);
        c2.drawString("4 juni", 225, 265);
        c2.drawString("5 juni", 300, 265);
        c2.drawString("6 juni", 375, 265);
        c2.drawString("7 juni", 450, 265);
        
        //grafik
        
       Shape g1 = new Rectangle2D.Double(75,210,40,40);
       Shape g2 = new Rectangle2D.Double(150,150,40,100);
       Shape g3 = new Rectangle2D.Double(220,100,40,150);
       Shape g4 = new Rectangle2D.Double(300,200,40,50);
       Shape g5 = new Rectangle2D.Double(375,10,40,240);
       Shape g6 = new Rectangle2D.Double(450,50,40,200);
       
     
       
       
       //kasih warna
       c2.setColor(Color.green);
       c2.fill(g1);
       c2.fill(g2);
       c2.fill(g3);
       c2.fill(g4);
       c2.fill(g5);
       c2.fill(g6);
       
       
    }
    
    
    //membuat method pemanggilan program
    
    public static void main(String[]args){
        String jdbcURL = "jdbc:mysql://localhost:3306/db_name";
        String username = "username";
        String password = "password";

        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "gilang", "010203")) {
            String query = "SELECT tanggal, COUNT(costomer) as jumlah from transaksi GROUP by tanggal;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (resultSet.next()) {
                String category = resultSet.getString("tanggal");
                int value = resultSet.getInt("jumlah");
                dataset.addValue(value, "index costomer", category);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "index costomer",
                    "tanggal",
                    "costomer",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            chart.setBackgroundPaint(Color.WHITE);

            ChartPanel chartPanel = new ChartPanel(chart);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(chartPanel, BorderLayout.CENTER);
            frame.setContentPane(panel);

            frame.pack();
            frame.setVisible(true);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        
    }
}
