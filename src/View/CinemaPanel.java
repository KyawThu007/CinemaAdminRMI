/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Controller.Service;
import Model.Cinema;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.rmi.RemoteException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class CinemaPanel extends javax.swing.JPanel {

    /**
     * Creates new form CinemaPanel
     */
    DefaultTableModel model;
    String longitude = "18.94249126042727";
    String latitude = "96.44335886756726";
    String url = "https://www.google.com/maps/embed/v1/view?key=AIzaSyDTSojQElP-SGcM3RRwgnfHk0xLRrFUsPY&center=18.94249126042727,96.44335886756726&zoom=18&maptype=satellite";

    public CinemaPanel() {
        initComponents();
        getAllCinemaAction();
        // Create a custom cell renderer to left align text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        for (int i = 0; i < cinemaTable.getColumnCount(); i++) {
            cinemaTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void mapView(String url) {
        webPanel.removeAll(); // Clear existing components
        webPanel.revalidate(); // Refresh layout
        webPanel.repaint(); // Redraw panel
        
        JFXPanel jfxPanel = new JFXPanel();
        webPanel.setPreferredSize(new Dimension(500, 392));
        webPanel.setLayout(new BorderLayout()); // Ensure proper layout
        webPanel.add(jfxPanel, BorderLayout.CENTER); // Add JFXPanel to webPanel

        
        // Initialize JavaFX content
        Platform.runLater(() -> initFX(jfxPanel, url));
    }

    private void initFX(JFXPanel jfxPanel, String url) {

        // Create the JavaFX WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load(url);

        // Create a JavaFX Scene with the WebView
        Scene scene = new Scene(webView);

        // Set the scene in the JFXPanel
        jfxPanel.setScene(scene);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jpanel = new javax.swing.JPanel();
        insertBtn = new javax.swing.JButton();
        branchField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        cityField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        locationField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cinemaTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        webPanel = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1100, 623));

        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 623));

        jpanel.setBackground(new java.awt.Color(204, 204, 204));

        insertBtn.setText("Insert");
        insertBtn.setPreferredSize(new java.awt.Dimension(80, 23));
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });

        branchField.setPreferredSize(new java.awt.Dimension(64, 23));
        branchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                branchFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Branch");

        cancelBtn.setText("Cancel");
        cancelBtn.setPreferredSize(new java.awt.Dimension(80, 23));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        cityField.setPreferredSize(new java.awt.Dimension(64, 23));
        cityField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("City");

        locationField.setPreferredSize(new java.awt.Dimension(64, 23));

        jLabel1.setText("Location");

        javax.swing.GroupLayout jpanelLayout = new javax.swing.GroupLayout(jpanel);
        jpanel.setLayout(jpanelLayout);
        jpanelLayout.setHorizontalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(branchField, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(locationField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jpanelLayout.setVerticalGroup(
            jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3))
                    .addComponent(jLabel1))
                .addGap(5, 5, 5)
                .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(branchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(locationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        cinemaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Branch", "City", "Location"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(cinemaTable);
        if (cinemaTable.getColumnModel().getColumnCount() > 0) {
            cinemaTable.getColumnModel().getColumn(0).setMinWidth(60);
            cinemaTable.getColumnModel().getColumn(0).setPreferredWidth(60);
            cinemaTable.getColumnModel().getColumn(0).setMaxWidth(60);
            cinemaTable.getColumnModel().getColumn(1).setMinWidth(120);
            cinemaTable.getColumnModel().getColumn(1).setPreferredWidth(120);
            cinemaTable.getColumnModel().getColumn(1).setMaxWidth(120);
            cinemaTable.getColumnModel().getColumn(2).setMinWidth(120);
            cinemaTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            cinemaTable.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Cinema");

        refreshBtn.setText("Refresh");
        refreshBtn.setPreferredSize(new java.awt.Dimension(80, 23));
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        updateBtn.setText("Update");
        updateBtn.setPreferredSize(new java.awt.Dimension(80, 23));
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete");
        deleteBtn.setPreferredSize(new java.awt.Dimension(80, 23));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jButton1.setText("Map");
        jButton1.setPreferredSize(new java.awt.Dimension(80, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        webPanel.setBackground(new java.awt.Color(255, 255, 255));
        webPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout webPanelLayout = new javax.swing.GroupLayout(webPanel);
        webPanel.setLayout(webPanelLayout);
        webPanelLayout.setHorizontalGroup(
            webPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );
        webPanelLayout.setVerticalGroup(
            webPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(webPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
                    .addComponent(jpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(webPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void branchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_branchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_branchFieldActionPerformed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        // TODO add your handling code here:
        cancel();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed

        // TODO add your handling code here:
        updateCinemaAction();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void cityFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityFieldActionPerformed

    private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed
        // TODO add your handling code here:
        addCinemaAction();
    }//GEN-LAST:event_insertBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
        deleteCinemaAction();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        // TODO add your handling code here:
        getAllCinemaAction();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadMapAction();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void loadMapAction() {
        int selectedRow = cinemaTable.getSelectedRow();
        // System.out.println("Selected Row => " + selectedRow);
        if (selectedRow != -1) {
            // Get data from the selected row
            mapView(cinemaTable.getValueAt(selectedRow, 3).toString());

        } else {
            // No row selected
            logMessage("Selected!");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField branchField;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTable cinemaTable;
    private javax.swing.JTextField cityField;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton insertBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpanel;
    private javax.swing.JTextField locationField;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton updateBtn;
    private javax.swing.JPanel webPanel;
    // End of variables declaration//GEN-END:variables

    public void addCinemaAction() {

        try {
            if (branchField.getText().isEmpty() || cityField.getText().isEmpty()) {
                logMessage("Fill Data!");
            } else {
                Cinema cinema = new Cinema();
                cinema.setBranch(branchField.getText());
                cinema.setCity(cityField.getText());
                cinema.setLocation(locationField.getText());
                int n = new Service().getCinemaStub().addCinema(cinema);
                if (n == 1) {
                    logMessage("Added Successful!");
                    cancel();
                    getAllCinemaAction();
                } else {
                    logMessage("Error!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        branchField.setText("");
        cityField.setText("");

    }

    public void updateCinemaAction() {
        try{
        int selectedRow = cinemaTable.getSelectedRow();
        System.out.println("Selected Row => " + selectedRow);
        if (selectedRow != -1) {
            // Get data from the selected row
            Cinema cinema = new Cinema();
            cinema.setId((int) cinemaTable.getValueAt(selectedRow, 0));
            cinema.setBranch(cinemaTable.getValueAt(selectedRow, 1).toString());
            cinema.setCity(cinemaTable.getValueAt(selectedRow, 2).toString());
            cinema.setLocation(cinemaTable.getValueAt(selectedRow, 3).toString());
            new Service().getCinemaStub().updateCinema(cinema);
            logMessage("Updated Successful!");
            getAllCinemaAction();
        } else {
            // No row selected
            logMessage("Selected!");
        }}catch(RemoteException e){}
    }

    public void deleteCinemaAction() {
        try{
        int selectedRow = cinemaTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get data from the selected row
            new Service().getCinemaStub().deleteCinema((int) cinemaTable.getValueAt(selectedRow, 0));
            logMessage("Deleted Successful!");
            getAllCinemaAction();

        } else {
            // No row selected
            logMessage("Selected!");
        }}catch(RemoteException e){}
    }

    public void getAllCinemaAction() {
        try{
        model = (DefaultTableModel) cinemaTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (Cinema cinema : new Service().getCinemaStub().getAllCinema()) {
            model.addRow(new Object[]{cinema.getId(), cinema.getBranch(), cinema.getCity(), cinema.getLocation()});
        }
        }catch(RemoteException e){}
    }

    public void logMessage(String log) {
        JOptionPane.showMessageDialog(null, log, "", JOptionPane.INFORMATION_MESSAGE);
    }

}