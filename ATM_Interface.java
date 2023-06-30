import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class ATM_Interface implements ActionListener {
    JFrame frame1;
    JLabel label_pin, label_userID;
    JTextField txt_userID;
    JPasswordField txt_pin;
    JButton login_btn;

    JButton history, withdraw, deposit, transfer, exit;

    private double balance;
    private ArrayList<String> transactionHistory;

    ATM_Interface() {

        frame1 = new JFrame("ATM INTERFACE");
        frame1.setLayout(null);

        label_userID = new JLabel("Enter the USER ID :");

        Font currentFont = label_userID.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.BOLD, 16);

        label_userID.setFont(newFont);
        label_userID.setBounds(650, 50, 150, 50);
        frame1.add(label_userID);

        txt_userID = new JTextField();
        int width = 650 + label_userID.getWidth();
        txt_userID.setBounds(width, 65, 100, 25);
        frame1.add(txt_userID);

        label_pin = new JLabel("Enter the PIN : ");
        label_pin.setFont(newFont);
        label_pin.setBounds(650, 100, 150, 50);
        frame1.add(label_pin);

        txt_pin = new JPasswordField(100);
        txt_pin.setBounds(width, 115, 100, 25);
        frame1.add(txt_pin);

        login_btn = new JButton("LOGIN");
        login_btn.setFont(newFont);
        login_btn.setBounds(730, 200, 100, 30);
        frame1.add(login_btn);
        login_btn.addActionListener(this);

        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame1.setSize(400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame1.setSize(screenSize.width, screenSize.height);

        balance = 10000; // Initial balance
        transactionHistory = new ArrayList<>();
    }

    public void showOptions() {
        JFrame frame2 = new JFrame();
        frame2.setLayout(null);

        history = new JButton("Transaction History");
        Font currentFont = history.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.BOLD, 16);
        history.setFont(newFont);
        history.setBounds(500, 100, 250, 40);
        history.addActionListener(this);
        frame2.add(history);

        deposit = new JButton("Deposit");
        deposit.setFont(newFont);
        deposit.setBounds(800, 100, 250, 40);
        deposit.addActionListener(this);
        frame2.add(deposit);

        withdraw = new JButton("Withdraw");
        withdraw.setFont(newFont);
        withdraw.setBounds(500, 200, 250, 40);
        withdraw.addActionListener(this);
        frame2.add(withdraw);

        transfer = new JButton("Transfer");
        transfer.setFont(newFont);
        transfer.setBounds(800, 200, 250, 40);
        transfer.addActionListener(this);
        frame2.add(transfer);

        exit = new JButton("Exit");
        exit.setFont(newFont);
        exit.setBounds(650, 300, 250, 40);
        exit.addActionListener(this);
        frame2.add(exit);

        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame2.setSize(400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame2.setSize(screenSize.width, screenSize.height);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == login_btn) {

            String userID = txt_userID.getText();
            String userPin = new String(txt_pin.getPassword());

            if (userID.equals("user") && userPin.equals("user@123")) {

                frame1.setVisible(false);
                showOptions();

            } else {

                JOptionPane.showMessageDialog(null, "Incorrect username or password.", "Login Error",
                        JOptionPane.ERROR_MESSAGE);

            }
        } else if (ae.getSource() == history) {
            displayTransactionHistory();
        } else if (ae.getSource() == withdraw) {
            withdrawMoney();
        } else if (ae.getSource() == deposit) {
            depositMoney();
        } else if (ae.getSource() == transfer) {
            transferMoney();
        } else if (ae.getSource() == exit) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private void displayTransactionHistory() {
        StringBuilder historyText = new StringBuilder();
        for (String transaction : transactionHistory) {
            historyText.append(transaction).append("\n");
        }
        JOptionPane.showMessageDialog(null, historyText.toString(), "Transaction History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void withdrawMoney() {
        String amountString = JOptionPane.showInputDialog(null, "Enter the amount to withdraw:", "Withdraw",
                JOptionPane.PLAIN_MESSAGE);
        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Withdraw", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance.", "Withdraw", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                balance -= amount;
                String transaction = "Withdraw: Rs. " + amount;
                transactionHistory.add(transaction);
                JOptionPane.showMessageDialog(null, transaction, "Withdraw", JOptionPane.INFORMATION_MESSAGE);

                // Showing available balance
                JOptionPane.showMessageDialog(null, "Available balance: Rs. " + balance, "Withdraw",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount.", "Withdraw", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void depositMoney() {
        String amountString = JOptionPane.showInputDialog(null, "Enter the amount to deposit:", "Deposit",
                JOptionPane.PLAIN_MESSAGE);
        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Deposit", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                balance += amount;
                String transaction = "Deposit: Rs. " + amount;
                transactionHistory.add(transaction);
                JOptionPane.showMessageDialog(null, transaction, "Deposit", JOptionPane.INFORMATION_MESSAGE);

                // Showing available balance
                JOptionPane.showMessageDialog(null, "Available balance: Rs. " + balance, "Deposit",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount.", "Deposit", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void transferMoney() {
        String amountString = JOptionPane.showInputDialog(null, "Enter the amount to transfer:", "Transfer",
                JOptionPane.PLAIN_MESSAGE);
        if (amountString != null) {
            try {
                double amount = Double.parseDouble(amountString);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid amount.", "Transfer", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance.", "Transfer", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Getting account Number
                String accountNumber = JOptionPane.showInputDialog(null, "Enter the account number to transfer in form of dddd-dddd-dddd-dddd:",
                        "Transfer", JOptionPane.PLAIN_MESSAGE);
                if (accountNumber == null || accountNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid account number.", "Transfer",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Checking the accountNumber is of the following pattern or not
                String accountNumberPattern = "\\d{4}-\\d{4}-\\d{4}-\\d{4}"; // Example pattern: 1234-5678-9012-3456
                if (!accountNumber.matches(accountNumberPattern)) {
                    JOptionPane.showMessageDialog(null, "Invalid account number format.", "Transfer",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                balance -= amount;
                String transaction = "Transfer: Rs. " + amount + " to account number " + accountNumber;
                transactionHistory.add(transaction);
                JOptionPane.showMessageDialog(null, transaction, "Transfer", JOptionPane.INFORMATION_MESSAGE);

                // Showing available balance
                JOptionPane.showMessageDialog(null, "Available balance: Rs. " + balance, "Transfer",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount.", "Transfer", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String args[]) {
        ATM_Interface obj = new ATM_Interface();
    }
}
