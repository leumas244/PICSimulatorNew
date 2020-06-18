import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controller {
	private GUI gui;
	private Memory Mem;
	private Comands Com;
	private Adresschecking Ac;
	private Masks Mk;
	private Read Rd;
	private Processor prc;
	private boolean isRunning = false;
	
	public Controller(GUI gui) {
		this.gui = gui;
		this.Mem = new Memory(this);
		this.initializeRamtable();
		this.Mem.start();
		this.Com = new Comands(this);
		this.Ac = new Adresschecking();
		this.Mk = new Masks(this);
		this.Rd = new Read(this);
	}
	
	public void startprogramm() {
		int[] programmSpeicher = Mem.getProgcount();
        for (int i = 0; i < 1024; i++) {
        	Mem.setAktuellerPC(i);
        //	int row = getrow();


            
        }
	}

	
	public void loadFile() {
		JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(gui.frame);
 
        File file = fc.getSelectedFile();
        String filename = file.getAbsolutePath();
        Mem.setFilename(filename);
        
        //Gui.lblUsedFileValue.setText(filename);
        
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
	            
	            DefaultTableModel model = (DefaultTableModel)gui.table.getModel();
	            model.addRow(new Object [] {pCounter, pCode, row, label, comand, coment});
	            }
            bufferedReader.close();
            
            Rd.readLines(filename);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.gui.setFileValue(filename);
	}
	
	public int getrow() {
		int h = gui.table.getRowCount();
		int testint = 1234;
		int PC = Mem.getAktuellerPC();
		for (int i=0; i<h; i++) {
			String test = (String) gui.table.getValueAt(i,0);
			if (!(test.equals("    "))) {
				testint = Integer.parseInt(test);
				if (PC == testint) {
					return i;
				}
			}
		}
		return 0;
		
	}
	public void updateRamGui(int x, int y, int value) {
		gui.updateRamtable(x, y, value);
	}
	private void initializeRamtable() {
		for(int i=0;i<32;i++) {
			gui.addRowToRam(new Object[] {Integer.toHexString(i*8), "00", "00", "00", "00", "00", "00", "00", "00"});
		}
	}
	public void startProcessor() {
		if (isRunning==false) {
			prc = new Processor(this);
			prc.start();
		}
	
	}
	public void stopProcessor() {
		if(isRunning) {
			prc.exit=true;
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Memory getMem() {
		return Mem;
	}

	public Comands getCom() {
		return Com;
	}

	public Adresschecking getAc() {
		return Ac;
	}

	public Masks getMk() {
		return Mk;
	}
}