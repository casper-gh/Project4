package notepad;
//
// Description: A simple notepad program.
//
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.io.File.*;
import java.awt.event.*;
///////////////////////////////////////////////////////////////////////////////
public class Editor implements ActionListener
{
   //Create instance of frame, area, and file
   private JFrame frm;
   private JTextArea jarea;
   private File file;
  
   //-------------------------------------------------------------------------- 
   Editor()
   {
      //Set name of frame
      frm = new JFrame(getFileName());
  
      //Set frame size and layout
      frm.setSize(900, 600);

      frm.setLayout(new GridLayout(1, 1));
     
      //Set close operation for frame
      frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
      //Set frame to visisble
      frm.setVisible(true);
     
      //Set area
      setTextArea();
      setMenuBar();
   }
   //--------------------------------------------------------------------------
   /*Returns the name of the file so it can be placed as title of frame.
    if the file name is null then set the name of the frame to "Untitled else
    set the name of the frame to the name of the file.*/
   public String getFileName() throws NullPointerException
   {
      String fileName;
      try
      {
         fileName = file.getName();
      }
      catch(NullPointerException e)
      {
         fileName = "Untitled";
      }
      return fileName;
   }
   //--------------------------------------------------------------------------
   /*Add the text area to the frame. And add scroll bar*/
   public void setTextArea()
   {
      jarea = new JTextArea();
      JScrollPane jsp = new JScrollPane(jarea);
      frm.add(jsp);
   }
   //--------------------------------------------------------------------------
   /*Sets the menu bar for window. Also adds mneumonics for keboard short cuts.
    *Likewise, the items in drop down menu have mneumonics.
    */
   public void setMenuBar()
   {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        file.add(new JSeparator());
        JMenu help = new JMenu("Help");
        JMenuItem newSubMenu = new JMenuItem("New");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem close = new JMenuItem("Close");
        close.add(new JSeparator());
        JMenuItem about = new JMenuItem("About");
        file.add(newSubMenu);
        file.add(open);
        file.add(save);
        file.add(close);
        close.add(new JSeparator());
        help.add(about);
        menu.add(file);
        menu.add(help);
        frm.setJMenuBar(menu);
        //Set mnemonic for File, Edit, About
        file.setMnemonic(KeyEvent.VK_F);
        help.setMnemonic(KeyEvent.VK_H);
        //Set accelerator for items in menu bar
        newSubMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        newSubMenu.addActionListener(this);
        open.addActionListener(this);
        close.addActionListener(this);
        save.addActionListener(this);
        about.addActionListener(this);
   }   
   //--------------------------------------------------------------------------
   /*Action is triggered by selecting an item in the menu bar drop down list.*/
   public void actionPerformed(ActionEvent ae) 
   {
      if(ae.getActionCommand().equals("New"))
      {
         new Editor();
      }
      else if(ae.getActionCommand().equals("Open"))
      {
         openJFileChooser();
      }
      else if(ae.getActionCommand().equals("Close"))
      {
         System.exit(0);
      }
      else if(ae.getActionCommand().equals("About"))
      {
         JOptionPane.showMessageDialog(frm, "(c) Tello 2011");
      }
      else
      {
         //Set a name to save file     
         String str = JOptionPane.showInputDialog(frm, "Save As..", "Save As..", JOptionPane.YES_NO_CANCEL_OPTION);
         frm.setTitle(str);
         saveFile(str);
      }
   }
   //--------------------------------------------------------------------------
   /*Opens a selected file and rename the frame to the corresponding file
    * name.
    */
   public void openJFileChooser() 
   {
      try
      {
         JFileChooser chooser = new JFileChooser();
         chooser.setFileFilter(new TextFileFilter());
         int result = chooser.showOpenDialog(null);
         if(result != JFileChooser.APPROVE_OPTION)
         {
            jarea.setText("");
            JOptionPane.showMessageDialog(frm, "No file selected");
         }
         else
         {
            File file = chooser.getSelectedFile();
            Scanner scan = new Scanner(file);
            String content = "";
            while(scan.hasNext())
            {
               content += scan.nextLine() + "\n";            
            }
            jarea.setText(content);
            String title = chooser.getName(file);
            frm.setTitle(title);
         }
      }
      catch(IOException e)
      {
         JOptionPane.showMessageDialog(frm, e);   
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
         out.write(jarea.getText());
         out.close();
      }
      catch(Exception e)
      {
         System.out.println(jarea.getSelectedText());
         JOptionPane.showMessageDialog(frm, "Cannot save file...");
         frm.setTitle("Untitled");
      }
   }
   //--------------------------------------------------------------------------
   public static void main(String [] args)
   {
      SwingUtilities.invokeLater(new Runnable() { 
         public void run() {
            new Editor();
         }
      });
   }
   //--------------------------------------------------------------------------
}
///////////////////////////////////////////////////////////////////////////////