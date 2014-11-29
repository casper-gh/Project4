//
//Name:    Nguyen, Lam
//Project: #4
//Due:     
//Course:  CS-245-01-w14
//
//Description:
//    
//

import javax.swing.*;
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
        
        // Add menu to frame
        jfrm.setJMenuBar(menu);
   }   
  
	public void actionPerformed(ActionEvent ae) {
		// File menu
		if(ae.getActionCommand().equals("New")) new JNotePad();
		else if(ae.getActionCommand().equals("Open...")) openJFileChooser();
		else if(ae.getActionCommand().equals("Exit")) System.exit(0); 
		else if(ae.getActionCommand().equals("About JNotepad")) {
			JOptionPane.showMessageDialog(jfrm, "(c) Lam Nguyen");
		}
		else if (ae.getActionCommand().equals("Save")) {
			if (myFile==null) saveJFileChooser();
			else saveFileOverwrite();
		}
		else if (ae.getActionCommand().equals("Save As...")) {
			saveJFileChooser();
		}
      
		// Edit menu
		if (ae.getActionCommand().equals("Cut") || ae.getActionCommand().equals("Cut-")) {
			String selection = jta.getSelectedText();
			StringSelection clipString = new StringSelection(selection);
			clipboard.setContents(clipString, clipString);
			jta.replaceRange("", jta.getSelectionStart(), jta.getSelectionEnd());
		}
		else if (ae.getActionCommand().equals("Copy")) {
			String selection = jta.getSelectedText();
			StringSelection clipString = new StringSelection(selection);
			clipboard.setContents(clipString, clipString);
		}
		else if (ae.getActionCommand().equals("Paste")) {
			Transferable clipData = clipboard.getContents(this);
			try {
				String clipString =
						(String)clipData.getTransferData(DataFlavor.stringFlavor);
				jta.replaceRange(clipString, jta.getSelectionStart(), jta.getSelectionEnd());
			}
			catch(Exception ex){}
		}
		else if (ae.getActionCommand().equals("Delete")) {
				jta.replaceRange("", jta.getSelectionStart(),
						jta.getSelectionEnd());
		}
		else if (ae.getActionCommand().equals("Find...")) {	
			findText();
		}  
		else if (ae.getActionCommand().equals("Find Next")) {			
			findString(stringToSearch);			
		}  
		else if (ae.getActionCommand().equals("Time/Date")) {		
			DateFormat dateFormat = new SimpleDateFormat("h:mm a MM/dd/yyyy");
			Date date = new Date();
			String currentText = jta.getText();
			jta.setText(currentText + dateFormat.format(date));	
		}  
		else if (ae.getActionCommand().equals("Word Wrap")) {
			if (!jta.getLineWrap())
				jta.setLineWrap(true);
			else jta.setLineWrap(false);
		}
		else if (ae.getActionCommand().equals("Font...")) {			
			myFont.jdlg.setVisible(true);	
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
                   Rectangle viewRect = jta.modelToView(searchTextPosition);
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
	    	   BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave+".txt"));	        
	           out.write(jta.getText());	
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
    	   BufferedWriter out = new BufferedWriter(new FileWriter(myFile));
           out.write(jta.getText());
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
   
	public static void main(String [] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				new JNotePad();
			}
		});
	}
}
