
public class Memory extends Thread {
	public static final int RAMLENGTH = 256;
	private int[] ram = new int[RAMLENGTH];
	private int wRegister = 0;
	private int[] stack = new int[8];
	private int stackpointer = 0;
	private int[] progmem = new int[1024];
	private String filename = "";
	private int aktuellerPC;

	private Controller Ctr;

    public Memory(Controller ctr) {
    	this.Ctr = ctr;
        wRegister = 0;
        for (int i = 0; i < RAMLENGTH; i++) {
            if (i == 0x3) {
                ram[i] = 0x18;
            } else if (i == 0x81) {
                ram[i] = 0xFF;
            } else if (i == 0x83) {
                ram[i] = 0x18;
            } else if (i == 0x85) {
                ram[i] = 0x1F;
            } else if (i == 0x86) {
                ram[i] = 0xFF;
            }
            else {
                ram[i] = 0;
            }
        }
    }
    public void run() {
    	for(int i = 0; i < RAMLENGTH; i++) {
    		
    		Ctr.updateRamGui(i%8, i/8, this.ram[i]);
    	}
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public int[] getRam() {
        return ram;
    }

    public void setRam(int[] ram) {
        this.ram = ram;
    }

    public int getwRegister(){
        return wRegister;
    }

    public void setwRegister(int wRegister) {
        this.wRegister = wRegister;
    }

    public int getStack(){
        this.stackpointer--;
        this.stackpointer = checkStackpointer(stackpointer);
        int aktuellerStack = stack[stackpointer];
        this.stack[stackpointer] = 0x0;
        return aktuellerStack;
    }

    public void setStack(int aktuelleaddresse){
        this.stack[stackpointer] = aktuelleaddresse;
        this.stackpointer++;
        this.stackpointer = checkStackpointer(stackpointer);
    }

    public int checkStackpointer(int aktuellerstackpointer) {
        if (aktuellerstackpointer > 7) {
            aktuellerstackpointer -= 8;
        } else if (aktuellerstackpointer < 0) {
            aktuellerstackpointer += 8;
        }
        return aktuellerstackpointer;
    }
    
    public int[] getProgcount() {
        return progmem;
    }
    
    public void setProgcount(int[] progcount) {
        this.progmem = progcount;
    }
    
    public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public int getAktuellerPC() {
		return aktuellerPC;
	}

	public void setPC(int PC) {
		this.aktuellerPC = PC;
	}
	public int getCurrentCommand(int pc) {
		return this.progmem[pc];
	}
	public void incPc() {
		this.aktuellerPC++;
	}
}
