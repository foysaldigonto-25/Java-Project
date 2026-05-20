package frames;

import entities.Account;
import entities.Transaction;
import fileio.FileHandler;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddAccountFrame extends JFrame
    {
    private static final Color BG_COLOR = new Color(10,20,50);    
    private static final Color LABEL_BG = new Color(0,220,0);     
    private static final Color LABEL_FG = new Color(0,0,180);     
    private static final Color FIELD_BG = new Color(0,230,230);   
    private static final Color CREATE_BTN_COLOR = new Color(0,200,0);     
    private static final Color CANCEL_BTN_COLOR = new Color(220,0,0);     
    private static final Font TITLE_FONT = new Font("Arial Black",Font.BOLD,20);
    private static final Font LABEL_FONT = new Font("Arial",Font.BOLD,15);
    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 15);

    private DashboardFrame parent;

    public AddAccountFrame(DashboardFrame parent)
    {
        this.parent = parent;
        setTitle("Java Bank - Add Account");
        setSize(500, 320);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);

        panel.add(createTitleLabel());
        
        JTextField nameField = createInputField(" "); 
        JTextField depositField = createInputField(" ");

        panel.add(createLabel("Holder Name :",50,80));
        panel.add(placeField(nameField,220,080));

        panel.add(createLabel("Initial Deposit :",50,140));
        panel.add(placeField(depositField,220,140));

        JButton createBtn = createButton("Create Account",CREATE_BTN_COLOR,100,220,180,45);
        JButton cancelBtn = createButton("Cancel",CANCEL_BTN_COLOR,300,220,130,45);
        panel.add(createBtn);
        panel.add(cancelBtn);
        createBtn.addActionListener(e -> handleCreateAccount(nameField, depositField));
        cancelBtn.addActionListener(e -> this.dispose());

        setContentPane(panel);
    }

    private void handleCreateAccount(JTextField nameField,JTextField depositField)
    {
        String name = nameField.getText().trim();
        String depositStr = depositField.getText().trim();

        if (name.isEmpty())
        {
            JOptionPane.showMessageDialog(this,"Please enter holder name.","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try
        {
            double deposit = Double.parseDouble(depositStr);
            if (deposit < 0) throw new NumberFormatException();
            String accNum = FileHandler.generateAccountNumber();
            Account newAcc = new Account(accNum,name,deposit);
            List<Account> accounts = FileHandler.loadAccounts();
            accounts.add(newAcc);
            FileHandler.saveAccounts(accounts);

            if (deposit > 0)
            {
                Transaction t = new Transaction(accNum,"DEPOSIT",deposit,deposit);
                FileHandler.appendTransaction(t);
            }
            JOptionPane.showMessageDialog(this,"Account created!\nAccount Number: " + accNum,"Success", JOptionPane.INFORMATION_MESSAGE);
            parent.loadAccounts();
            this.dispose();
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this,"Invalid deposit amount.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    private JLabel createTitleLabel()
    {
        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);
        title.setBounds(50, 15, 400, 40);
        return title;
    }
    private JLabel createLabel(String text,int x,int y)
    {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_FG);
        label.setBackground(LABEL_BG);
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(x, y, 160, 40);
        return label;
    }
    private JTextField createInputField(String defaultText)
    {
    JTextField field = new JTextField();
    field.setText("");
    field.setBackground(FIELD_BG);
    field.setFont(FIELD_FONT);
    return field;
    }
    private JTextField placeField(JTextField field,int x,int y)
    {
        field.setBounds(x, y, 220, 40);
        return field;
    }
    private JButton createButton(String text, Color bgColor, int x, int y, int width, int height)
    {
        JButton btn = new JButton(text);
        btn.setFont(LABEL_FONT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBounds(x,y,width,height);
        return btn;
    }
}