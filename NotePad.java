package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter;
import javax.swing.text.Document;
import javax.swing.JOptionPane;

public class NotePad extends JFrame implements ActionListener, KeyListener {

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }

    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.YELLOW);

    public void highlight(JTextComponent textcomp, String pattern) {

        try {
            Highlighter hilite = textcomp.getHighlighter();
            Document doc = textcomp.getDocument();

            String text = doc.getText(0, doc.getLength());
            int pos = 0;

            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }
        } catch (Exception e) {

        }
    }

    private Stack stack = new Stack();
    private JTextArea area;
    private JScrollPane scpane;
    String text = "";

    public NotePad() {
        super("Notepad - MANIO ---- HUZAIFA");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar(); //menubar
        menuBar.setBackground(Color.white);
        menuBar.setForeground(Color.black);

        JMenu file = new JMenu("File"); //file menu
        file.setBackground(Color.white);

        JMenuItem NewPad = new JMenuItem("New");
        NewPad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        NewPad.addActionListener(this);
        NewPad.setBackground(Color.white);

        JMenuItem opnPad = new JMenuItem("Open");
        opnPad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        opnPad.addActionListener(this);
        opnPad.setBackground(Color.white);

        JMenuItem savePad = new JMenuItem("Save");
        savePad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        savePad.addActionListener(this);
        savePad.setBackground(Color.white);

        JMenuItem extPad = new JMenuItem("Exit");
        extPad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        extPad.addActionListener(this);
        extPad.setBackground(Color.white);

        JMenu editPad = new JMenu("Edit");
        
        JMenuItem find = new JMenuItem("Find");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        find.addActionListener(this);
        find.setBackground(Color.white);

        JMenuItem replace = new JMenuItem("Find and Replace");
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        replace.addActionListener(this);
        replace.setBackground(Color.white);

        JMenuItem slctAllText = new JMenuItem("Select All");
        slctAllText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        slctAllText.addActionListener(this);
        slctAllText.setBackground(Color.white);

        JMenuItem undText = new JMenuItem("Undo");
        undText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undText.addActionListener(this);
        undText.setBackground(Color.white);

        JMenuItem rdoText = new JMenuItem("Redo");
        rdoText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        rdoText.addActionListener(this);
        rdoText.setBackground(Color.white);


        JMenuItem notepad = new JMenuItem("About Notepad");
        notepad.addActionListener(this);

        area = new JTextArea();
        area.setFont(new Font("Calibri", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(Color.white);
        area.addKeyListener(this);
        //area.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        scpane = new JScrollPane(area);
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(editPad);

        file.add(NewPad);
        file.add(opnPad);
        file.add(savePad);
        file.add(extPad);

        editPad.add(slctAllText);
        editPad.add(undText);
        editPad.add(rdoText);
        editPad.add(find);
        editPad.add(replace);

        add(scpane, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("New")) {
            area.setText("");

        } else if (actionEvent.getActionCommand().equals("Open")) {
            stack.push(" ");
            JFileChooser chooser = new JFileChooser("D:");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(this);
            if (actionEvent.getActionCommand().equals("Save")) {
                final JFileChooser SaveAs = new JFileChooser();
                SaveAs.setApproveButtonText("Save");
                int actionDialog = SaveAs.showOpenDialog(this);
                if (actionDialog != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                File fileName = new File(SaveAs.getSelectedFile() + ".txt");
                BufferedWriter outFile = null;
                try {
                    outFile = new BufferedWriter(new FileWriter(fileName));
                    area.write(outFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stack.push(area.getText());
            } else if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try {
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read(br, null);
                    br.close();
                    area.requestFocus();
                    stack.push(area.getText());
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
        } else if (actionEvent.getActionCommand().equals("Select All")) {
            area.selectAll();
        } else if (actionEvent.getActionCommand().equals("Exit")) {
            System.exit(0);
        } else if (actionEvent.getActionCommand().equals("Save")) {
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (actionEvent.getActionCommand().equals("Undo")) {
            String textt = stack.pop();
            area.setText(textt);
        } else if (actionEvent.getActionCommand().equals("Redo")) {
            String textt = stack.redoPop();
            area.setText(textt);
        } else if (actionEvent.getActionCommand().equals("Find")) {
            JFrame findFrame = new JFrame("Find");
            findFrame.setSize(400, 150);
            findFrame.setLocationRelativeTo(null);
            findFrame.setLayout(null);
            findFrame.setResizable(false);

            findFrame.setBackground(Color.white);

            JTextField textArea = new JTextField(20);
            JButton btn = new JButton("Find");

            JLabel findLabel = new JLabel("Find ");
            findLabel.setBounds(25, 30, 40, 40);
            findFrame.add(findLabel);
            textArea.setBounds(60, 35, 150, 30);

            JLabel Label1 = new JLabel("");
            Label1.setBounds(60, 55, 150, 30);
            findFrame.add(Label1);
            btn.setBounds(250, 40, 70, 30);
            btn.setBackground(Color.white);

            findFrame.add(textArea);
            findFrame.add(btn);

            findFrame.setVisible(true);

            findFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    if (JOptionPane.showConfirmDialog(findFrame,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        area.setText(area.getText());

                    }
                }
            });

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String textIn = area.getText();
                    if (textIn.contains(textArea.getText())) {

                        if (textIn.charAt(textIn.length() - 1) != ' ') {

                            Label1.setText(" ");
                            String line = textIn + " ";
                            area.setText(line);
                        }

                        if (!textIn.equals("")) {

                            String[] words = textIn.split("\\s+");
                            int count = 0;
                            for (int i = 0; i < words.length; i++) {
                                if (words[i].equals(textArea.getText())) {
                                    count++;
                                }
                            }
                            Label1.setText("Word Exist : " + Integer.toString(count) + " times");
                            highlight(area, textArea.getText());
                            String areaText = area.getText();
                            stack.push(areaText);
                        }
                        return;
                    } else {

                        Label1.setText("Match Word Not Found");
                    }
                }
            });
            // findFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (actionEvent.getActionCommand().equals("Find and Replace")) {
            JFrame replaceFrame = new JFrame("Find and Replace");
            replaceFrame.setSize(400, 150);
            replaceFrame.setLocationRelativeTo(null);
            replaceFrame.setLayout(null);
            replaceFrame.setBackground(Color.white);
            replaceFrame.setResizable(false);

            JLabel findLabel = new JLabel("Find ");
            JTextField findText = new JTextField(20);

            findLabel.setBounds(40, 20, 50, 25);
            findText.setBounds(70, 20, 150, 25);
            replaceFrame.add(findText);
            replaceFrame.add(findLabel);

            JLabel replaceLabel = new JLabel("Replace ");
            JTextField replaceText = new JTextField(20);

            replaceLabel.setBounds(20, 50, 50, 25);
            replaceText.setBounds(70, 50, 150, 25);
            replaceFrame.add(replaceText);
            replaceFrame.add(replaceLabel);

            JButton btn = new JButton("Replace");

            btn.setBounds(240, 20, 90, 55);
            btn.setBackground(Color.white);
            JLabel label2 = new JLabel();
            label2.setBounds(70, 70, 150, 25);
            replaceFrame.add(label2);

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String text = area.getText();
                    String fText = findText.getText();
                    String rText = replaceText.getText();

                    if (text.contains(fText)) {
                        label2.setText("");

                        String replaceString = text.replace(fText, rText);
                        area.setText(replaceString);
                        stack.push(replaceString);
                        return;
                    } else {

                        label2.setText("Match Not Found");

                    }
                }
            });

            replaceFrame.add(btn);

            replaceFrame.setVisible(true);
        }

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyChar() == ',' || e.getKeyChar() == '.' || e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            stack.push(area.getText());
            System.out.println("pushed");
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new NotePad();
    }
}
