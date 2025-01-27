import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdvancedCALCI extends JFrame {
    private JTextField txtNum1, txtNum2, txtResult;
    private JButton btnAdd, btnSubtract, btnMultiply, btnDivide, btnClear, btnExit;

    public AdvancedCALCI() {
        setTitle("Advanced Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header Label
        JLabel header = new JLabel("Calculator", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setForeground(Color.BLUE);
        add(header, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Number 1:"));
        txtNum1 = new JTextField();
        inputPanel.add(txtNum1);
        inputPanel.add(new JLabel("Number 2:"));
        txtNum2 = new JTextField();
        inputPanel.add(txtNum2);
        inputPanel.add(new JLabel("Result:"));
        txtResult = new JTextField();
        txtResult.setEditable(false);
        inputPanel.add(txtResult);
        add(inputPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnAdd = new JButton("+");
        btnSubtract = new JButton("-");
        btnMultiply = new JButton("*");
        btnDivide = new JButton("/");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSubtract);
        buttonPanel.add(btnMultiply);
        buttonPanel.add(btnDivide);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Handlers
        btnAdd.addActionListener(this::performOperation);
        btnSubtract.addActionListener(this::performOperation);
        btnMultiply.addActionListener(this::performOperation);
        btnDivide.addActionListener(this::performOperation);
        btnClear.addActionListener(e -> clearFields());
        btnExit.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null); // Center window
        setVisible(true);
    }

    private void performOperation(ActionEvent e) {
        try {
            double num1 = Double.parseDouble(txtNum1.getText());
            double num2 = Double.parseDouble(txtNum2.getText());
            double result = 0;

            if (e.getSource() == btnAdd) {
                result = num1 + num2;
            } else if (e.getSource() == btnSubtract) {
                result = num1 - num2;
            } else if (e.getSource() == btnMultiply) {
                result = num1 * num2;
            } else if (e.getSource() == btnDivide) {
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    txtResult.setText("Error: Division by 0");
                    return;
                }
            }
            txtResult.setText(String.format("%.2f", result));
        } catch (NumberFormatException ex) {
            txtResult.setText("Invalid input");
        }
    }

    private void clearFields() {
        txtNum1.setText("");
        txtNum2.setText("");
        txtResult.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdvancedCALCI::new);
    }
}
