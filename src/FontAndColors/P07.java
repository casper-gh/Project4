package FontAndColors;
/*
 *		Group:		2
 *      Name:       Chiu, Chi Lung - Westerhoff, Bradley - Huang, Wayne - Tello, Eric
 *      Project:    #7
 *      Course:     CS-245-01-f11
 *
 *      Description:
 *                  Font Changer
 *                  Displays a sample text and enables user to select font, size, style
 *                  Also enables user to select text and background color of text
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

class P07 implements ActionListener
{
    JFrame jfrm;
    JList fontsList, sizeList, styleList, bgColorList, textColorList;
    JLabel display;
    JPanel displayPanel;
    JTextField fontField, sizeField, styleField;

    //The font, style and size of the display label
    String font;
    int style = Font.PLAIN;
    int size = 30;
    Color bgColor = Color.WHITE;
    Color textColor = Color.BLACK;

    //Creates a list of all available fonts in java
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //List of fonts sizes
    String[] sizes = new String[60];
    //List of styles
    String[] styles = { "Plain", "Italic", "Bold", "Bold Italic" };
    //List of colors
    Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
                      Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                      Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    String[] colorSTR = {"Black", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Yellow"};

    P07()
    {
        jfrm = new JFrame("Font Changing");
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(620, 365);
        jfrm.getContentPane().setBackground(new Color(230, 226, 226));

        display = new JLabel("Font Demonstration");
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(display,BorderLayout.CENTER);
        display.setHorizontalAlignment(JTextField.CENTER);
        displayPanel.setPreferredSize(new Dimension(595,100));
        displayPanel.setBorder(BorderFactory.createEtchedBorder());

        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        //Font List
        fontField = new JTextField();
        fontsList = new JList(fonts);
        JScrollPane fontSP = new JScrollPane(fontsList);
        //fontSP.setPreferredSize(new Dimension(50,50));
        fontsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = fontsList.getSelectedIndex();
                font = fonts[idx];
                fontField.setText(font);
                display.setFont(new Font(font, style, size));
            }
        });
        JPanel fontPnl = new JPanel(new BorderLayout());
        fontPnl.add(fontField, BorderLayout.NORTH);
        fontPnl.add(fontSP, BorderLayout.CENTER);
        fontPnl.setPreferredSize(new Dimension(210, 189));

        //Font Size List
        sizeField = new JTextField();
        sizeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean isNum = true;
                int temp = size;
                try {
                    temp = Integer.parseInt(sizeField.getText());
                }
                catch(NumberFormatException nfe) {
                    isNum = false;
                }
                if(isNum)
                {
                    size = temp;
                    display.setFont(new Font(font, style, size));
                }
            }

        });
        for(int i=0; i<60; i++)
            sizes[i] = (i+10)+"";
        sizeList = new JList(sizes);
        JScrollPane sizeSP = new JScrollPane(sizeList);
        sizeList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = sizeList.getSelectedIndex();
                size = Integer.parseInt(sizes[idx]);
                sizeField.setText(size+"");
                display.setFont(new Font(font, style, size));
            }
        });

        JPanel sizePNL = new JPanel(new BorderLayout());
        sizePNL.add(sizeField, BorderLayout.NORTH);
        sizePNL.add(sizeSP, BorderLayout.CENTER);
        sizePNL.setPreferredSize(new Dimension(50,189));

        //Font Style list
        styleField = new JTextField();
        styleList = new JList(styles);
        JScrollPane styleSP = new JScrollPane(styleList);
        styleList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = styleList.getSelectedIndex();
                if(styles[idx].equals("Plain"))
                    style = Font.PLAIN;
                else if(styles[idx].equals("Italic"))
                    style = Font.ITALIC;
                else if(styles[idx].equals("Bold"))
                    style = Font.BOLD;
                else if(styles[idx].equals("Bold Italic"))
                    style = Font.BOLD | Font.ITALIC;
                styleField.setText(styles[idx]);
                display.setFont(new Font(font, style, size));
            }
        });
        JPanel stylePnl = new JPanel(new BorderLayout());
        stylePnl.add(styleField, BorderLayout.NORTH);
        stylePnl.add(styleSP, BorderLayout.CENTER);
        stylePnl.setPreferredSize(new Dimension(60, 189));


        //Text Color
        JLabel textColorLab = new JLabel("Choose Text Color");
        JButton textColorBTN = new JButton ("More Options");
        textColorBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Color color = JColorChooser.showDialog(jfrm, "Choose Color", textColor);
                textColor = color;
                display.setForeground(textColor);
            }
        });
        textColorList = new JList(colorSTR);
        textColorList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = textColorList.getSelectedIndex();
                textColor = colors[idx];
                display.setForeground(textColor);
            }
        });
        JScrollPane textColorSP = new JScrollPane(textColorList);
        //Panel to fit em all together
        JPanel textColorPNL = new JPanel(new BorderLayout());
        textColorPNL.add(textColorLab, BorderLayout.NORTH);
        textColorLab.setHorizontalAlignment(JTextField.CENTER);
        textColorPNL.add(textColorSP);
        textColorPNL.add(textColorBTN, BorderLayout.SOUTH);
        textColorBTN.setHorizontalAlignment(JTextField.CENTER);
        textColorPNL.setBackground(new Color(230, 226, 226));

        //Background Color
        JLabel backgroundLab = new JLabel("Choose Background Color");
        JButton backgroundColorBTN = new JButton ("More Options");
        backgroundColorBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Color color = JColorChooser.showDialog(jfrm, "Choose Color", bgColor);
                bgColor = color;
                displayPanel.setBackground(bgColor);
            }
        });
        bgColorList = new JList(colorSTR);
        bgColorList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = bgColorList.getSelectedIndex();
                bgColor = colors[idx];
                displayPanel.setBackground(bgColor);
            }
        });
        JScrollPane bgColorSP = new JScrollPane(bgColorList);
        //Panel to fit em all together
        JPanel bgColorPNL = new JPanel(new BorderLayout());
        bgColorPNL.add(backgroundLab, BorderLayout.NORTH);
        backgroundLab.setHorizontalAlignment(JTextField.CENTER);
        bgColorPNL.add(bgColorSP);
        bgColorPNL.add(backgroundColorBTN, BorderLayout.SOUTH);
        backgroundColorBTN.setHorizontalAlignment(JTextField.CENTER);
        bgColorPNL.setBackground(new Color(230, 226, 226));

        //Menu
        JMenuBar jmb = new JMenuBar();
        //File Menu
        JMenu fileMenu = new JMenu("File");
        //Exit Button - Accelerator to Ctrl+X
        JMenuItem jmiExit = new JMenuItem("Exit",KeyEvent.VK_X);
        jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        jmiExit.addActionListener(this);
        fileMenu.add(jmiExit);
        //Help menu
        JMenu helpMenu = new JMenu("Help");
        //About Button - Shows information
        JMenuItem jmiAbout = new JMenuItem("About", KeyEvent.VK_A);
        jmiAbout.addActionListener(this);
        //Compiling menu bars
        helpMenu.add(jmiAbout);
        jmb.add(fileMenu);
        jmb.add(helpMenu);

        jfrm.setJMenuBar(jmb);
        jfrm.add(displayPanel);
        jfrm.add(fontPnl);
        jfrm.add(sizePNL);
        jfrm.add(stylePnl);
        jfrm.add(bgColorPNL);
        jfrm.add(textColorPNL);

        jfrm.setResizable(false);
        jfrm.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        System.out.println(jfrm.getSize());
        String cmd = ae.getActionCommand().toLowerCase();
        if(cmd.equals("exit"))
            System.exit(0);
        if(cmd.equals("about"))
            JOptionPane.showMessageDialog(jfrm, "<html> Team: <br> Alex Chiu <br> Bradley Westerhoff  <br> Wayne Huang <br> Eric Tello", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new P07();
            }
        });
    }
}
