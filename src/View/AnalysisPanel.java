/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Controller.Service;
import Controller.SharedData;
import Model.Cinema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author DELL
 */
public class AnalysisPanel extends javax.swing.JPanel {

    /**
     * Creates new form AnalysisPanel
     */
    private String[] genreList;
    private String[] monthList;
    private int cinema_id;

    public AnalysisPanel() {
        initComponents();
        SharedData sharedData = SharedData.getInstance();

        genreList = sharedData.getGenreList();
        monthList = sharedData.getMonthList();
        monthBox1.addItem("All");
        monthBox3.addItem("All");
        for (String monthList1 : monthList) {
            monthBox1.addItem(monthList1);
            monthBox3.addItem(monthList1);
        }

        LocalDate date = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //String getMonthName = date.getMonth().name().toLowerCase();
        int getYear = date.getYear();
        for (int i = 0; i < yearBox1.getItemCount(); i++) {
            int setYear = Integer.parseInt(yearBox1.getItemAt(i));
            if (setYear == getYear) {
                yearBox1.setSelectedIndex(i);
                yearBox3.setSelectedIndex(i);
                break;
            }
        }

        branchBox.addItemListener((ItemEvent e) -> {
            cinemaSelected();
        });

        new Thread(() -> {
            // Simulate a background task
            try {
                Thread.sleep(1500); // Simulate a delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            branchBox.removeAllItems();
            try {
                for (Cinema cinema : new Service().getCinemaStub().getAllCinema()) {
                    branchBox.addItem(cinema.getBranch());
                }
            } catch (RemoteException e) {
            }
            try {
                cinemaSelected();
                profitBarChart(monthList);
                incomeBarChart(monthList);
                outcomeBarChart(monthList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void incomeBarChart(String[] monthList) {
        try {
            final DefaultCategoryDataset dataset = new Service().getAdminAnalysisStub().getOnlyCinemaAnalysis(cinema_id, genreList, Integer.parseInt(yearBox3.getSelectedItem().toString()), monthList);
            JFreeChart chart = ChartFactory.createBarChart(
                    "Income Graph",
                    "Genre",
                    "Score",
                    dataset,
                    PlotOrientation.HORIZONTAL, true, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setItemMargin(0.0);
            renderer.setMaximumBarWidth(0.1);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setDomainZoomable(false);
            chartPanel.setRangeZoomable(false);
            chartPanel.setPreferredSize(new Dimension(120, 420));
            // Update panel layout
            incomePanel.setLayout(new BorderLayout());
            incomePanel.removeAll();
            incomePanel.add(chartPanel, BorderLayout.CENTER);
            incomePanel.revalidate();
            incomePanel.repaint();
            chartPanel.addChartMouseListener(new ChartMouseListener() {

                public void chartMouseClicked(ChartMouseEvent e) {
                    ChartEntity entity = e.getEntity();
                    if (entity instanceof CategoryItemEntity) {
                        CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                        String seriesKey = itemEntity.getDataset().getRowKey(itemEntity.getSeries()).toString();
                        String categoryKey = itemEntity.getDataset().getColumnKey(itemEntity.getCategoryIndex()).toString();
                        Number value = itemEntity.getDataset().getValue(itemEntity.getSeries(), itemEntity.getCategoryIndex());

                        String message = String.format("Genre: %s, Value: %s", seriesKey, value);
                        JOptionPane.showMessageDialog(null, message);
                    } else if (entity instanceof XYItemEntity) {
                        XYItemEntity itemEntity = (XYItemEntity) entity;
                        int seriesIndex = itemEntity.getSeriesIndex();
                        int itemIndex = itemEntity.getItem();
                        double x = itemEntity.getDataset().getX(seriesIndex, itemIndex).doubleValue();
                        double y = itemEntity.getDataset().getY(seriesIndex, itemIndex).doubleValue();

                        String message = String.format("%d, Item: %d, X: %.2f, Y: %.2f", seriesIndex, itemIndex, x, y);
                        JOptionPane.showMessageDialog(null, message);
                    }
                }

                public void chartMouseMoved(ChartMouseEvent e) {
                }

            });
        } catch (RemoteException e) {
        }
    }

    public void outcomeBarChart(String[] monthList) {
        try {
            final DefaultCategoryDataset dataset = new Service().getAdminAnalysisStub().getOnlyExpenseAnalysis(cinema_id, Integer.parseInt(yearBox3.getSelectedItem().toString()), monthList);
            JFreeChart chart = ChartFactory.createBarChart(
                    "Outcome Graph",
                    "Category",
                    "Score",
                    dataset,
                    PlotOrientation.VERTICAL, true, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setItemMargin(0.0);
            renderer.setMaximumBarWidth(0.2);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setDomainZoomable(false);
            chartPanel.setRangeZoomable(false);
            chartPanel.setPreferredSize(new Dimension(120, 420));
            // Update panel layout
            outcomePanel.setLayout(new BorderLayout());
            outcomePanel.removeAll();
            outcomePanel.add(chartPanel, BorderLayout.CENTER);
            outcomePanel.revalidate();
            outcomePanel.repaint();
            chartPanel.addChartMouseListener(new ChartMouseListener() {

                public void chartMouseClicked(ChartMouseEvent e) {
                    ChartEntity entity = e.getEntity();
                    if (entity instanceof CategoryItemEntity) {
                        CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                        String seriesKey = itemEntity.getDataset().getRowKey(itemEntity.getSeries()).toString();
                        String categoryKey = itemEntity.getDataset().getColumnKey(itemEntity.getCategoryIndex()).toString();
                        Number value = itemEntity.getDataset().getValue(itemEntity.getSeries(), itemEntity.getCategoryIndex());

                        String message = String.format("%s, Value: %s", seriesKey, value);
                        JOptionPane.showMessageDialog(null, message);
                    } else if (entity instanceof XYItemEntity) {
                        XYItemEntity itemEntity = (XYItemEntity) entity;
                        int seriesIndex = itemEntity.getSeriesIndex();
                        int itemIndex = itemEntity.getItem();
                        double x = itemEntity.getDataset().getX(seriesIndex, itemIndex).doubleValue();
                        double y = itemEntity.getDataset().getY(seriesIndex, itemIndex).doubleValue();

                        String message = String.format("%d, Item: %d, X: %.2f, Y: %.2f", seriesIndex, itemIndex, x, y);
                        JOptionPane.showMessageDialog(null, message);
                    }
                }

                public void chartMouseMoved(ChartMouseEvent e) {
                }

            });
        } catch (RemoteException e) {
        }
    }

    public void profitBarChart(String[] monthList) {
        try {
            final DefaultCategoryDataset dataset = new Service().getAdminAnalysisStub().getCinemaProfitAnalysis(Integer.parseInt(yearBox1.getSelectedItem().toString()), monthList);
            JFreeChart chart = ChartFactory.createBarChart(
                    "Profit Graph",
                    "Branch",
                    "Score",
                    dataset,
                    PlotOrientation.VERTICAL, true, true, false);
            chart.setBackgroundPaint(Color.WHITE);
            CategoryPlot plot = chart.getCategoryPlot();
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setItemMargin(0.0);
            renderer.setMaximumBarWidth(0.05);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setDomainZoomable(true);
            chartPanel.setRangeZoomable(true);
            chartPanel.setPreferredSize(new Dimension(120, 420));

            // Update panel layout
            profitPanel1.setLayout(new BorderLayout());
            profitPanel1.removeAll();
            profitPanel1.add(chartPanel, BorderLayout.CENTER);
            profitPanel1.revalidate();
            profitPanel1.repaint();

            chartPanel.addChartMouseListener(new ChartMouseListener() {

                public void chartMouseClicked(ChartMouseEvent e) {
                    ChartEntity entity = e.getEntity();
                    if (entity instanceof CategoryItemEntity) {
                        CategoryItemEntity itemEntity = (CategoryItemEntity) entity;
                        String seriesKey = itemEntity.getDataset().getRowKey(itemEntity.getSeries()).toString();
                        String categoryKey = itemEntity.getDataset().getColumnKey(itemEntity.getCategoryIndex()).toString();
                        Number value = itemEntity.getDataset().getValue(itemEntity.getSeries(), itemEntity.getCategoryIndex());

                        String message = String.format("%s, Branch: %s, Value: %s", seriesKey, categoryKey, value);
                        JOptionPane.showMessageDialog(null, message);
                    } else if (entity instanceof XYItemEntity) {
                        XYItemEntity itemEntity = (XYItemEntity) entity;
                        int seriesIndex = itemEntity.getSeriesIndex();
                        int itemIndex = itemEntity.getItem();
                        double x = itemEntity.getDataset().getX(seriesIndex, itemIndex).doubleValue();
                        double y = itemEntity.getDataset().getY(seriesIndex, itemIndex).doubleValue();

                        String message = String.format("%d, Item: %d, X: %.2f, Y: %.2f", seriesIndex, itemIndex, x, y);
                        JOptionPane.showMessageDialog(null, message);
                    }
                }

                public void chartMouseMoved(ChartMouseEvent e) {
                }

            });
        } catch (RemoteException e) {
        }
    }

    public void cinemaSelected() {
        if (branchBox.getItemCount() > 0) {
            try {
                List<Cinema> list = new Service().getCinemaStub().getAllCinema();
                for (Cinema cinema : list) {
                    if (cinema.getBranch().equals(branchBox.getSelectedItem())) {
                        cinema_id = cinema.getId();
                        break;
                    }

                }
            } catch (RemoteException e) {
            }
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

        scrollPane = new javax.swing.JScrollPane();
        scrollPanel = new javax.swing.JPanel();
        profitPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        yearBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        monthBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        yearBox3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        monthBox3 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        branchBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        incomePanel = new javax.swing.JPanel();
        outcomePanel = new javax.swing.JPanel();

        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new java.awt.Dimension(1132, 1502));

        scrollPanel.setPreferredSize(new java.awt.Dimension(1130, 1492));

        profitPanel1.setBackground(new java.awt.Color(255, 255, 255));
        profitPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Analysis");

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(80, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        yearBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        yearBox1.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel3.setText("Year :");

        monthBox1.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel2.setText("Month :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(monthBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jButton3.setText("OK");
        jButton3.setPreferredSize(new java.awt.Dimension(80, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        yearBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        yearBox3.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel8.setText("Year :");

        monthBox3.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel9.setText("Month :");

        branchBox.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel7.setText("Branch :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(branchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(monthBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(branchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        incomePanel.setBackground(new java.awt.Color(255, 255, 255));
        incomePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        outcomePanel.setBackground(new java.awt.Color(255, 255, 255));
        outcomePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout scrollPanelLayout = new javax.swing.GroupLayout(scrollPanel);
        scrollPanel.setLayout(scrollPanelLayout);
        scrollPanelLayout.setHorizontalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scrollPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(incomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(outcomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scrollPanelLayout.createSequentialGroup()
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(profitPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );
        scrollPanelLayout.setVerticalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scrollPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profitPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(incomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outcomePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1332, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(scrollPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Thread(() -> {
            // Simulate a background task
            try {
                Thread.sleep(100); // Simulate a delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (monthBox1.getSelectedItem().equals("All")) {
                profitBarChart(monthList);
            } else {
                String[] month = {monthBox1.getSelectedItem().toString()};
                profitBarChart(month);
            }
        }).start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Thread(() -> {
            // Simulate a background task
            try {
                Thread.sleep(100); // Simulate a delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (monthBox3.getSelectedItem().equals("All")) {
                incomeBarChart(monthList);
                outcomeBarChart(monthList);
            } else {
                String[] month = {monthBox3.getSelectedItem().toString()};
                incomeBarChart(month);
                outcomeBarChart(month);
            }
        }).start();

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> branchBox;
    private javax.swing.JPanel incomePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JComboBox<String> monthBox1;
    private javax.swing.JComboBox<String> monthBox3;
    private javax.swing.JPanel outcomePanel;
    private javax.swing.JPanel profitPanel1;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel scrollPanel;
    private javax.swing.JComboBox<String> yearBox1;
    private javax.swing.JComboBox<String> yearBox3;
    // End of variables declaration//GEN-END:variables

}
