package frames;

import entities.Account;
import fileio.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame
    {
    private static final Color BG_DARK = new Color(10, 20, 50);   
    private static final Color BG_HEADER = new Color(15,30,70);  
    private static final Color TABLE_ROW_BG = new Color(20,35,70);  
    private static final Color TABLE_HEADER = new Color(0,100,180);  
    private static final Color TABLE_GRID = new Color(50,70,120);   
    private static final Color BTN_ADD = new Color(0,200,0);  
    private static final Color BTN_DEPOSIT = new Color(0,150,220);  
    private static final Color BTN_WITHDRAW = new Color(220,150,0);   
    private static final Color BTN_HISTORY = new Color(150,0,220);  
    private static final Color BTN_LOGOUT = new Color(220,0,0);  
    private static final Font TITLE_FONT = new Font("Arial Black",Font.BOLD,22);
    private static final Font BTN_FONT = new Font("Arial",Font.BOLD,13);
    private static final Font TABLE_FONT = new Font("Arial",Font.PLAIN,14);
    private static final Font TABLE_HDR_FONT = new Font("Arial", Font.BOLD,14);
    private static final String[] TABLE_COLUMNS = {"Account Number", "Holder Name", "Balance (BDT)"};

    private JTable accountTable;
    private DefaultTableModel tableModel;

    public DashboardFrame()
    {
        setTitle("Java Bank-Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
		
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_DARK);

        mainPanel.add(buildHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(buildTablePanel(),  BorderLayout.CENTER);

        setContentPane(mainPanel);
        loadAccounts();   
    }

    private JPanel buildHeaderPanel()
    {
        JLabel titleLabel = new JLabel("JAVA BANK-Dashboard");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);

        JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftSide.setBackground(BG_HEADER);
        leftSide.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        leftSide.add(titleLabel);
        JPanel rightSide = buildButtonPanel();

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.add(leftSide,BorderLayout.WEST);
        header.add(rightSide,BorderLayout.EAST);
        return header;
    }

    private JPanel buildButtonPanel()
    {
        JButton addBtn = createButton("Add Account",BTN_ADD);
        JButton depositBtn = createButton("Deposit",BTN_DEPOSIT);
        JButton withdrawBtn = createButton("Withdraw",BTN_WITHDRAW);
        JButton historyBtn = createButton("History",BTN_HISTORY);
        JButton logoutBtn = createButton("Logout",BTN_LOGOUT);
        
		addBtn.addActionListener(e ->
        {
            new AddAccountFrame(this).setVisible(true);
        });
		
        depositBtn.addActionListener(e ->
        {
            String accNum = getSelectedAccountNumber();
            if (accNum != null)
                new TransactionFrame(this, accNum, "DEPOSIT").setVisible(true);
        });

        withdrawBtn.addActionListener(e ->
        {
            String accNum = getSelectedAccountNumber();
            if (accNum != null)
                new TransactionFrame(this, accNum, "WITHDRAW").setVisible(true);
        });

        historyBtn.addActionListener(e ->
        {
            String accNum = getSelectedAccountNumber();
            if (accNum != null)
                new TransactionHistoryFrame(accNum).setVisible(true);
        });

        logoutBtn.addActionListener(e ->
        {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(BG_HEADER);
        btnPanel.add(addBtn);
        btnPanel.add(depositBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(historyBtn);
        btnPanel.add(logoutBtn);

        return btnPanel;
    }

    private JScrollPane buildTablePanel()
    {
        tableModel = new DefaultTableModel(TABLE_COLUMNS, 0)
        {
            public boolean isCellEditable(int row, int col) 
			{
				return false; 
			}
        };
		
        accountTable = new JTable(tableModel);
        accountTable.setBackground(TABLE_ROW_BG);
        accountTable.setForeground(Color.WHITE);
        accountTable.setFont(TABLE_FONT);
        accountTable.setRowHeight(30);
        accountTable.setSelectionBackground(TABLE_HEADER);
        accountTable.setGridColor(TABLE_GRID);
        accountTable.getTableHeader().setBackground(TABLE_HEADER);
        accountTable.getTableHeader().setForeground(Color.WHITE);
        accountTable.getTableHeader().setFont(TABLE_HDR_FONT);
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.setBackground(BG_DARK);
        scrollPane.getViewport().setBackground(TABLE_ROW_BG);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        return scrollPane;
    }

    private String getSelectedAccountNumber()
    {
        int selectedRow = accountTable.getSelectedRow();

        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an account first.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return (String) tableModel.getValueAt(selectedRow, 0);
    }

    public void loadAccounts()
    {
        tableModel.setRowCount(0); 

        List<Account> accounts = FileHandler.loadAccounts();

        for (Account acc : accounts)
        {
            tableModel.addRow(new Object[]
            {
                acc.getAccountNumber(),
                acc.getHolderName(),
                String.format("%.2f", acc.getBalance())  
            });
        }
    }
	
    private JButton createButton(String text, Color bgColor)
    {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}