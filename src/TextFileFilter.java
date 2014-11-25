
import javax.swing.filechooser.FileFilter;
import java.io.*;

public class TextFileFilter extends FileFilter
{
   public boolean accept(File file) {
        if(file.getName().endsWith("txt"))
            return true;
        else
            return false;
    }
    
    public String getDescription() {
        return ".txt";
    }
   
}
