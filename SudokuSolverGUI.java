import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI {
    private static final int SIZE = 9;
    private JFrame frame;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton, clearButton;

    public SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField(2);
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(cells[row][col]);
            }
        }

        solveButton = new JButton("Solve");
        clearButton = new JButton("Clear");
        solveButton.addActionListener(new SolveAction());
        clearButton.addActionListener(e -> clearBoard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void clearBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText("");
            }
        }
    }

    private boolean solveSudoku(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solveSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private class SolveAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[][] board = new int[SIZE][SIZE];
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    String text = cells[row][col].getText();
                    board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                }
            }
            if (solveSudoku(board)) {
                for (int row = 0; row < SIZE; row++) {
                    for (int col = 0; col < SIZE; col++) {
                        cells[row][col].setText(String.valueOf(board[row][col]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No solution exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new SudokuSolverGUI();
    }
}
