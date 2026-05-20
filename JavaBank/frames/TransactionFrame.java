package frames;

import entities.Account;
import entities.Transaction;
import fileio.FileHandler;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionFrame extends JFrame 
    {

    public TransactionFrame(DashboardFrame parent,String accountNumber,String type) 
	{
        setTitle("Java Bank - " + type);
        setSize(430, 260);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(10, 20, 50));

        JLabel titleLabel = new JLabel(type + " - " + accountNumber,SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black",Font.BOLD,16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20,15,380,35);
        panel.add(titleLabel);

        JLabel amtLabel = new JLabel("Amount (BDT) :");
        amtLabel.setFont(new Font("Arial", Font.BOLD, 15));
        amtLabel.setForeground(new Color(0, 0, 180));
        amtLabel.setBackground(new Color(0, 220, 0));
        amtLabel.setOpaque(true);
        amtLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amtLabel.setBounds(60, 80, 160, 40);
        panel.add(amtLabel);

        JTextField amtField = new JTextField();
        amtField.setBackground(new Color(0, 230, 230));
        amtField.setFont(new Font("Arial", Font.PLAIN, 15));
        amtField.setBounds(230, 80, 150, 40);
        panel.add(amtField);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 15));
        confirmBtn.setBackground(new Color(0, 200, 0));
        confirmBtn.setForeground(Color.BLACK);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.setBounds(80, 165, 130, 45);
        panel.add(confirmBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 15));
        cancelBtn.setBackground(new Color(220, 0, 0));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.setBounds(225, 165, 130, 45);
        panel.add(cancelBtn);

        confirmBtn.addActionListener(e -> {
           
		   try {
                double amount = Double.parseDouble(amtField.getText().trim());
				
                if (amount <= 0) throw new NumberFormatException();

                List<Account> accounts = FileHandler.loadAccounts();
                Account target = null;
				
                for (Account acc : accounts) 
				{
                    if (acc.getAccountNumber().equals(accountNumber)) 
					{
                        target = acc;
                        break;
                    }
                }

                if (target == null) 
				{
                    JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (type.equals("WITHDRAW") && target.getBalance() < amount) 
				{
                    JOptionPane.showMessageDialog(this, "Insufficient balance!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (type.equals("DEPOSIT")) 
				{
                    target.setBalance(target.getBalance() + amount);
                } 
				else 
				{
                    target.setBalance(target.getBalance() - amount);
                }

                FileHandler.saveAccounts(accounts);
                Transaction t = new Transaction(accountNumber, type, amount, target.getBalance());
                FileHandler.appendTransaction(t);

                JOptionPane.showMessageDialog(this, type + " successful!\nNew Balance: " + String.format("%.2f", target.getBalance()) + " BDT", "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.loadAccounts();
                this.dispose();
            } 
			
			catch (NumberFormatException ex) 
			{
                JOptionPane.showMessageDialog(this, "Enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> this.dispose());
        setContentPane(panel);
    }
}
