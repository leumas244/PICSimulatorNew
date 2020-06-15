
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
	public Object[][] ramUmwandeln() {
		int [] ram = Mem.getRam();
		Object[][] newram = new Object [32][8];
		int h = 0;
		int g = 0;
		
		for(int i = 0; i<255; i++) {
			if (h==8) {
				h = 0;
				g++;
			}
			newram[g][h]=ram[i];
			h++;
			}



			
		 return newram;	
	}
	public void ramaktualisieren() {
		Gui.ramtable.repaint();
	}
	
	
	
}