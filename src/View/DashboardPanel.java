/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import Controller.Service;
import Controller.SharedData;
import Interface.LoginInterface;
import Model.Cinema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author DELL
 */
public class DashboardPanel extends javax.swing.JPanel {

    /**
     * Creates new form DashboardPanel
     */
    private String[] genreList;
    private String[] monthList;
    private int cinema_id;

    public DashboardPanel() {
        initComponents();

        SharedData sharedData = SharedData.getInstance();
        genreList = sharedData.getGenreList();
        monthList = sharedData.getMonthList();

        for (String monthList1 : monthList) {
            monthBox2.addItem(monthList1);
        }
        LocalDate date = LocalDate.parse(LocalDate.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String getMonthName = date.getMonth().name().toLowerCase();
        int getYear = date.getYear();
        for (int i = 0; i < yearBox1.getItemCount(); i++) {
            int setYear = Integer.parseInt(yearBox1.getItemAt(i));
            if (setYear == getYear) {
                yearBox1.setSelectedIndex(i);
                yearBox2.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < monthBox2.getItemCount(); i++) {
            String setMonth = monthBox2.getItemAt(i).toLowerCase();
            if (setMonth.equals(getMonthName)) {
                monthBox2.setSelectedIndex(i);
                break;
            }
        }

        branchBox.addItemListener((var e) -> {
            cinemaSelected();
        });

        imageSetup(branchImage, "C://Users/DELL/Downloads/branch.png");
        imageSetup(employeeImage, "C://Users/DELL/Downloads/employee.png");
        imageSetup(roomImage, "C://Users/DELL/Downloads/room.png");

        setup();

    }

    private void setup() {
        setCount();
        branchBox.removeAllItems();
        try {
            for (Cinema cinema : new Service().getCinemaStub().getAllCinema()) {
                branchBox.addItem(cinema.getBranch());
            }
        } catch (RemoteException e) {
        }

        try {
            cinemaSelected();
            cinemaLineChart();
            monthlyCinemaBarChart();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void refresh() {
        int port = SharedData.getInstance().getPort();
        if (isRegistryAvailable("localhost", port)) {
            new Thread(() -> {
                // Simulate a background task
                try {
                    Thread.sleep(100); // Simulate a delay
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                setup();

            }).start();

        } else {
            branchCount.setText("0");
            employeeCount.setText("0");
            roomCount.setText("0");
            branchBox.removeAllItems();
            cinemaLineChartIsEmpty();
            cinemaPiechartIsEmpty();
            monthlyCinemaBarChartIsEmpty();

        }
    }

    public boolean isRegistryAvailable(String host, int port) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", port);
            LoginInterface loginStub = (LoginInterface) registry.lookup("login");
            if (loginStub == null) {
                throw new RuntimeException("Login interface not found");
            }
            return true;
        } catch (RemoteException | NotBoundException e) {
            return false;
        }

    }

    public void setCount() {
        try {
            List<Cinema> list = new Service().getCinemaStub().getAllCinema();
            branchCount.setText(String.valueOf(list.size()));
            int emp_count = 0;
            int room_count = 0;
            for (Cinema cinema : list) {
                emp_count += new Service().getEmployeeStub().getAllEmployee(cinema.getId()).size();
                room_count += new Service().getRoomStub().getAllRoom(cinema.getId()).size();
            }
            employeeCount.setText(String.valueOf(emp_count));
            roomCount.setText(String.valueOf(room_count));
        } catch (RemoteException e) {
        }
    }

    private void imageSetup(JLabel imageLabel, String file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Scale the image
        Image scaledImage = bufferedImage.getScaledInstance(94, 94, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);

    }

    public void cinemaLineChartIsEmpty() {
        JFreeChart chart = ChartFactory.createLineChart(
                "Branch Graph",
                "Month",
                "Score",
                null,
                PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        chartPanel.setPreferredSize(new Dimension(120, 420));

        // Update panel layout
        cinemaPanel1.setLayout(new BorderLayout());
        cinemaPanel1.removeAll();
        cinemaPanel1.add(chartPanel, BorderLayout.CENTER);
        cinemaPanel1.revalidate();
        cinemaPanel1.repaint();

    }

    public void cinemaPiechartIsEmpty() {
        JFreeChart chart = ChartFactory.createPieChart(
                "Branch Income", // chart title
                null, // data
                true, // include legend
                true,
                false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanel.setPreferredSize(new Dimension(120, 420));
        cinemaPiechartPanel1.setLayout(new BorderLayout());
        cinemaPiechartPanel1.removeAll();
        cinemaPiechartPanel1.add(chartPanel, BorderLayout.NORTH);
        cinemaPiechartPanel1.revalidate();
        cinemaPiechartPanel1.repaint();
    }

    public void monthlyCinemaBarChartIsEmpty() {

        JFreeChart chart = ChartFactory.createBarChart(
                "Movie Graph",
                "Genre",
                "Score",
                null,
                PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setPreferredSize(new Dimension(120, 420));

        // Update panel layout
        cinemaPanel2.setLayout(new BorderLayout());
        cinemaPanel2.removeAll();
        cinemaPanel2.add(chartPanel, BorderLayout.CENTER);
        cinemaPanel2.revalidate();
        cinemaPanel2.repaint();

    }

    public void cinemaLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            dataset = new Service().getAdminAnalysisStub().getCinemaAnalysis(Integer.parseInt(yearBox1.getSelectedItem().toString()), monthList);
        } catch (RemoteException e) {
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Branch Graph",
                "Month",
                "Score",
                dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        chartPanel.setPreferredSize(new Dimension(120, 420));

        // Update panel layout
        cinemaPanel1.setLayout(new BorderLayout());
        cinemaPanel1.removeAll();
        cinemaPanel1.add(chartPanel, BorderLayout.CENTER);
        cinemaPanel1.revalidate();
        cinemaPanel1.repaint();

        cinemaPiechart();

    }

    public void monthlyCinemaBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            String[] month = {monthBox2.getSelectedItem().toString()};
            dataset = new Service().getAdminAnalysisStub().getOnlyCinemaAnalysis(cinema_id, genreList, Integer.parseInt(yearBox2.getSelectedItem().toString()), month);
        } catch (RemoteException e) {
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Movie Graph",
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
        cinemaPanel2.setLayout(new BorderLayout());
        cinemaPanel2.removeAll();
        cinemaPanel2.add(chartPanel, BorderLayout.CENTER);
        cinemaPanel2.revalidate();
        cinemaPanel2.repaint();
        chartPanel.addChartMouseListener(new ChartMouseListener() {

            public void chartMouseClicked(ChartMouseEvent e) {
                ChartEntity entity = e.getEntity();
                if (entity instanceof CategoryItemEntity itemEntity) {
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

            @Override
            public void chartMouseMoved(ChartMouseEvent e) {
            }

        });

    }

    public void cinemaPiechart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            dataset = new Service().getAdminAnalysisStub().getCinemaAnalysisPieChart(Integer.parseInt(yearBox1.getSelectedItem().toString()), monthList);
        } catch (RemoteException e) {
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Branch Income", // chart title
                dataset, // data
                true, // include legend
                true,
                false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setDomainZoomable(true);
        chartPanel.setPreferredSize(new Dimension(120, 420));
        cinemaPiechartPanel1.setLayout(new BorderLayout());
        cinemaPiechartPanel1.removeAll();
        cinemaPiechartPanel1.add(chartPanel, BorderLayout.NORTH);
        cinemaPiechartPanel1.revalidate();
        cinemaPiechartPanel1.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        scrollPanel = new javax.swing.JPanel();
        cinemaPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        yearBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        yearBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monthBox2 = new javax.swing.JComboBox<>();
        branchBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        branchImage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        branchCount = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        employeeImage = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        employeeCount = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        roomImage = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        roomCount = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cinemaPanel2 = new javax.swing.JPanel();
        cinemaPiechartPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(1100, 623));

        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new java.awt.Dimension(1132, 1502));

        scrollPanel.setPreferredSize(new java.awt.Dimension(1130, 1492));

        cinemaPanel1.setBackground(new java.awt.Color(255, 255, 255));
        cinemaPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        yearBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        yearBox1.setMaximumSize(new java.awt.Dimension(100, 23));
        yearBox1.setMinimumSize(new java.awt.Dimension(100, 23));
        yearBox1.setPreferredSize(new java.awt.Dimension(100, 23));

        jButton1.setText("OK");
        jButton1.setPreferredSize(new java.awt.Dimension(80, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Year :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(749, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(yearBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(yearBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        yearBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));
        yearBox2.setMaximumSize(new java.awt.Dimension(100, 23));
        yearBox2.setMinimumSize(new java.awt.Dimension(100, 23));
        yearBox2.setPreferredSize(new java.awt.Dimension(100, 23));

        jButton2.setText("OK");
        jButton2.setPreferredSize(new java.awt.Dimension(80, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Year :");

        jLabel2.setText("Month :");

        monthBox2.setMaximumSize(new java.awt.Dimension(100, 23));
        monthBox2.setMinimumSize(new java.awt.Dimension(100, 23));
        monthBox2.setPreferredSize(new java.awt.Dimension(100, 23));

        branchBox.setMaximumSize(new java.awt.Dimension(100, 23));
        branchBox.setMinimumSize(new java.awt.Dimension(100, 23));
        branchBox.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel7.setText("Branch :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(branchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(0, 0, 0)
                .addComponent(yearBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(monthBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(yearBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(branchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        branchImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        branchImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        branchImage.setMaximumSize(new java.awt.Dimension(80, 80));
        branchImage.setMinimumSize(new java.awt.Dimension(80, 80));
        branchImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Branch");

        branchCount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        branchCount.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(branchImage, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(branchCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(branchCount)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(branchImage, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        employeeImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        employeeImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        employeeImage.setMaximumSize(new java.awt.Dimension(80, 80));
        employeeImage.setMinimumSize(new java.awt.Dimension(80, 80));
        employeeImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Employee");

        employeeCount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        employeeCount.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employeeImage, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(employeeCount, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(employeeCount)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(employeeImage, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        roomImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roomImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        roomImage.setMaximumSize(new java.awt.Dimension(80, 80));
        roomImage.setMinimumSize(new java.awt.Dimension(80, 80));
        roomImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Room");

        roomCount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        roomCount.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomImage, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomCount, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(roomCount)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(roomImage, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Dashboard");

        cinemaPanel2.setBackground(new java.awt.Color(255, 255, 255));
        cinemaPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        cinemaPiechartPanel1.setBackground(new java.awt.Color(255, 255, 255));
        cinemaPiechartPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout scrollPanelLayout = new javax.swing.GroupLayout(scrollPanel);
        scrollPanel.setLayout(scrollPanelLayout);
        scrollPanelLayout.setHorizontalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scrollPanelLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addComponent(cinemaPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addComponent(cinemaPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cinemaPiechartPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        scrollPanelLayout.setVerticalGroup(
            scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scrollPanelLayout.createSequentialGroup()
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scrollPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)))
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(scrollPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scrollPanelLayout.createSequentialGroup()
                        .addComponent(cinemaPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cinemaPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cinemaPiechartPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1197, Short.MAX_VALUE))
        );

        scrollPane.setViewportView(scrollPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Thread(() -> {
            // Simulate a background task
            try {
                Thread.sleep(100); // Simulate a delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            cinemaLineChart();
        }).start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Thread(() -> {
            // Simulate a background task
            try {
                Thread.sleep(100); // Simulate a delay
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            monthlyCinemaBarChart();
        }).start();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        refresh();

    }//GEN-LAST:event_jButton3ActionPerformed
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> branchBox;
    private javax.swing.JLabel branchCount;
    private javax.swing.JLabel branchImage;
    private javax.swing.JPanel cinemaPanel1;
    private javax.swing.JPanel cinemaPanel2;
    private javax.swing.JPanel cinemaPiechartPanel1;
    private javax.swing.JLabel employeeCount;
    private javax.swing.JLabel employeeImage;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JComboBox<String> monthBox2;
    private javax.swing.JLabel roomCount;
    private javax.swing.JLabel roomImage;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel scrollPanel;
    private javax.swing.JComboBox<String> yearBox1;
    private javax.swing.JComboBox<String> yearBox2;
    // End of variables declaration//GEN-END:variables

}
