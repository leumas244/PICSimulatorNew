
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
	public Object[][] ramAktualisiern() {
		int[] ram = new int[256];
		Object[][] newram = new Object [32][8];
		
		ram = Mem.getRam();
		
		for(int i = 0; i<ram.length; i++) {
			int g=0;
			for(int h=0;;h++) {
					if(h==8) {h=0; g++;}
					newram[g][h]=ram[i];
					
				}
				
			}
		System.out.println(newram);
		
		 return newram;	
	}
	
	
	
}