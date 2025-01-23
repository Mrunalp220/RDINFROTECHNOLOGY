import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StylishBankingApp {
    private double balance = 0.0;
    private JLabel balanceLabel;
    private JPanel mainPanel;
    private boolean isDarkMode = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StylishBankingApp::new);
    }

    public StylishBankingApp() {
        JFrame frame = new JFrame("Stylish Banking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Sidebar with icons
        JPanel sidebar = createSidebar();
        frame.add(sidebar, BorderLayout.WEST);

        // Main content panel with card layout
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create Panels
        JPanel homePanel = createDashboardPanel();
        JPanel depositPanel = createTransactionPanel("Deposit", this::handleDeposit);
        JPanel withdrawPanel = createTransactionPanel("Withdraw", this::handleWithdraw);

        // Add panels to main panel
        mainPanel.add(homePanel, "Dashboard");
        mainPanel.add(depositPanel, "Deposit");
        mainPanel.add(withdrawPanel, "Withdraw");

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 600));
        sidebar.setBackground(new Color(45, 52, 54));

        JLabel logo = new JLabel(new ImageIcon("bank_logo.png")); // Placeholder for logo
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton dashboardButton = createSidebarButton("Dashboard", "icons/dashboard.png", e -> switchToPanel("Dashboard"));
        JButton depositButton = createSidebarButton("Deposit", "icons/deposit.png", e -> switchToPanel("Deposit"));
        JButton withdrawButton = createSidebarButton("Withdraw", "icons/withdraw.png", e -> switchToPanel("Withdraw"));
        JButton toggleTheme = createSidebarButton("Dark Mode", "icons/theme.png", e -> toggleDarkMode());

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(logo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(dashboardButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(depositButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(withdrawButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        sidebar.add(toggleTheme);

        return sidebar;
    }

    private JButton createSidebarButton(String text, String iconPath, ActionListener listener) {
        JButton button = new JButton(text, new ImageIcon(iconPath));
        button.setMaximumSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(99, 110, 114));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(5, 15, 5, 5));
        button.addActionListener(listener);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(178, 190, 195));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(99, 110, 114));
            }
        });

        return button;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBackground(new Color(240, 242, 245));

        // Balance Panel
        JPanel balancePanel = new JPanel(new GridBagLayout());
        balancePanel.setBackground(new Color(52, 152, 219));
        balancePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        balanceLabel = new JLabel("Current Balance: $0.0");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        balanceLabel.setForeground(Color.WHITE);
        balancePanel.add(balanceLabel);

        // Transaction History Placeholder
        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setBorder(new LineBorder(new Color(220, 221, 225), 1, true));

        JLabel historyLabel = new JLabel("Recent Transactions");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        historyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        historyPanel.add(historyLabel);
        historyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        historyPanel.add(new JLabel("• Deposit: $500"));
        historyPanel.add(new JLabel("• Withdraw: $200"));

        dashboardPanel.add(balancePanel, BorderLayout.NORTH);
        dashboardPanel.add(historyPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private JPanel createTransactionPanel(String action, ActionListener actionListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 242, 245));

        JLabel titleLabel = new JLabel(action + " Amount");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField amountField = new JTextField(10);
        amountField.setMaximumSize(new Dimension(200, 30));
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton actionButton = new JButton(action);
        actionButton.setBackground(new Color(46, 204, 113));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.BOLD, 16));
        actionButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                actionListener.actionPerformed(new ActionEvent(amount, ActionEvent.ACTION_PERFORMED, null));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(amountField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(actionButton);

        return panel;
    }

    private void switchToPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }

    private void handleDeposit(ActionEvent e) {
        double amount = (double) e.getSource();
        if (amount > 0) {
            balance += amount;
            updateBalanceLabel();
            JOptionPane.showMessageDialog(mainPanel, "Amount of $" + amount + " deposited successfully!", 
                                          "Deposit Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Invalid deposit amount. Please try again.", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleWithdraw(ActionEvent e) {
        double amount = (double) e.getSource();
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalanceLabel();
            JOptionPane.showMessageDialog(mainPanel, "Amount of $" + amount + " withdrawn successfully!", 
                                          "Withdrawal Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(mainPanel, "Insufficient balance. Unable to withdraw $" + amount, 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Invalid withdrawal amount. Please try again.", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: $" + balance);
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        mainPanel.setBackground(isDarkMode ? Color.DARK_GRAY : Color.LIGHT_GRAY);
    }
}
