    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author gprak
 */

public class Frame_login extends javax.swing.JFrame {

  

    /**
     * Creates new form Frame_login
     */
    public Frame_login() {
        
        
        initComponents();
    }
    
    String usser2;
    
    public String getkirimData(){
     usser2 = txtuser.getText();
        return null;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelLogin = new javax.swing.JPanel();
        txtuser = new javax.swing.JTextField();
        txtpw = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(84, 84, 84));

        PanelLogin.setBackground(new java.awt.Color(84, 84, 84));
        PanelLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtuser.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        txtuser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtuser.setBorder(null);
        PanelLogin.add(txtuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, 500, 50));

        txtpw.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        txtpw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpw.setBorder(null);
        PanelLogin.add(txtpw, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, 500, 60));

        jButton1.setBackground(new java.awt.Color(9, 150, 23));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jButton1.setText("LOGIN");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        PanelLogin.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 400, 100));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Password:");
        PanelLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, 110, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username:");
        PanelLogin.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 110, -1));

        jLabel3.setFont(new java.awt.Font("High Tower Text", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("GMN BENGKEL");
        PanelLogin.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 480, 80));

        jButton2.setBackground(new java.awt.Color(255, 255, 0));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton2.setText("Tambah admin");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        PanelLogin.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 640, 400, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String usser =txtuser.getText();
        String passs= txtpw.getText();
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel","root","")){
            String query = "SELECT * FROM akun WHERE username = ? and pasword = MD5(?)";
	    PreparedStatement statement = conn.prepareStatement(query);
	    statement.setString(1, usser);
	    statement.setString(2, passs);
     	    ResultSet resultSet = statement.executeQuery();
                
                if(resultSet.next()) {
      
                    Frame_dashboard coba = new Frame_dashboard();
                    coba.setVisible(true);
                    dispose();
                    Frame_dashboard.labelAdmin.setText(resultSet.getString(2));
                    
                }
                else {
                    JOptionPane.showMessageDialog(this, "Wrong username and password");
                }
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "connection errors!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        AkunAdmin bikin = new AkunAdmin();
        bikin.setLocationRelativeTo(null);
        bikin.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelLogin;
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtpw;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}

