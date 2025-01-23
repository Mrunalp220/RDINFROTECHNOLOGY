import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton btnAdd, btnEdit, btnDelete, btnComplete, btnToggleTheme;
    private boolean isDarkTheme = false;

    public ToDoListApp() {
        setTitle("To-Do List Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
       

        JLabel header = new JLabel("\u2605 To-Do List \u2605", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 30));
        add(header, BorderLayout.NORTH);

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 18));
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        updateTheme();

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        taskInput = new JTextField();
        taskInput.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnAdd = new JButton("+ Add Task");
        styleButton(btnAdd, new Color(0, 204, 102));
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(btnAdd, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnEdit = new JButton("✏ Edit");
        btnDelete = new JButton("🗑 Delete");
        btnComplete = new JButton("✔ Complete");
        btnToggleTheme = new JButton("☀ Toggle Theme");

        styleButton(btnEdit, new Color(255, 153, 51));
        styleButton(btnDelete, new Color(204, 0, 0));
        styleButton(btnComplete, new Color(0, 102, 204));
        styleButton(btnToggleTheme, new Color(100, 100, 100));

        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnComplete);
        buttonPanel.add(btnToggleTheme);
        add(buttonPanel, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> addTask());
        btnEdit.addActionListener(e -> editTask());
        btnDelete.addActionListener(e -> deleteTask());
        btnComplete.addActionListener(e -> markComplete());
        btnToggleTheme.addActionListener(e -> toggleTheme());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskInput.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String newTask = JOptionPane.showInputDialog(this, "Edit task:", taskListModel.getElementAt(selectedIndex));
            if (newTask != null && !newTask.trim().isEmpty()) {
                taskListModel.set(selectedIndex, newTask.trim());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void markComplete() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String completedTask = "✔ " + taskListModel.getElementAt(selectedIndex);
            taskListModel.set(selectedIndex, completedTask);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark complete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        updateTheme();
    }

    private void updateTheme() {
        Color bgColor = isDarkTheme ? new Color(50, 50, 50) : new Color(240, 240, 240);
        Color fgColor = isDarkTheme ? Color.WHITE : Color.BLACK;
        getContentPane().setBackground(bgColor);
        taskList.setBackground(bgColor);
        taskList.setForeground(fgColor);
        repaint();
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}
