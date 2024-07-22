import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DSA_Assignment {
    private String text = "";
    
    Stack<Character> u = new Stack<>();

    private Font currentFont = new Font("Arial", Font.PLAIN, 14);
    private Color currentColor = Color.BLACK;

    private JTextArea ta;
    private JButton undoButton;
    private JButton redoButton;
    private JComboBox<String> fontComboBox;
    private JComboBox<String> styleComboBox;
    private JComboBox<String> sizeComboBox;
    private JButton colorButton;

    public Text_editor() {
        JFrame frame = new JFrame("Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ta = new JTextArea(text);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setFont(currentFont);
        ta.setForeground(currentColor);
        ta.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        fontComboBox = new JComboBox<>(new String[]{"Arial", "Times New Roman", "Courier New"});
        fontComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedFont = (String) fontComboBox.getSelectedItem();
                    currentFont = new Font(selectedFont, currentFont.getStyle(), currentFont.getSize());
                    ta.setFont(currentFont);
                }
            }
        });

        styleComboBox = new JComboBox<>(new String[]{"Plain", "Bold", "Italic", "Bold Italic"});
        styleComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedStyle = (String) styleComboBox.getSelectedItem();
                    int style = Font.PLAIN;

                    if (selectedStyle.equals("Bold")) {
                        style = Font.BOLD;
                    } else if (selectedStyle.equals("Italic")) {
                        style = Font.ITALIC;
                    } else if (selectedStyle.equals("Bold Italic")) {
                        style = Font.BOLD + Font.ITALIC;
                    }

                    currentFont = currentFont.deriveFont(style);
                    ta.setFont(currentFont);
                }
            }
        });

        sizeComboBox = new JComboBox<>(new String[]{"12", "14", "16", "18", "20"});
        sizeComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int selectedSize = Integer.parseInt((String) sizeComboBox.getSelectedItem());
                    currentFont = currentFont.deriveFont((float) selectedSize);
                    ta.setFont(currentFont);
                }
            }
        });

        colorButton = new JButton("Text Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseColor();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(fontComboBox);
        buttonPanel.add(styleComboBox);
        buttonPanel.add(sizeComboBox);
        buttonPanel.add(colorButton);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public void undo() {
        text = ta.getText();
        if (text.length() > 0) {
            char lastChar = text.charAt(text.length() - 1);
            u.push(lastChar);
            String newText = text.substring(0, text.length() - 1);
            ta.setText(newText);
        }
    }

    public void redo() {
        text = ta.getText();
        if (!u.isEmpty()) {
            char lastChar = u.pop();
            String newText = text + lastChar;
            ta.setText(newText);
        }
    }
    

    public void chooseColor() {
        Color newColor = JColorChooser.showDialog(null, "Choose Text Color", currentColor);
        if (newColor != null) {
            currentColor = newColor;
            ta.setForeground(currentColor);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DSA_Assignment();
            }
        });
    }
}
