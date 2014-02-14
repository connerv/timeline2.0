package newtimeline;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IO {
	
	public static Timeline getFile(){
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Timeline files", "timeline", "tl");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("file: " +
	            chooser.getSelectedFile().getName());
	    }
	    String filePath = chooser.getSelectedFile().getPath();
	    Timeline tl = null;
	    try{
	    	FileInputStream saveFile = new FileInputStream(filePath);
	    	ObjectInputStream save = new ObjectInputStream(saveFile);
	    	tl = (Timeline) save.readObject();
	    	
	    	save.close(); 
	    	}
	    	catch(Exception exc){
	    	exc.printStackTrace(); 
	    	}
	    
	    return tl;
	}
	
	public static void saveFile(Timeline tl, String saveName){
		try{  
			FileOutputStream saveFile=new FileOutputStream(saveName+".tl");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(tl);
			save.close(); 
			}
			catch(Exception exc){
			exc.printStackTrace(); 
			}
	}
}

