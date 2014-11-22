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
   File file;
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
   public String getOpenedFileName() throws NullPointerException
   {
      
      try {
         openedFileName = file.getName();
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
        jmFile.add(jmiExit);
        menu.add(jmFile);
        
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
        
        // Help menu
        JMenu jmHelp = new JMenu("Help");
        JMenuItem jmiAbout = new JMenuItem("About");
        jmHelp.add(jmiAbout);        
        menu.add(jmHelp);
        
        
        
        jfrm.setJMenuBar(menu);
        //Set mnemonic for File, Edit, About
        jmFile.setMnemonic(KeyEvent.VK_F);
        jmHelp.setMnemonic(KeyEvent.VK_H);
        //Set accelerator for items in menu bar
        jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
        jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        jmiNew.addActionListener(this);
        jmiOpen.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiSaveAs.addActionListener(this);
        jmiAbout.addActionListener(this);
   }   
   //--------------------------------------------------------------------------
   /*Action is triggered by selecting an item in the menu bar drop down list.*/
   public void actionPerformed(ActionEvent ae) 
   {
      if(ae.getActionCommand().equals("New"))
      {
         new JNotePad();
      }
      else if(ae.getActionCommand().equals("Open..."))
      {
         openJFileChooser();
      }
      else if(ae.getActionCommand().equals("Close"))
      {
         System.exit(0);
      }
      else if(ae.getActionCommand().equals("About"))
      {
         JOptionPane.showMessageDialog(jfrm, "(c) Tello 2011");
      }
      else if (ae.getActionCommand().equals("Save"))
      {
         if (!openedFileName.equals("JNotepad")) {
        	 saveFile(openedFileName);
        	 System.out.println(openedFileName);
         }
         else {
             //Set a name to save file     
             String str = JOptionPane.showInputDialog(jfrm, "Save As..", "Save As..", JOptionPane.YES_NO_CANCEL_OPTION);
             jfrm.setTitle(str);
             saveFile(str);
         }
      }
      else if (ae.getActionCommand().equals("Save As..."))
      {
         //Set a name to save file     
//         String str = JOptionPane.showInputDialog(jfrm, "Save As..", "Save As..", JOptionPane.YES_NO_CANCEL_OPTION);
//         jfrm.setTitle(str);
//         saveFile(str);
    	  saveJFileChooser();
      }
   }
   //--------------------------------------------------------------------------
   /*Opens a selected file and rename the frame to the corresponding file
    * name.
    */
   public void openJFileChooser() 
   {
      try {
         JFileChooser chooser = new JFileChooser();
         chooser.setFileFilter(new TextFileFilter());
         int result = chooser.showOpenDialog(null);
         if(result != JFileChooser.APPROVE_OPTION) {
            jta.setText("");
            JOptionPane.showMessageDialog(jfrm, "No file selected");
         }
         else {
            File file = chooser.getSelectedFile();
            Scanner scan = new Scanner(file);
            String content = "";
            while(scan.hasNext())
            {
               content += scan.nextLine() + "\n";            
            }
            jta.setText(content);
            String title = chooser.getName(file);
            jfrm.setTitle(title);
         }
      }
      catch(IOException e)
      {
         JOptionPane.showMessageDialog(jfrm, e);   
      }
   }
   
   public void saveJFileChooser() {
	   JFileChooser fileChooser = new JFileChooser();
	   fileChooser.setDialogTitle("Specify a file to save");   
	    
	   int userSelection = fileChooser.showSaveDialog(jfrm);
	    
	   if (userSelection == JFileChooser.APPROVE_OPTION) {
	       File fileToSave = fileChooser.getSelectedFile();
	       System.out.println("Save as file: " + fileToSave.getAbsolutePath());
	       try {
	    	   BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave));
	           out.write(jta.getText());
	           out.close();
	       }      
	       catch(Exception e) {
	           JOptionPane.showMessageDialog(jfrm, "Cannot save file...");
	           jfrm.setTitle("JNotepad");
	       }
	   }
   }
   
   //--------------------------------------------------------------------------
   /*Writes file to and output file to save*/
   public void saveFile(String fname)
   {
      try
      {
         FileWriter writer = new FileWriter(fname + ".txt");
         BufferedWriter out = new BufferedWriter(writer);
         out.write(jta.getText());
         out.close();
      }
      catch(Exception e)
      {
         System.out.println(jta.getSelectedText());
         JOptionPane.showMessageDialog(jfrm, "Cannot save file...");
         jfrm.setTitle("JNotepad");
      }
   }
   //--------------------------------------------------------------------------
   public static void main(String [] args)
   {
      SwingUtilities.invokeLater(new Runnable() { 
         public void run() {
            new JNotePad();
         }
      });
   }
   //--------------------------------------------------------------------------
}
///////////////////////////////////////////////////////////////////////////////