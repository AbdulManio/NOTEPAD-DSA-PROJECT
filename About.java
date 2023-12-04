package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class About extends JFrame implements ActionListener {
    JButton b1;

    About() {
        setBounds(600, 200, 700, 600);
        setLayout(new BorderLayout());

        ImageIcon i1 = new ImageIcon(getClass().getResource("/project/windows.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 80, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        add(l1, BorderLayout.NORTH);

        ImageIcon i4 = new ImageIcon(getClass().getResource("/project/notepad.png"));
        Image i5 = i4.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        add(l2, BorderLayout.WEST);

        JLabel l3 = new JLabel("<html>'Notepad is a word processing program,"
                + "<br>which allows changing of text in a computer file."
                + "<br>Notepad is a simple text editor for basic text-editing program<br>"
                + " which enables computer users to create documents.</html>");
        l3.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        add(l3, BorderLayout.CENTER);

        b1 = new JButton("OK");
        b1.addActionListener(this);
        add(b1, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent ae) {
        this.setVisible(false);
    }

    public static void main(String[] args) {
        new About().setVisible(true);
    }
}
