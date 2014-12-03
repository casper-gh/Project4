//
//Name:    Nguyen, Lam
//Project: #4
//Due:     December 4, 2014
//Course:  CS-245-01-w14
//
//Description:
//    A simple Java Swing notepad program
//

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.awt.datatransfer.*;
import java.awt.event.*;

public class JNotePad implements ActionListener {
   
	JFrame jfrm;
	JTextArea jta;
	JPopupMenu jpopup;
	File myFile;
	String openedFileName;
	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();   
	String stringToSearch = "";
	int searchTextPosition = 0;
	FontOptions myFont;

	JNotePad() {
		
		// Icon
		ImageIcon icon = new ImageIcon("JNotepad.png");	
		
		jfrm = new JFrame(getOpenedFileName());  
		
		// Set the program icon
		jfrm.setIconImage(icon.getImage());
		//Set frame size and layout
		jfrm.setSize(900, 600);
//		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		jfrm.setLayout(new GridLayout(1, 1));
		jfrm.setVisible(true);
                jfrm.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                
		//Set area
		setTextArea();
		setMenus();
		jfrm.setLocationRelativeTo(null);   
		
		// Load fonts
		myFont = new FontOptions(jfrm, jta);      
	}
	
	public String getOpenedFileName() throws NullPointerException {      
		try {
			openedFileName = myFile.getName();
		}
		catch(NullPointerException e) {
			openedFileName = "JNotepad";
		}
		return openedFileName;
	}

	public void setTextArea() {
		jta = new JTextArea();
		JScrollPane jsp = new JScrollPane(jta);
		jfrm.add(jsp);
	}
   
	public void setMenus() {
		JMenuBar menu = new JMenuBar();
        
        // File menu
        JMenu jmFile = new JMenu("File");         
        JMenuItem jmiNew = new JMenuItem("New");        
        JMenuItem jmiOpen = new JMenuItem("Open...");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiSaveAs = new JMenuItem("Save As...");
        JMenuItem jmiPageSetup = new JMenuItem("Page Setup...");    
        JMenuItem jmiPrint = new JMenuItem("Print...");
        JMenuItem jmiExit = new JMenuItem("Exit");        
        jmFile.add(jmiNew);
        jmFile.add(jmiOpen);
        jmFile.add(jmiSave);
        jmFile.add(jmiSaveAs);
        jmFile.add(new JSeparator());
        jmFile.add(jmiPageSetup);
        jmFile.add(jmiPrint);
        jmFile.add(new JSeparator());
        jmFile.add(jmiExit);
        menu.add(jmFile);          
                
        //Set mnemonic 
        jmiNew.setMnemonic(KeyEvent.VK_N);
        jmiPageSetup.setMnemonic(KeyEvent.VK_U);
        jmFile.setMnemonic(KeyEvent.VK_F);        
        jmiExit.setMnemonic(KeyEvent.VK_X);
        
        //Set accelerator 
        jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));        
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        jmiPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        jmiNew.addActionListener(this);
        jmiOpen.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiSaveAs.addActionListener(this);
        
        // Edit menu
        JMenu jmiEdit = new JMenu("Edit");
        JMenuItem jmiUndo = new JMenuItem("Undo");
        JMenuItem jmiCut = new JMenuItem("Cut");
        JMenuItem jmiCopy = new JMenuItem("Copy");
        JMenuItem jmiPaste = new JMenuItem("Paste");
        JMenuItem jmiDelete = new JMenuItem("Delete");        
        JMenuItem jmiFind = new JMenuItem("Find...");
        JMenuItem jmiFindNext = new JMenuItem("Find Next");
        JMenuItem jmiReplace = new JMenuItem("Replace...");
        JMenuItem jmiGoTo = new JMenuItem("Go To...");
        JMenuItem jmiSelectAll = new JMenuItem("Select All");
        JMenuItem jmiTimeDate = new JMenuItem("Time/Date");
        jmiEdit.add(jmiEdit);
        jmiEdit.add(jmiUndo);
        jmiEdit.add(new JSeparator());
        jmiEdit.add(jmiCut);
        jmiEdit.add(jmiCopy);
        jmiEdit.add(jmiPaste);
        jmiEdit.add(jmiDelete);
        jmiEdit.add(new JSeparator());
        jmiEdit.add(jmiFind);
        jmiEdit.add(jmiFindNext);
        jmiEdit.add(jmiReplace);
        jmiEdit.add(jmiGoTo);
        jmiEdit.add(new JSeparator());
        jmiEdit.add(jmiSelectAll);
        jmiEdit.add(jmiTimeDate);
        menu.add(jmiEdit);
        
		// Create a popup menu
		jpopup = new JPopupMenu( "popup" );		
        JMenuItem jmiCutPopup = new JMenuItem("Cut");
        JMenuItem jmiCopyPopup = new JMenuItem("Copy");
        JMenuItem jmiPastePopup = new JMenuItem("Paste");		
		jpopup.add( jmiCutPopup );
		jpopup.add( jmiCopyPopup );
		jpopup.add( jmiPastePopup );		
		jta.add(jpopup);
        jmiCutPopup.addActionListener(this);
        jmiCopyPopup.addActionListener(this);
        jmiPastePopup.addActionListener(this);

	    //Add listener to components that can bring up popup menus.
	    MouseListener popupListener = new PopupListener();
	    jta.addMouseListener(popupListener);
        
        //Set accelerator 
        jmiEdit.setMnemonic(KeyEvent.VK_E); 
        jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
        jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        jmiPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));       
        jmiDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
        jmiFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK)); 
        jmiReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
        jmiGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK)); 
        jmiSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK)); 
        jmiTimeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));
        
        // Set action listener
        jmiCut.addActionListener(this);
        jmiCopy.addActionListener(this);
        jmiPaste.addActionListener(this);
        jmiDelete.addActionListener(this);
        jmiFind.addActionListener(this);
        jmiFindNext.addActionListener(this);
        jmiTimeDate.addActionListener(this);   
        jmiSelectAll.addActionListener(this);   
        
        // -- Format menu --
        JMenu jmiFormat = new JMenu("Format");
        JMenuItem WordWrap = new JCheckBoxMenuItem("Word Wrap");
        JMenuItem Font = new JMenuItem("Font...");
        jmiFormat.add(WordWrap);
        jmiFormat.add(Font);
        
        // Set mnemonic
        jmiFormat.setMnemonic(KeyEvent.VK_O); 
        WordWrap.setMnemonic(KeyEvent.VK_W); 
        Font.setMnemonic(KeyEvent.VK_F);         
        menu.add(jmiFormat);              
        
        // Set action listener
        WordWrap.addActionListener(this);
        Font.addActionListener(this);
        
        // Help view
        JMenu jmView = new JMenu("View");
        JMenuItem jmiStatusBar = new JMenuItem("Status Bar");
        jmView.add(jmiStatusBar);        
        menu.add(jmView);  
        jmView.setMnemonic(KeyEvent.VK_V);
        jmiStatusBar.setMnemonic(KeyEvent.VK_S); 
        
        // Help menu
        JMenu jmHelp = new JMenu("Help");
        JMenuItem jmiHelp = new JMenuItem("View Help");
        JMenuItem jmiAbout = new JMenuItem("About JNotepad");        
        jmHelp.add(jmiHelp); 
        jmHelp.add(new JSeparator());
        jmHelp.add(jmiAbout);               
        jmiAbout.addActionListener(this);        
        menu.add(jmHelp);        
        jmHelp.setMnemonic(KeyEvent.VK_H); 
        jmiHelp.setMnemonic(KeyEvent.VK_H); 
        
        // Disabled menu items        
        jmiPageSetup.setEnabled(false);
        jmiPrint.setEnabled(false);
        jmiUndo.setEnabled(false);
        jmiReplace.setEnabled(false);
        jmiGoTo.setEnabled(false);
        jmiSelectAll.setEnabled(false);
        jmiStatusBar.setEnabled(false);
        jmiHelp.setEnabled(false);
        
        // Handle closing frame
        jfrm.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               saveOnExit();
            }
        });
        
        // Add menu to frame
        jfrm.setJMenuBar(menu);
   }   
        
        public void saveOnExit() {
            if (myFile==null && !jta.getText().equals("")) {
                int result = JOptionPane.showConfirmDialog(jfrm, "Do you want to save changes to JNotepad?" );
                if (result == 0) {
                    saveJFileChooser();
                    jfrm.dispose();
                }    
                else if (result == 1) jfrm.dispose();
            }
            else jfrm.dispose();    
        }
  
	public void actionPerformed(ActionEvent ae) {
            switch(ae.getActionCommand()) {
                case "New": new JNotePad(); break;
                case "Open...": openJFileChooser(); break;
                case "Exit": saveOnExit(); break;
                case "About JNotepad": JOptionPane.showMessageDialog(jfrm, "(c) Lam Nguyen"); break;
                case "Save":    if (myFile==null) saveJFileChooser();
                                else saveFileOverwrite();
                                break;
                case "Save As...": saveJFileChooser(); break;
                case "Cut": jta.cut(); break;
		case "Copy": jta.copy(); break;
                case "Paste": jta.paste(); break;
                case "Delete": jta.replaceRange("", jta.getSelectionStart(), jta.getSelectionEnd()); break;
                case "Find...": findText(); break;
                case "Find Next": findString(stringToSearch); break;
                case "Time/Date":   DateFormat dateFormat = new SimpleDateFormat("h:mm a MM/dd/yyyy");
                                    Date date = new Date();
                                    String currentText = jta.getText();
                                    jta.setText(currentText + dateFormat.format(date));	
                                    break;
		
                case "Word Wrap":   if (!jta.getLineWrap())
                                            jta.setLineWrap(true);
                                    else jta.setLineWrap(false);
                                    break;
		
                case "Font...": myFont.jdlg.setVisible(true); break;
		}  
	}
   
	public void findText() {
	   JPanel pan=new JPanel();
	   pan.setLayout(new FlowLayout());
	   JDialog dialog = new JDialog(jfrm, "Find...");    	    
	   JButton findBtn = new JButton("Find Next");
	   final JTextField searchText = new JTextField();
	   pan.add(findBtn);
	   pan.add(searchText).setPreferredSize(new Dimension(100, 30));
	   dialog.add(pan);
	   dialog.setBounds(200, 200, 300, 100);
	   dialog.setVisible(true);
	   searchText.requestFocusInWindow();
	   JRootPane rootPane = SwingUtilities.getRootPane(findBtn); 
	   rootPane.setDefaultButton(findBtn);
	   findBtn.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   stringToSearch = searchText.getText().toLowerCase();
			   findString(stringToSearch);	
		   }
	   });	
	}
   
   // Find string in JTextArea
   public void findString(String target) {	   
	   jta.requestFocusInWindow();
	   if (target != null && target.length() > 0) {
           Document document = jta.getDocument();
           int findLength = target.length();
           try {
               boolean found = false;
               if (searchTextPosition + findLength > document.getLength()) {
                   searchTextPosition = 0;
               }
               while (searchTextPosition + findLength <= document.getLength()) {
                   String match = document.getText(searchTextPosition, findLength).toLowerCase();
                   if (match.equals(target)) {
                       found = true;
                       break;
                   }
                   searchTextPosition++;
               }
               if (found) {
//                   Rectangle viewRect = jta.modelToView(searchTextPosition);
                   jta.setCaretPosition(searchTextPosition + findLength);
                   jta.moveCaretPosition(searchTextPosition);
                   searchTextPosition += findLength;
               }
           } catch (Exception exp) {}
       }
   }
   
   public void openJFileChooser() {
      try {
         JFileChooser openChooser = new JFileChooser();
         openChooser.setFileFilter(new Filter());
         int userSelection = openChooser.showOpenDialog(null);
         if(userSelection != JFileChooser.APPROVE_OPTION) {
            jta.setText("");
            JOptionPane.showMessageDialog(jfrm, "No file selected");
         }
         else {
            File openedFile = openChooser.getSelectedFile();
            Scanner scan = new Scanner(openedFile);
            String content = "";
            while(scan.hasNext()) {
               content += scan.nextLine() + "\n";            
            }
            jta.setText(content);
            String title = openChooser.getName(openedFile);
            jfrm.setTitle(title);
            myFile = openedFile;
         }
      }
      catch(IOException e) {
         JOptionPane.showMessageDialog(jfrm, e);   
      }
   }
   
   public void saveJFileChooser() {
	   JFileChooser saveChooser = new JFileChooser();
	   saveChooser.setFileFilter(new Filter());
	   int userSelection = saveChooser.showSaveDialog(jfrm);
	    
	   if (userSelection == JFileChooser.APPROVE_OPTION) {
	       File fileToSave = saveChooser.getSelectedFile();
	       System.out.println("Save as file: " + fileToSave.getAbsolutePath());
	       try {
	    	   PrintWriter out = new PrintWriter(new FileWriter(fileToSave+".txt"));
                   Scanner scan = new Scanner(jta.getText());
                   while (scan.hasNext()) {
                        String str = scan.nextLine();
                        out.println(str);
                   }
                   out.close();
	           myFile = fileToSave;
	           jfrm.setTitle(getOpenedFileName());	           
	       }      
	       catch(Exception e) {
	           JOptionPane.showMessageDialog(jfrm, "Cannot save file...");
	           jfrm.setTitle("JNotepad");
	       }
	   }
   }
   
   public void saveFileOverwrite() {
       try {
    	   PrintWriter out = new PrintWriter(new FileWriter(myFile+".txt"));
           Scanner scan = new Scanner(jta.getText());
           while (scan.hasNext()) {
               String str = scan.nextLine();
               out.println(str);
           }
           out.close();
       }      
       catch(Exception e) {
           JOptionPane.showMessageDialog(jfrm, "Cannot save file...");
           jfrm.setTitle("JNotepad");
       }
   }
   
   class PopupListener extends MouseAdapter {
	    public void mousePressed(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        maybeShowPopup(e);
	    }

	    private void maybeShowPopup(MouseEvent e) {
	        if (e.isPopupTrigger()) {
	        	jpopup.show(e.getComponent(), e.getX(), e.getY());
	        }
	    }
	}
  
	class Filter extends FileFilter {
		public boolean accept(File file) {
			if(file.getName().endsWith("txt"))
				return true;
			else return false;
		}      
		public String getDescription() {
			return ".txt";
		}      
	}
	
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
	        jdlg.setSize(750, 350);
	        jdlg.setResizable(false);
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
   
	public static void main(String [] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				new JNotePad();
			}
		});
	}
}
