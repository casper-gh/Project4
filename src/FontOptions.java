import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

class FontOptions {
    JDialog jdlg;
    JList fontsJl, sizeJl, styleJl, backgroundColorJl, textColorJl;
    JTextField fontJtf, sizeJtf, styleJtf;
    JTextArea jta;
    JPanel jpn;
    JLabel jlbl;
   
    String font;
    Color defaultBackgroundColor = Color.WHITE;
    Color defaultTextColor = Color.BLACK;
    int defaultTextStyle = Font.PLAIN;
    int defaultTextSize = 12;

    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String[] sizes = new String[60];
    String[] styles = { "Plain", "Italic", "Bold", "Bold Italic" };
    Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY,
                      Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
                      Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    String[] colorsText = {"Black", "Blue", "Cyan", "Dark Gray", 
				    		"Gray", "Green", "Light Gray", "Magenta", 
				    		"Orange", "Pink", "Red", "White", "Yellow"};

    FontOptions(JFrame parentJfrm, JTextArea parentJta) {
    	jta = parentJta;
    	
        jdlg = new JDialog(parentJfrm, "AaBbYyZz", true);
        jdlg.setLayout(new FlowLayout());
	    FlowLayout flow = new FlowLayout();
	    flow.setVgap(13);  
	    JPanel infoPanel = new JPanel(flow);
        jdlg.setSize(620, 350);
        jdlg.setLocationRelativeTo(parentJfrm);
        
        jlbl = new JLabel("AaBbYyZz");
        jpn = new JPanel(new BorderLayout());
        jpn.add(jlbl,BorderLayout.CENTER);
        jlbl.setHorizontalAlignment(JTextField.CENTER);
        jpn.setPreferredSize(new Dimension(440,100));
        jpn.setBorder(BorderFactory.createEtchedBorder());

        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        //Font List
        fontJtf = new JTextField();
        fontsJl = new JList(fonts);
        JScrollPane fontJSP = new JScrollPane(fontsJl);
        fontsJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = fontsJl.getSelectedIndex();
                font = fonts[idx];
                fontJtf.setText(font);
                jlbl.setFont(new Font(font, defaultTextStyle, defaultTextSize));
            }
        });
        JPanel fontPnl = new JPanel(new BorderLayout());
        fontPnl.add(fontJtf, BorderLayout.NORTH);
        fontPnl.add(fontJSP, BorderLayout.CENTER);
        fontPnl.setPreferredSize(new Dimension(210, 189));

        //Font Size List
        sizeJtf = new JTextField();
        sizeJtf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean isNum = true;
                int temp = defaultTextSize;
                try {
                    temp = Integer.parseInt(sizeJtf.getText());
                }
                catch(NumberFormatException nfe) {
                    isNum = false;
                }
                if(isNum) {
                    defaultTextSize = temp;
                    jlbl.setFont(new Font(font, defaultTextStyle, defaultTextSize));
                }
            }
        });
        for(int i=0; i<60; i++)
            sizes[i] = (i+10)+"";
        sizeJl = new JList(sizes);
        JScrollPane sizeSP = new JScrollPane(sizeJl);
        sizeJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = sizeJl.getSelectedIndex();
                defaultTextSize = Integer.parseInt(sizes[idx]);
                sizeJtf.setText(defaultTextSize+"");
                jlbl.setFont(new Font(font, defaultTextStyle, defaultTextSize));
            }
        });

        JPanel sizePNL = new JPanel(new BorderLayout());
        sizePNL.add(sizeJtf, BorderLayout.NORTH);
        sizePNL.add(sizeSP, BorderLayout.CENTER);
        sizePNL.setPreferredSize(new Dimension(50,189));

        //Font Style list
        styleJtf = new JTextField();
        styleJl = new JList(styles);
        JScrollPane styleSP = new JScrollPane(styleJl);
        styleJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = styleJl.getSelectedIndex();
                if(styles[idx].equals("Plain"))
                    defaultTextStyle = Font.PLAIN;
                else if(styles[idx].equals("Italic"))
                    defaultTextStyle = Font.ITALIC;
                else if(styles[idx].equals("Bold"))
                    defaultTextStyle = Font.BOLD;
                else if(styles[idx].equals("Bold Italic"))
                    defaultTextStyle = Font.BOLD | Font.ITALIC;
                styleJtf.setText(styles[idx]);
                jlbl.setFont(new Font(font, defaultTextStyle, defaultTextSize));
            }
        });
        JPanel stylePnl = new JPanel(new BorderLayout());
        stylePnl.add(styleJtf, BorderLayout.NORTH);
        stylePnl.add(styleSP, BorderLayout.CENTER);
        stylePnl.setPreferredSize(new Dimension(60, 189));

        //Text Color
        JLabel textColorLab = new JLabel("Choose Text Color");
        JButton textColorBTN = new JButton ("More Options");
        textColorBTN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Color color = JColorChooser.showDialog(jdlg, "Choose Color", defaultTextColor);
                defaultTextColor = color;
                jlbl.setForeground(defaultTextColor);
            }
        });
        textColorJl = new JList(colorsText);
        textColorJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = textColorJl.getSelectedIndex();
                defaultTextColor = colors[idx];
                jlbl.setForeground(defaultTextColor);
            }
        });
        JScrollPane textColorSP = new JScrollPane(textColorJl);
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
                Color color = JColorChooser.showDialog(jdlg, "Choose Color", defaultBackgroundColor);
                defaultBackgroundColor = color;
                jpn.setBackground(defaultBackgroundColor);
            }
        });
        backgroundColorJl = new JList(colorsText);
        backgroundColorJl.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent le) {
                int idx = backgroundColorJl.getSelectedIndex();
                defaultBackgroundColor = colors[idx];
                jpn.setBackground(defaultBackgroundColor);
            }
        });
        JScrollPane bgColorSP = new JScrollPane(backgroundColorJl);
        //Panel to fit em all together
        JPanel bgColorPNL = new JPanel(new BorderLayout());
        bgColorPNL.add(backgroundLab, BorderLayout.NORTH);
        backgroundLab.setHorizontalAlignment(JTextField.CENTER);
        bgColorPNL.add(bgColorSP);
        bgColorPNL.add(backgroundColorBTN, BorderLayout.SOUTH);
        backgroundColorBTN.setHorizontalAlignment(JTextField.CENTER);
        bgColorPNL.setBackground(new Color(230, 226, 226));
        
        JButton okBtn = new JButton ("   OK   ");
        okBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jta.setFont(new Font(font, defaultTextStyle, defaultTextSize));
                jta.setForeground(defaultTextColor);
                jta.setBackground(defaultBackgroundColor);
            	jdlg.setVisible(false);
            }
        });
        
        JButton cancelBtn = new JButton ("Cancel");
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	jdlg.setVisible(false);
            }
        });        
        
        jdlg.add(fontPnl);
        jdlg.add(sizePNL);
        jdlg.add(stylePnl);
        jdlg.add(bgColorPNL);
        jdlg.add(textColorPNL);
        jdlg.add(jpn);
        jdlg.add(okBtn);
        jdlg.add(cancelBtn);
    }
}
