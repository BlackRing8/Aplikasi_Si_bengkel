/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barchart;

/**
 *
 * @author gprak
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;

@SuppressWarnings("serial")

public class barchart_2 extends JFrame {
    
    
private JPanel contentPane;
    
public barchart_2() {
    
    super("Grafik garis nilai Mahasiswa");
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 685, 429);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    setLocationRelativeTo(null);
}

public static void main(String[] args) {
    
    EventQueue.invokeLater(new Runnable() {
        
        public void run() {
            
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                barchart_2 frame = new barchart_2();
                frame.setVisible(true);
                String query = "SELECT tanggal, COUNT(costomer) as jumlah from transaksi GROUP by tanggal;";
                JDBCCategoryDataset data = new JDBCCategoryDataset("jdbc:mysql://localhost:3306/dbbengkel","com.mysql.jdbc.Driver","root","");
                data.executeQuery(query);
                JFreeChart chart = ChartFactory.createLineChart("index costomer", "tanggal", "costomer", data,PlotOrientation.VERTICAL,true, true, false);
                ChartPanel cPanel = new ChartPanel(chart);
                frame.setContentPane(cPanel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    
}

}
