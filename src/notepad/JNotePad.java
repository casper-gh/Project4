package notepad;

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

import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.io.File.*;
import java.awt.event.*;

public class JNotePad implements ActionListener
{
   //Create instance of frame, area, and file
   JFrame jfrm;
   JTextArea jta;
   File myFile;
   String openedFileName;
  
   //-------------------------------------------------------------------------- 
   JNotePad()
   {
      //Set name of frame
      jfrm = new JFrame(getOpenedFileName());
  
      //Set frame size and layout
      jfrm.setSize(900, 600);
      jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

      jfrm.setLayout(new GridLayout(1, 1));
     
      //Set close operation for frame
      jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
      //Set frame to visisble
      jfrm.setVisible(true);
     
      //Set area
      setTextArea();
      setMenuBar();
   }
   //--------------------------------------------------------------------------
   /*Returns the name of the file so it can be placed as title of frame.
    if the file name is null then set the name of the frame to "Untitled else
    set the name of the frame to the name of the file.*/
   public String getOpenedFileName() throws NullPointerException {
      
      try {
         openedFileName = myFile.getName();
      }
      catch(NullPointerException e) {
         openedFileName = "JNotepad";
      }
      return openedFileName;
   }
   //--------------------------------------------------------------------------
   /*Add the text area to the frame. And add scroll bar*/
   public void setTextArea()
   {
      jta = new JTextArea();
      JScrollPane jsp = new JScrollPane(jta);
      jfrm.add(jsp);
   }
   //--------------------------------------------------------------------------
   /*Sets the menu bar for window. Also adds mneumonics for keboard short cuts.
    *Likewise, the items in drop down menu have mneumonics.
    */
   public void setMenuBar()
   {
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
        jmiExit.setMnemonic(KeyEvent.VK_E);
        
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
        
        //Set accelerator 
        jmiEdit.setMnemonic(KeyEvent.VK_E); 
        jmiCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
        jmiCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
        jmiPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));       
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
        
        // -- Format menu --
        JMenu jmiFormat = new JMenu("Format");
        JMenuItem WordWrap = new JMenuItem("Word Wrap");
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
        
        // -- Help view --
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
        
        // Add menu to frame
        jfrm.setJMenuBar(menu);
   }   
  
   /*Action is triggered by selecting an item in the menu bar drop down list.*/
   public void actionPerformed(ActionEvent ae) 
   {
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
   }
   
   /*Opens a selected file and rename the frame to the corresponding file
    * name.
    */
   public void openJFileChooser() 
   {
      try {
         JFileChooser openChooser = new JFileChooser();
         openChooser.setFileFilter(new TextFileFilter());
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
	   saveChooser.setFileFilter(new TextFileFilter());
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
   
   public static void main(String [] args)
   {
      SwingUtilities.invokeLater(new Runnable() { 
         public void run() {
            new JNotePad();
         }
      });
   }
}