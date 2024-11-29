/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.SharedData;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author DELL
 */
public class Home extends javax.swing.JFrame {

    CardLayout cardLayout;
    private static final int FADE_DURATION = 100; // Duration of the fade effect in milliseconds
    private static final int FADE_STEPS = 20; // Number of steps in the fade effect

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        int port=SharedData.getInstance().getPort();
        portLabel.setText("Port : "+port);
        
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("C://Users//DELL//Desktop//logo.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Scale the image
        Image scaledImage = bufferedImage.getScaledInstance(190, 190, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
        this.setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        panelChange.setLayout(cardLayout);

        // Create Dashboard Panel
        panelChange.add(new DashboardPanel(), "DashboardPanel");
        // Create Cinema Panel
        panelChange.add(new CinemaPanel(), "CinemaPanel");
        // Create Employee Panel
        panelChange.add(new EmployeePanel(), "EmployeePanel");
        // Create Salary Panel
        panelChange.add(new SalaryPanel(), "SalaryPanel");
        // Create Role Type Panel
        panelChange.add(new RoleTypePanel(), "RoleTypePanel");
        // Create Expense Panel
        panelChange.add(new ExpensePanel(), "ExpensePanel");
        // Create Analysis Panel
        panelChange.add(new AnalysisPanel(), "AnalysisPanel");

        startFadeEffect(dashboardBtn);
        dashboardBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(dashboardBtn);
                cardLayout.show(panelChange, "DashboardPanel");
                panelBackground(branchBtn);
                panelBackground(employeeBtn);
                panelBackground(salaryBtn);
                panelBackground(roleBtn);
                panelBackground(expenseBtn);
                panelBackground(analysisBtn);
            }
        });
        branchBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(branchBtn);
                cardLayout.show(panelChange, "CinemaPanel");
                panelBackground(dashboardBtn);
                panelBackground(employeeBtn);
                panelBackground(salaryBtn);
                panelBackground(roleBtn);
                panelBackground(expenseBtn);
                panelBackground(analysisBtn);
            }
        });

        employeeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(employeeBtn);
                cardLayout.show(panelChange, "EmployeePanel");
                panelBackground(dashboardBtn);
                panelBackground(branchBtn);
                panelBackground(salaryBtn);
                panelBackground(roleBtn);
                panelBackground(expenseBtn);
                panelBackground(analysisBtn);
            }
        });

        salaryBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(salaryBtn);
                cardLayout.show(panelChange, "SalaryPanel");
                panelBackground(dashboardBtn);
                panelBackground(branchBtn);
                panelBackground(employeeBtn);
                panelBackground(roleBtn);
                panelBackground(expenseBtn);
                panelBackground(analysisBtn);
            }
        });
        roleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(roleBtn);
                cardLayout.show(panelChange, "RoleTypePanel");
                panelBackground(dashboardBtn);
                panelBackground(branchBtn);
                panelBackground(employeeBtn);
                panelBackground(salaryBtn);
                panelBackground(expenseBtn);
                panelBackground(analysisBtn);
            }
        });
        expenseBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(expenseBtn);
                cardLayout.show(panelChange, "ExpensePanel");
                panelBackground(dashboardBtn);
                panelBackground(branchBtn);
                panelBackground(employeeBtn);
                panelBackground(salaryBtn);
                panelBackground(roleBtn);
                panelBackground(analysisBtn);
            }
        });
        analysisBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startFadeEffect(analysisBtn);
                cardLayout.show(panelChange, "AnalysisPanel");
                panelBackground(dashboardBtn);
                panelBackground(branchBtn);
                panelBackground(employeeBtn);
                panelBackground(salaryBtn);
                panelBackground(roleBtn);
                panelBackground(expenseBtn);
            }
        });

        imageSetup(dashboardImage, "C://Users/DELL/Downloads/dashboard.png");
        imageSetup(branchImage, "C://Users/DELL/Downloads/cinema.png");
        imageSetup(employeeImage, "C://Users/DELL/Downloads/employee.png");
        imageSetup(salaryImage, "C://Users/DELL/Downloads/salary.png");
        imageSetup(roleImage, "C://Users/DELL/Downloads/role.png");
        imageSetup(expenseImage, "C://Users/DELL/Downloads/expense.png");
        imageSetup(analysisImage, "C://Users/DELL/Downloads/analysis.png");
    }

    public void panelBackground(JPanel jpanel) {
        jpanel.setBackground(new Color(204, 204, 204));
    }
    
    public void imageSetup(JLabel imageLabel,String file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Scale the image
        Image scaledImage = bufferedImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(imageIcon);
    }

    private void startFadeEffect(JPanel panel) {
        final Color originalColor = panel.getBackground();
        final Color targetColor = Color.LIGHT_GRAY; // The color to fade to

        Timer timer = new Timer(FADE_DURATION / FADE_STEPS, new ActionListener() {
            private int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the new color
                float progress = (float) step / FADE_STEPS;
                int red = (int) (originalColor.getRed() + progress * (targetColor.getRed() - originalColor.getRed()));
                int green = (int) (originalColor.getGreen() + progress * (targetColor.getGreen() - originalColor.getGreen()));
                int blue = (int) (originalColor.getBlue() + progress * (targetColor.getBlue() - originalColor.getBlue()));
                panel.setBackground(new Color(red, green, blue));

                // Increment step and check if fade is complete
                step++;
                if (step > FADE_STEPS) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
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
        jPanel2 = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        employeeBtn = new javax.swing.JPanel();
        employeeImage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dashboardBtn = new javax.swing.JPanel();
        dashboardImage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        salaryBtn = new javax.swing.JPanel();
        salaryImage = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        branchBtn = new javax.swing.JPanel();
        branchImage = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        roleBtn = new javax.swing.JPanel();
        roleImage = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        expenseBtn = new javax.swing.JPanel();
        expenseImage = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        analysisBtn = new javax.swing.JPanel();
        analysisImage = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        panelChange = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1314, 946));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(208, 940));

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        imageLabel.setMaximumSize(new java.awt.Dimension(80, 80));
        imageLabel.setMinimumSize(new java.awt.Dimension(80, 80));
        imageLabel.setPreferredSize(new java.awt.Dimension(80, 80));

        employeeBtn.setBackground(new java.awt.Color(204, 204, 204));

        employeeImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        employeeImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        employeeImage.setMaximumSize(new java.awt.Dimension(80, 80));
        employeeImage.setMinimumSize(new java.awt.Dimension(80, 80));
        employeeImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Employee");

        javax.swing.GroupLayout employeeBtnLayout = new javax.swing.GroupLayout(employeeBtn);
        employeeBtn.setLayout(employeeBtnLayout);
        employeeBtnLayout.setHorizontalGroup(
            employeeBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(employeeImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        employeeBtnLayout.setVerticalGroup(
            employeeBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeBtnLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(employeeImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(employeeBtnLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dashboardBtn.setBackground(new java.awt.Color(204, 204, 204));

        dashboardImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboardImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        dashboardImage.setMaximumSize(new java.awt.Dimension(80, 80));
        dashboardImage.setMinimumSize(new java.awt.Dimension(80, 80));
        dashboardImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Dashboard");

        javax.swing.GroupLayout dashboardBtnLayout = new javax.swing.GroupLayout(dashboardBtn);
        dashboardBtn.setLayout(dashboardBtnLayout);
        dashboardBtnLayout.setHorizontalGroup(
            dashboardBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(dashboardImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        dashboardBtnLayout.setVerticalGroup(
            dashboardBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardBtnLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(dashboardImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(dashboardBtnLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        salaryBtn.setBackground(new java.awt.Color(204, 204, 204));

        salaryImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salaryImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        salaryImage.setMaximumSize(new java.awt.Dimension(80, 80));
        salaryImage.setMinimumSize(new java.awt.Dimension(80, 80));
        salaryImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Salary");

        javax.swing.GroupLayout salaryBtnLayout = new javax.swing.GroupLayout(salaryBtn);
        salaryBtn.setLayout(salaryBtnLayout);
        salaryBtnLayout.setHorizontalGroup(
            salaryBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaryBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(salaryImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        salaryBtnLayout.setVerticalGroup(
            salaryBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaryBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(salaryImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(salaryBtnLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        branchBtn.setBackground(new java.awt.Color(204, 204, 204));

        branchImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        branchImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        branchImage.setMaximumSize(new java.awt.Dimension(80, 80));
        branchImage.setMinimumSize(new java.awt.Dimension(80, 80));
        branchImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Branch");

        javax.swing.GroupLayout branchBtnLayout = new javax.swing.GroupLayout(branchBtn);
        branchBtn.setLayout(branchBtnLayout);
        branchBtnLayout.setHorizontalGroup(
            branchBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(branchImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        branchBtnLayout.setVerticalGroup(
            branchBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchBtnLayout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(branchImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(branchBtnLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roleBtn.setBackground(new java.awt.Color(204, 204, 204));

        roleImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roleImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        roleImage.setMaximumSize(new java.awt.Dimension(80, 80));
        roleImage.setMinimumSize(new java.awt.Dimension(80, 80));
        roleImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Role");

        javax.swing.GroupLayout roleBtnLayout = new javax.swing.GroupLayout(roleBtn);
        roleBtn.setLayout(roleBtnLayout);
        roleBtnLayout.setHorizontalGroup(
            roleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roleBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(roleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roleBtnLayout.setVerticalGroup(
            roleBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roleBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(roleImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(roleBtnLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        expenseBtn.setBackground(new java.awt.Color(204, 204, 204));

        expenseImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        expenseImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        expenseImage.setMaximumSize(new java.awt.Dimension(80, 80));
        expenseImage.setMinimumSize(new java.awt.Dimension(80, 80));
        expenseImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Expense");

        javax.swing.GroupLayout expenseBtnLayout = new javax.swing.GroupLayout(expenseBtn);
        expenseBtn.setLayout(expenseBtnLayout);
        expenseBtnLayout.setHorizontalGroup(
            expenseBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(expenseImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        expenseBtnLayout.setVerticalGroup(
            expenseBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenseBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(expenseImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(expenseBtnLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        analysisBtn.setBackground(new java.awt.Color(204, 204, 204));

        analysisImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        analysisImage.setIcon(new javax.swing.ImageIcon("C:\\Users\\DELL\\Desktop\\logo.png")); // NOI18N
        analysisImage.setMaximumSize(new java.awt.Dimension(80, 80));
        analysisImage.setMinimumSize(new java.awt.Dimension(80, 80));
        analysisImage.setPreferredSize(new java.awt.Dimension(80, 80));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Analysis");

        javax.swing.GroupLayout analysisBtnLayout = new javax.swing.GroupLayout(analysisBtn);
        analysisBtn.setLayout(analysisBtnLayout);
        analysisBtnLayout.setHorizontalGroup(
            analysisBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analysisBtnLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(analysisImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        analysisBtnLayout.setVerticalGroup(
            analysisBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analysisBtnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(analysisImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(analysisBtnLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(employeeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(salaryBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(branchBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roleBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(expenseBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(analysisBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(branchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salaryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expenseBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(analysisBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(309, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(347, 82));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Cinema Management System");

        portLabel.setText("Port : 1099");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(portLabel)
                .addGap(323, 323, 323)
                .addComponent(jLabel2)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(portLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelChange.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelChange.setPreferredSize(new java.awt.Dimension(1100, 623));
        panelChange.setRequestFocusEnabled(false);

        javax.swing.GroupLayout panelChangeLayout = new javax.swing.GroupLayout(panelChange);
        panelChange.setLayout(panelChangeLayout);
        panelChangeLayout.setHorizontalGroup(
            panelChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelChangeLayout.setVerticalGroup(
            panelChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelChange, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        panelChange.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel analysisBtn;
    private javax.swing.JLabel analysisImage;
    private javax.swing.JPanel branchBtn;
    private javax.swing.JLabel branchImage;
    private javax.swing.JPanel dashboardBtn;
    private javax.swing.JLabel dashboardImage;
    private javax.swing.JPanel employeeBtn;
    private javax.swing.JLabel employeeImage;
    private javax.swing.JPanel expenseBtn;
    private javax.swing.JLabel expenseImage;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel panelChange;
    private javax.swing.JLabel portLabel;
    private javax.swing.JPanel roleBtn;
    private javax.swing.JLabel roleImage;
    private javax.swing.JPanel salaryBtn;
    private javax.swing.JLabel salaryImage;
    // End of variables declaration//GEN-END:variables
}
