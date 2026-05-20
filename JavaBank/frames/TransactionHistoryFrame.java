package frames;

import entities.Transaction;
import fileio.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionHistoryFrame extends JFrame 
    {

    public TransactionHistoryFrame(String accountNumber) 
	{
        setTitle("Java Bank--Transaction History: " + accountNumber);
        setSize(750,450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(10,20,50));

        JLabel titleLabel = new JLabel("Transactions for: " + accountNumber, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black",Font.BOLD,16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] cols = {"Type", "Amount (BDT)", "Balance After", "Date & Time"};
		
        DefaultTableModel model = new DefaultTableModel(cols, 0) 
		{
            public boolean isCellEditable(int r, int c) 
			{
				return false; 
			}
        };

        List<Transaction> transactions = FileHandler.loadTransactions();
        for (Transaction t : transactions) 
		{
            if (t.getAccountNumber().equals(accountNumber)) 
			{
                model.addRow(new Object[]{t.getType(),String.format("%.2f", t.getAmount()),String.format("%.2f", t.getBalanceAfter()),t.getDateTime()});   
            }
        }

        JTable table = new JTable(model);
        table.setBackground(new Color(20, 35, 70));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setBackground(new Color(0, 100, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setGridColor(new Color(50, 70, 120));

        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(new Color(20, 35, 70));
        sp.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        panel.add(sp, BorderLayout.CENTER);

        setContentPane(panel);
    }
}
