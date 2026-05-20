package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class LoginFrame extends JFrame
{
  
    private static final String BG_IMAGE_PATH = "Java_Bank_pic.png"; 
    private static final Color LABEL_BG = new Color(180,140,50);
    private static final Color LABEL_FG = new Color(10,20,50); 
    private static final Color FIELD_BG = new Color(255,255,255);
    private static final Color FIELD_BORDER = new Color(180,140,50);
    private static final Color BTN_LOGIN = new Color(180,140,50); 
    private static final Color BTN_EXIT = new Color(120,30,50);
    private static final Font TITLE_FONT = new Font("Arial Black",Font.BOLD,42);
    private static final Font LABEL_FONT = new Font("Arial",Font.BOLD,18);
    private static final Font FIELD_FONT = new Font("Arial",Font.PLAIN,16);
    private static final Font HINT_FONT = new Font("Arial",Font.ITALIC,13);
    private static final Font BTN_FONT = new Font("Arial",Font.BOLD,18);

    private static final String ADMIN_USERNAME = "Shefat Sir";
    private static final String ADMIN_PASSWORD = "9301";

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame()
    {
        setTitle("Java Bank-Login");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = buildBackgroundPanel();  
        panel.setLayout(null);

        panel.add(buildTitleLabel());
        panel.add(buildLabel("Username :",200,180));
        panel.add(buildLabel("Password :",200,255));

        usernameField = buildTextField(410,180);
        passwordField = buildPasswordField(410,255);
        panel.add(usernameField);
        panel.add(passwordField);

        panel.add(buildHintLabel());

        JButton loginBtn = buildButton("Login",BTN_LOGIN,215,375);
        JButton exitBtn = buildButton("Exit",BTN_EXIT,430,375);
        panel.add(loginBtn);
        panel.add(exitBtn);

        loginBtn.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin());
        exitBtn.addActionListener(e -> System.exit(0));
        setContentPane(panel);
    }

    private void doLogin()
    {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD))
        {
            new DashboardFrame().setVisible(true);
            this.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private JPanel buildBackgroundPanel()
    {
        BufferedImage bgImage = loadBackgroundImage();

        return new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);

                if (bgImage != null)
                {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
                else
                {
                    g.setColor(new Color(10, 20, 50));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
    }
	
    private BufferedImage loadBackgroundImage()
    {
        try
        {
            return ImageIO.read(new File(BG_IMAGE_PATH));
        }
        catch (IOException e)
        {
            System.out.println("Background image not found !!");
            return null;
        }
    }

    private JLabel buildTitleLabel()
    {
        JLabel label = new JLabel("JAVA BANK", SwingConstants.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(Color.WHITE);
        label.setBounds(250, 50, 500, 70);
        return label;
    }

    private JLabel buildLabel(String text, int x, int y)
    {
        JLabel label = new JLabel(text,SwingConstants.CENTER);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_FG);
        label.setBackground(LABEL_BG);
        label.setOpaque(true);
        label.setBounds(x, y, 200, 45);
        return label;
    }

    private JTextField buildTextField(int x, int y)
    {
        JTextField field = new JTextField();
        field.setBackground(FIELD_BG);
        field.setFont(FIELD_FONT);
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 2));
        field.setBounds(x, y, 280, 45);
        return field;
    }

    private JPasswordField buildPasswordField(int x, int y)
    {
        JPasswordField field = new JPasswordField();
        field.setBackground(FIELD_BG);
        field.setFont(FIELD_FONT);
        field.setBorder(BorderFactory.createLineBorder(FIELD_BORDER, 2));
        field.setBounds(x, y, 280, 45);
        return field;
    }

    private JLabel buildHintLabel()
    {
        JLabel hint = new JLabel("Hint: Faculty / Room Number");
        hint.setFont(HINT_FONT);
        hint.setForeground(new Color(200, 200, 200));
        hint.setBounds(200, 315, 250, 25);
        return hint;
    }

    private JButton buildButton(String text, Color bgColor, int x, int y)
    {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBounds(x, y, 200, 55);
        return btn;
    }
}