/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import javax.swing.JFrame;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author gprak
 */
public class Frame_dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Frame_dashboard
     */
    public Connection con;
    public Statement st;
    public ResultSet rs;
    Connection cn = Aplikasi.Koneksidb.getKoneksi();
    public DefaultTableModel modelStok;
    public DefaultTableModel modelTransaksi;
    public DefaultTableModel modelList;
    public DefaultTableModel modelMekanik;
    public DefaultTableModel modelService;
    public JLabel totaldata;
    public JLabel admin;
    public JTextField admintx;
    public Frame_dashboard() {
        initComponents();
        
        String[] headerService = {"id_service","tanggal","Jenis Service","Engineer","Total","Admin"};
        modelService = new DefaultTableModel(headerService,0);
        tabel_service.setModel(modelService);
        tabel_service.setBackground(Color.WHITE);
        tabel_service.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 24));
        tabel_service.getTableHeader().setDefaultRenderer(new HeaderColor());
        tabel_service.setRowHeight(40);  
        tampilService();
        
        
        String[] headermekanik = {"id Engineer","Nama Engineer","Alamat","Gaji"};
        modelMekanik = new DefaultTableModel(headermekanik,0);
        tabelEngineer.setModel(modelMekanik);
        tabelEngineer.setBackground(Color.WHITE);
        tabelEngineer.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 24));
        tabelEngineer.getTableHeader().setDefaultRenderer(new HeaderColor());
        tabelEngineer.setRowHeight(100);   
        tampilEngineer();
        
        
        
        
        
        String[] headerList = {"Kode","Nama Barang","Harga satuan"};
        modelList = new DefaultTableModel(headerList,0);
        TabelListSperpart.setModel(modelList);
        TabelListSperpart.setBackground(Color.WHITE);
        TabelListSperpart.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 15));
        TabelListSperpart.getTableHeader().setDefaultRenderer(new HeaderColor());
        TabelListSperpart.setRowHeight(40);        
        tampilList();
        
        
       
        
        
        // ini table sperpart
        String[] headerStok = {"Kode barang","nama barang","Harga","Quantiti","Kategori"};
        modelStok = new DefaultTableModel (headerStok,0);
        tableStok.setModel(modelStok);
        tableStok.setBackground(Color.WHITE);
        tableStok.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 15));
        tableStok.getTableHeader().setDefaultRenderer(new HeaderColor());
        tableStok.setRowHeight(40);
        TableColumnModel columnModel = tableStok.getColumnModel();
        tampilDataStok();
       
        
        
        
        //ini tabel transaksi
        
        
        String[] headerTransaksi = {"Id Transaksi","Tanggal","Qty","Nama Barang","Total Harga","admin",};
        modelTransaksi = new DefaultTableModel(headerTransaksi,0);
        tableTransaksi.setModel(modelTransaksi);
        tableTransaksi.setBackground(Color.WHITE);
        tableTransaksi.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD, 15));
        tableTransaksi.getTableHeader().setDefaultRenderer(new HeaderColor());
        tableTransaksi.setRowHeight(40);
        TableColumnModel columnModel2 = tableTransaksi.getColumnModel();
        TableColumn firstColumn2 = columnModel2.getColumn(3);
        firstColumn2.setPreferredWidth(200);
        tampilDataTransaksi();

        
        
        totaldata = new JLabel();
        labeldata.add(totaldata);
        dataPendapatan();
        
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setVisible(true);
        setResizable(false);
    }

     public class HeaderColor extends DefaultTableCellRenderer {

        public HeaderColor() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
           
           setBackground(new java.awt.Color(0, 204, 255));
           
//you can change the color that u want 
            return this;
        }

    }
     
     private void tampilService(){
         modelService.setRowCount(0);
         Koneksidb classKoneksi = new Koneksidb();
        try {
            con = Koneksidb.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM service");
           
            while(rs.next()){
                String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(7)};
                modelService.addRow(row);
                
            }
            tabelEngineer.setModel(modelService);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        }
     }
     
     private void tampilEngineer(){
        modelMekanik.setRowCount(0);
        Koneksidb classKoneksi = new Koneksidb();
        try {
            con = Koneksidb.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM mechanic");
           
            while(rs.next()){
                String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
                modelMekanik.addRow(row);
                
            }
            tabelEngineer.setModel(modelMekanik);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        }
         
     }
    
    
    //data sperpart
    private void tampilList(){
        modelList.setRowCount(0);
        Koneksidb classKoneksi = new Koneksidb();
        try {
            con = Koneksidb.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT kode_brg, nama_brg,Harga FROM sperpart");
           
            while(rs.next()){
                String[] row = {rs.getString(1),rs.getString(2),rs.getString(3)};
                modelList.addRow(row);
                
            }
            TabelListSperpart.setModel(modelList);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        }
  
    }
    
    
    private void tampilDataStok(){
        modelStok.setRowCount(0);
        Koneksidb classKoneksi = new Koneksidb();
        try {
            con = Koneksidb.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM sperpart");
           
            while(rs.next()){
                String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
                modelStok.addRow(row);
                
            }
            tableStok.setModel(modelStok);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
    
    //data transaksi
        private void tampilDataTransaksi(){
            modelTransaksi.setRowCount(0);
            
        Koneksidb classKoneksi = new Koneksidb();
        try {
            con = Koneksidb.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM transaksi");
            ResultSetMetaData metaData = rs.getMetaData();
           
            
            while(rs.next()){
                String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(7)};
                modelTransaksi.addRow(row);
                
            }
            tableTransaksi.setModel(modelTransaksi);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        } 
        
    }
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BodyPanel = new javax.swing.JPanel();
        MenuPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        PanelAdmin = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        labeldata = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        transaksiPane = new javax.swing.JPanel();
        list = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        panelpilihan = new javax.swing.JPanel();
        SperpartPane = new javax.swing.JPanel();
        nama_barang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        totalHarga = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        quantity = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        text_tanggal = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelListSperpart = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        kode_barang = new javax.swing.JTextField();
        ServicePane = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        engineer = new javax.swing.JComboBox<>();
        jenis_service = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        total_service = new javax.swing.JTextField();
        jButton16 = new javax.swing.JButton();
        text_tanggal2 = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        RiwayatTransaksiPane = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        HapusTransaksi = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        ubah_qty = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        ubah_nama = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        ubah_harga = new javax.swing.JTextField();
        ubah_id = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        RiwayatServicePane = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabel_service = new javax.swing.JTable();
        StokPane = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStok = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        kode_stok = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tambahan = new javax.swing.JTextField();
        EngineerPane = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelEngineer = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("aplikasi bengkel GMN");

        BodyPanel.setBackground(new java.awt.Color(217, 217, 217));

        MenuPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-roda-01.png"))); // NOI18N
        MenuPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 640, 60, 60));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-sparepart-03.png"))); // NOI18N
        MenuPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 580, 50, 40));

        jLabel4.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stok Sparepart");
        MenuPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 570, 200, 50));

        jButton1.setBackground(new java.awt.Color(85, 85, 85));
        jButton1.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setAlignmentX(0.5F);
        jButton1.setBorder(null);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 290, 60));

        jButton2.setBackground(new java.awt.Color(85, 85, 85));
        jButton2.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home-icon-04.png"))); // NOI18N
        jButton2.setText("Dashboard");
        jButton2.setBorder(null);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 290, 60));

        jButton3.setBackground(new java.awt.Color(85, 85, 85));
        jButton3.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tambah-01.png"))); // NOI18N
        jButton3.setText("Transaksi");
        jButton3.setBorder(null);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 290, 60));

        jButton4.setBackground(new java.awt.Color(85, 85, 85));
        jButton4.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_list-01.png"))); // NOI18N
        jButton4.setText("Riwayat Transaksi");
        jButton4.setBorder(null);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 290, 60));

        jButton5.setBackground(new java.awt.Color(85, 85, 85));
        jButton5.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("      Data Mechanic");
        jButton5.setBorder(null);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 640, 290, 60));

        jButton6.setBackground(new java.awt.Color(85, 85, 85));
        jButton6.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_list-01.png"))); // NOI18N
        jButton6.setText("Riwayat Service");
        jButton6.setBorder(null);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 290, 60));

        jButton14.setBackground(new java.awt.Color(85, 85, 85));
        jButton14.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("              Log Out");
        jButton14.setBorder(null);
        jButton14.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        MenuPanel.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 706, 290, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ui_Component/UI_MENU.png"))); // NOI18N
        MenuPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        PanelAdmin.setBackground(new java.awt.Color(255, 255, 255));
        PanelAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelAdmin.setFont(new java.awt.Font("Ebrima", 0, 36)); // NOI18N
        labelAdmin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        PanelAdmin.add(labelAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 200, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ui_Component/pp_kosong.png"))); // NOI18N
        PanelAdmin.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 80, 70));

        MainPanel.setBackground(new java.awt.Color(255, 255, 255));
        MainPanel.setLayout(new java.awt.CardLayout());

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel7.setText("Dashboard");

        jPanel6.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(9, 150, 23));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jButton8.setBackground(new java.awt.Color(9, 150, 23));
        jButton8.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Grafik Pendapatan");
        jButton8.setBorder(new javax.swing.border.MatteBorder(null));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Pendapat Minggu ini:");

        labeldata.setBackground(new java.awt.Color(0, 0, 0));
        labeldata.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        labeldata.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Rp.");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labeldata, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labeldata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 189, 89));

        jButton9.setBackground(new java.awt.Color(255, 189, 89));
        jButton9.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 0, 0));
        jButton9.setText("Grafik Costomer");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Index costomer:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Ebrima", 0, 36)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 388, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashboardPanelLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        MainPanel.add(dashboardPanel, "card2");

        transaksiPane.setBackground(new java.awt.Color(255, 255, 255));

        list.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout listLayout = new javax.swing.GroupLayout(list);
        list.setLayout(listLayout);
        listLayout.setHorizontalGroup(
            listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        listLayout.setVerticalGroup(
            listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel9.setText("Transaksi");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton10.setText("Sperpart");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jButton11.setText("Service");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelpilihan.setLayout(new java.awt.CardLayout());

        SperpartPane.setBackground(new java.awt.Color(255, 255, 204));

        nama_barang.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        nama_barang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nama_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nama_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_barangActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel17.setText("Total Harga(Rp)");

        jLabel18.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel18.setText("Kode barang");

        totalHarga.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        totalHarga.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        totalHarga.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        totalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalHargaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel19.setText("Quantity");

        quantity.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        quantity.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantity.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityActionPerformed(evt);
            }
        });

        btn_tambah.setBackground(new java.awt.Color(0, 153, 0));
        btn_tambah.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(255, 0, 51));
        btn_reset.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel20.setText("Tanggal");

        text_tanggal.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N

        TabelListSperpart.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        TabelListSperpart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        TabelListSperpart.setRowHeight(40);
        jScrollPane3.setViewportView(TabelListSperpart);

        jLabel27.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jLabel27.setText("Nama Barang");

        kode_barang.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        kode_barang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        kode_barang.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout SperpartPaneLayout = new javax.swing.GroupLayout(SperpartPane);
        SperpartPane.setLayout(SperpartPaneLayout);
        SperpartPaneLayout.setHorizontalGroup(
            SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SperpartPaneLayout.createSequentialGroup()
                .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SperpartPaneLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SperpartPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(nama_barang, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addGroup(SperpartPaneLayout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btn_reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(text_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kode_barang))
                .addGap(48, 48, 48)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        SperpartPaneLayout.setVerticalGroup(
            SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SperpartPaneLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SperpartPaneLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(totalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(text_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(SperpartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelpilihan.add(SperpartPane, "card3");

        ServicePane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel30.setText("Jenis Service");

        engineer.setBackground(new java.awt.Color(255, 255, 255));
        engineer.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        engineer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Asep Surasep", "Muklis muwarna", "Fadil Angga", "Portal abwaba" }));
        engineer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jenis_service.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jenis_service.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel31.setText("Engineer");

        jLabel32.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel32.setText("Total Pembayaran(Rp)");

        total_service.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        total_service.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton16.setBackground(new java.awt.Color(102, 153, 0));
        jButton16.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Tambah");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        text_tanggal2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        text_tanggal2.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel33.setText("Tanggal");

        javax.swing.GroupLayout ServicePaneLayout = new javax.swing.GroupLayout(ServicePane);
        ServicePane.setLayout(ServicePaneLayout);
        ServicePaneLayout.setHorizontalGroup(
            ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServicePaneLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(total_service)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(engineer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jenis_service))
                .addGap(101, 101, 101)
                .addGroup(ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(text_tanggal2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(290, Short.MAX_VALUE))
        );
        ServicePaneLayout.setVerticalGroup(
            ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServicePaneLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(text_tanggal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jenis_service, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(engineer, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_service, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        panelpilihan.add(ServicePane, "card2");

        javax.swing.GroupLayout transaksiPaneLayout = new javax.swing.GroupLayout(transaksiPane);
        transaksiPane.setLayout(transaksiPaneLayout);
        transaksiPaneLayout.setHorizontalGroup(
            transaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelpilihan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(transaksiPaneLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        transaksiPaneLayout.setVerticalGroup(
            transaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelpilihan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(transaksiPane, "card3");

        RiwayatTransaksiPane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel10.setText("Riwayat Transaksi");

        jPanel4.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        tableTransaksi.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableTransaksi.setFocusable(false);
        tableTransaksi.setGridColor(new java.awt.Color(51, 0, 51));
        tableTransaksi.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tableTransaksi.setRowHeight(40);
        tableTransaksi.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tableTransaksi.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tableTransaksi.setShowVerticalLines(false);
        tableTransaksi.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableTransaksi);

        jPanel8.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Hapus Transaksi");

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel21.setText("Id Transaksi ");

        HapusTransaksi.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jButton12.setBackground(new java.awt.Color(255, 51, 0));
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Hapus");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Ebrima", 1, 24)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Ubah Transaksi");

        jLabel23.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jLabel23.setText("Id Transaksi yg diubah");

        jLabel24.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jLabel24.setText("Quantity");

        ubah_qty.setFont(new java.awt.Font("Ebrima", 0, 15)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jLabel25.setText("Nama Barang");

        ubah_nama.setFont(new java.awt.Font("Ebrima", 0, 15)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        jLabel26.setText("Total Harga (Rp)");

        ubah_harga.setFont(new java.awt.Font("Ebrima", 0, 15)); // NOI18N

        ubah_id.setFont(new java.awt.Font("Ebrima", 0, 15)); // NOI18N

        jButton13.setBackground(new java.awt.Color(102, 153, 0));
        jButton13.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Update");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(255, 255, 0));
        jButton15.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jButton15.setText("Reset");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RiwayatTransaksiPaneLayout = new javax.swing.GroupLayout(RiwayatTransaksiPane);
        RiwayatTransaksiPane.setLayout(RiwayatTransaksiPaneLayout);
        RiwayatTransaksiPaneLayout.setHorizontalGroup(
            RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(HapusTransaksi)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ubah_id, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ubah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(ubah_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(59, 59, 59))
                                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(ubah_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        RiwayatTransaksiPaneLayout.setVerticalGroup(
            RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HapusTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ubah_id, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(2, 2, 2)
                                .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ubah_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ubah_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ubah_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(RiwayatTransaksiPaneLayout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(RiwayatTransaksiPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(RiwayatTransaksiPane, "card4");

        RiwayatServicePane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel11.setText("Riwayat Service");

        jPanel3.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        tabel_service.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        tabel_service.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tabel_service);

        javax.swing.GroupLayout RiwayatServicePaneLayout = new javax.swing.GroupLayout(RiwayatServicePane);
        RiwayatServicePane.setLayout(RiwayatServicePaneLayout);
        RiwayatServicePaneLayout.setHorizontalGroup(
            RiwayatServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(RiwayatServicePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RiwayatServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        RiwayatServicePaneLayout.setVerticalGroup(
            RiwayatServicePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RiwayatServicePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );

        MainPanel.add(RiwayatServicePane, "card5");

        StokPane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel12.setText("Stok Sparepart");

        jPanel2.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 894, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        tableStok.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableStok.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        tableStok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode barang", "Nama barang", "Harga", "Quantiti", "Kategori"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableStok.setRowHeight(30);
        jScrollPane2.setViewportView(tableStok);

        jButton7.setBackground(new java.awt.Color(255, 0, 51));
        jButton7.setFont(new java.awt.Font("Ebrima", 0, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Tambah Stok");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setText("kode barang Stok");

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel29.setText("Tambah Stok");

        tambahan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout StokPaneLayout = new javax.swing.GroupLayout(StokPane);
        StokPane.setLayout(StokPaneLayout);
        StokPaneLayout.setHorizontalGroup(
            StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(StokPaneLayout.createSequentialGroup()
                .addGroup(StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StokPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(StokPaneLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(StokPaneLayout.createSequentialGroup()
                                .addGroup(StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(StokPaneLayout.createSequentialGroup()
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(StokPaneLayout.createSequentialGroup()
                                        .addComponent(kode_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(tambahan, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(43, 43, 43))))
                    .addGroup(StokPaneLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        StokPaneLayout.setVerticalGroup(
            StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StokPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StokPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kode_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        MainPanel.add(StokPane, "card6");

        EngineerPane.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        jLabel13.setText("Data Mechanic");

        jPanel1.setBackground(new java.awt.Color(217, 217, 217));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        tabelEngineer.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        tabelEngineer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tabelEngineer);

        javax.swing.GroupLayout EngineerPaneLayout = new javax.swing.GroupLayout(EngineerPane);
        EngineerPane.setLayout(EngineerPaneLayout);
        EngineerPaneLayout.setHorizontalGroup(
            EngineerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(EngineerPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
        );
        EngineerPaneLayout.setVerticalGroup(
            EngineerPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EngineerPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        MainPanel.add(EngineerPane, "card7");

        javax.swing.GroupLayout BodyPanelLayout = new javax.swing.GroupLayout(BodyPanel);
        BodyPanel.setLayout(BodyPanelLayout);
        BodyPanelLayout.setHorizontalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        BodyPanelLayout.setVerticalGroup(
            BodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BodyPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(PanelAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        // remove panel
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(dashboardPanel);
        MainPanel.repaint();
        MainPanel.revalidate();
        dataPendapatan();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        //remove panell
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(transaksiPane);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //remove panel
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(RiwayatTransaksiPane);
        MainPanel.repaint();
        MainPanel.revalidate();
        tampilDataTransaksi();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        
                //remove panel
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(RiwayatServicePane);
        MainPanel.repaint();
        MainPanel.revalidate();
        tampilService();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
                //remove panel
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(StokPane);
        MainPanel.repaint();
        MainPanel.revalidate();
        tampilDataStok();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
                //remove panel
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //add panel selanjutnya
        MainPanel.add(EngineerPane);
        MainPanel.repaint();
        MainPanel.revalidate();
        tampilEngineer();
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    
        String jdbcURL = "jdbc:mysql://localhost:3306/db_name";
        String username = "username";
        String password = "password";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")) {
            String query = "SELECT tanggal, sum(Total_harga) as jumlah from transaksi GROUP by tanggal;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (resultSet.next()) {
                String category = resultSet.getString("Tanggal");
                int value = resultSet.getInt("jumlah");
                dataset.addValue(value, "index Pendapatan Harian", category);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Index Pendapatan Harian",
                    "tanggal",
                    "Total_harga",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.BLACK);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.BLUE);

            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLowerMargin(0.05);
            domainAxis.setUpperMargin(0.05);

            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            JFrame frame = new JFrame("Barchart costomer");
            frame.setSize(1000, 400);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        
        String jdbcURL = "jdbc:mysql://localhost:3306/db_name";
        String username = "username";
        String password = "password";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")) {
            String query = "SELECT tanggal, COUNT(costomer) as jumlah from transaksi GROUP by tanggal;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            while (resultSet.next()) {
                String category = resultSet.getString("Tanggal");
                int value = resultSet.getInt("jumlah");
                dataset.addValue(value, "index costomer per tanggal", category);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "Index Pcostomer harian",
                    "tanggal",
                    "costomer",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setBackgroundPaint(Color.WHITE);
            plot.setRangeGridlinePaint(Color.BLACK);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.RED);

            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLowerMargin(0.05);
            domainAxis.setUpperMargin(0.05);

            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            JFrame frame = new JFrame("Barchart costomer");
            frame.setSize(1000, 400);

            ChartPanel chartPanel = new ChartPanel(chart);
            frame.getContentPane().add(chartPanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        
        panelpilihan.removeAll();
        panelpilihan.repaint();
        panelpilihan.revalidate();
        
        //add panel selanjutnya
        panelpilihan.add(SperpartPane);
        panelpilihan.repaint();
        panelpilihan.revalidate();
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        panelpilihan.removeAll();
        panelpilihan.repaint();
        panelpilihan.revalidate();
        
        //add panel selanjutnya
        panelpilihan.add(ServicePane);
        panelpilihan.repaint();
        panelpilihan.revalidate();
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void nama_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_barangActionPerformed

    private void totalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalHargaActionPerformed

    private void quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityActionPerformed
    
    
    String tanggal, jumlah,Nama_barang,Total_Harga,adminz,kode;
   
    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        try {
            Date date = text_tanggal.getDate();
            DateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd");
            this.tanggal = dateformat.format(date);
            
           kode = kode_barang.getText();
           jumlah = quantity.getText();
           Nama_barang = nama_barang.getText();
           Total_Harga = totalHarga.getText();
           JLabel admin = new JLabel();
           labelAdmin.add(admin);
           adminz = labelAdmin.getText();
            
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        String sql = "insert into transaksi values (null,?,?,?,?,1,?)";
        String sql2 = "UPDATE sperpart SET QTY = QTY - ? WHERE kode_brg = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")){
          
            
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, tanggal);
            statement.setString(2, jumlah);
            statement.setString(3, Nama_barang);
            statement.setString(4, Total_Harga);
            statement.setString(5, adminz);
            
            int rowinsert = statement.executeUpdate();
            
            if(rowinsert > 0){
                JOptionPane.showMessageDialog(null, "data berhasil disimpan");
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                statement2.setString(1, jumlah);
                statement2.setString(2, kode);
                
                int rowinsert2 = statement2.executeUpdate();
            }
            
            
            
            
            
            //JOptionPane.showConfirmDialog(null, "data berhasil disimpan");
            
          
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
           kode_barang.setText("");
           nama_barang.setText("");
           quantity.setText("");
           totalHarga.setText("");
           
    }//GEN-LAST:event_btn_resetActionPerformed
    String hapus;
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        hapus = HapusTransaksi.getText();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")){
         
            String sql = "DELETE FROM transaksi WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, hapus);
            int rowinsert = statement.executeUpdate();
            
            if(rowinsert > 0){
                JOptionPane.showMessageDialog(null, "data berhasil dihapus");
                tampilDataTransaksi();
            }
            
            
            
            
            
            //JOptionPane.showConfirmDialog(null, "data berhasil disimpan");
            
          
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        kode = ubah_id.getText();
        jumlah = ubah_qty.getText();
        Nama_barang = ubah_nama.getText();
        Total_Harga = ubah_harga.getText();
        JLabel admin = new JLabel();
        labelAdmin.add(admin);
        adminz = labelAdmin.getText();
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")) {
            
            String sql = "UPDATE transaksi SET QTY = ?, nama_barang = ?, Total_harga = ? ,admin = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, jumlah);
            statement.setString(2, Nama_barang);
            statement.setString(3, Total_Harga);
             statement.setString(4, adminz);
            statement.setString(5, kode);
            int rowinsert = statement.executeUpdate();
            
            if(rowinsert > 0){
                JOptionPane.showMessageDialog(null, "data berhasil diubah");
                tampilDataTransaksi();
            }
            
            
            
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        Frame_login balik = new Frame_login();
        balik.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        ubah_id.setText("");
        ubah_qty.setText("");
        ubah_nama.setText("");
        ubah_harga.setText("");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        kode = kode_stok.getText();
        jumlah = tambahan.getText();
        
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")) {
            
            String sql = "UPDATE sperpart SET QTY = QTY + ? WHERE kode_brg = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, jumlah);
            statement.setString(2, kode);


            int rowinsert = statement.executeUpdate();
            
            if(rowinsert > 0){
                JOptionPane.showMessageDialog(null, "stok berhasil ditambah");
                tampilDataStok();
            }
            
            
            
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton7ActionPerformed
    String selectedEngineer,jenis,Total;
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        Date date = text_tanggal2.getDate();
        DateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd");
        this.tanggal = dateformat.format(date);
        selectedEngineer = (String)engineer.getSelectedItem();
        jenis =  jenis_service.getText();
        Total = total_service.getText();
        JLabel admin = new JLabel();
        labelAdmin.add(admin);
        adminz = labelAdmin.getText();
        
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")) {
            
            String sql = "insert into service values (null,?,?,?,?,1,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            
            statement.setString(1, tanggal);
            statement.setString(2, jenis);
            statement.setString(3, selectedEngineer);
            statement.setString(4, Total);
            statement.setString(5, adminz);
            
            


            int rowinsert = statement.executeUpdate();
            
            if(rowinsert > 0){
                JOptionPane.showMessageDialog(null, "Service berhasil ditambah");
                
            }
            
            
            
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
       
    }//GEN-LAST:event_jButton16ActionPerformed
        
   
    
    private void dataPendapatan() {
                Koneksidb classKoneksi = new Koneksidb();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbengkel", "root", "")){
            
            String query = "SELECT sum(Total_harga) as total from transaksi";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
           
            
            
            if (resultSet.next()){
                String data = resultSet.getString("total");
                
                labeldata.setText(data);
                
            }
            tableTransaksi.setModel(modelTransaksi);
        }catch(SQLException ex) {
            System.out.print(ex.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(Frame_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_dashboard().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BodyPanel;
    private javax.swing.JPanel EngineerPane;
    private javax.swing.JTextField HapusTransaksi;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JPanel PanelAdmin;
    private javax.swing.JPanel RiwayatServicePane;
    private javax.swing.JPanel RiwayatTransaksiPane;
    private javax.swing.JPanel ServicePane;
    private javax.swing.JPanel SperpartPane;
    private javax.swing.JPanel StokPane;
    private javax.swing.JTable TabelListSperpart;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JComboBox<String> engineer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jenis_service;
    private javax.swing.JTextField kode_barang;
    private javax.swing.JTextField kode_stok;
    public static final javax.swing.JLabel labelAdmin = new javax.swing.JLabel();
    private javax.swing.JLabel labeldata;
    private javax.swing.JPanel list;
    private javax.swing.JTextField nama_barang;
    private javax.swing.JPanel panelpilihan;
    private javax.swing.JTextField quantity;
    private javax.swing.JTable tabelEngineer;
    private javax.swing.JTable tabel_service;
    private javax.swing.JTable tableStok;
    private javax.swing.JTable tableTransaksi;
    private javax.swing.JTextField tambahan;
    private com.toedter.calendar.JDateChooser text_tanggal;
    private com.toedter.calendar.JDateChooser text_tanggal2;
    private javax.swing.JTextField totalHarga;
    private javax.swing.JTextField total_service;
    private javax.swing.JPanel transaksiPane;
    private javax.swing.JTextField ubah_harga;
    private javax.swing.JTextField ubah_id;
    private javax.swing.JTextField ubah_nama;
    private javax.swing.JTextField ubah_qty;
    // End of variables declaration//GEN-END:variables
}
