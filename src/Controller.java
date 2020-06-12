import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class Controller {
	public GUI Gui;
	public Memory Mem;
	public Comands Com;
	public Adresschecking Ac;
	public Masks Mk;
	
	public Controller(GUI gui) {
		this.Gui = gui;
		this.Mem = new Memory(this);
		this.Com = new Comands(this);
		this.Ac = new Adresschecking();
		this.Mk = new Masks(this);
	}
	
	public void startprogramm() {
		int[] programmSpeicher = Mem.getProgcount();
        for (int i = 0; i < 1024; i++) {
            i = Mk.vorsortieren(programmSpeicher[i], i);

        }
	}
	
	public void loadFile() {
		JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(Gui.frame);

        File file = fc.getSelectedFile();
        String filename = file.getAbsolutePath();
        Mem.setFilename(filename);
        
        //Gui.setlblUsedFileValue(filename);
        
        FileReader fileReader;
        BufferedReader bufferedReader;
		try {
			fileReader = new FileReader(filename);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
            while ((line = bufferedReader.readLine()) != null) {
	            String pCounter = line.substring(0, 4);
	            String pCode = line.substring(5, 9);
	            String row = line.substring(20, 25);
	            String label = " ";
            	String comand = " ";
            	String coment = " ";
            	String Test = line.substring(27, 28);
	            if (!(line.substring(27, 28).equals(" "))) {
	            	label = line.substring(27, 36);
	            	comand = " ";
	            	coment = " ";
	            } else {
	            	label = " ";
	            	if (line.length() >=37) {
	            		if (line.substring(36, 37).equals(";")) {
	            			comand = " ";
	            			coment = line.substring(36, line.length());
	            		} else {
	            			if (line.length() >= 56) {
	            			comand = line.substring(36, 56);
	            			coment = line.substring(56, line.length());
	            			} else {
	            				comand = line.substring(36, line.length());
		            			coment = " ";
	            			}
	            			
	            		}
	            	} else {
	            		comand = " ";
		            	coment = " ";
	            	}
	            }
	            
	            DefaultTableModel model = (DefaultTableModel)Gui.table.getModel();
	            model.addRow(new Object [] {pCounter, pCode, row, label, comand, coment});
	            }
            bufferedReader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
