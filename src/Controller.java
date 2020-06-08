
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
	
}
