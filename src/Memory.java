
public class Memory extends Thread {
	public static final int RAMLENGTH = 256;
	private int[] ram = new int[RAMLENGTH];
	private int wRegister = 0;
	private int[] stack = new int[8];
	private int stackpointer = 0;
	private int[] progmem = new int[1024];
	private String filename = " ";
	private int aktuellerPC;
	private int[] PCtoRow = new int[1024];
	private int prescalevar = 0;


	private Controller Ctr;

    public Memory(Controller ctr) {
    	this.Ctr = ctr;
        this.reset();
    }
    
    public void run() {
    	while(true) {
			Ctr.updateSFR();
			Ctr.updateStack();
			for(int i = 0; i < RAMLENGTH; i++) {
			
				Ctr.updateRamGui(i%8, i/8, this.ram[i]);
			}
			
			
			Ctr.markrow(aktuellerPC);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    
    public int[] getganzenStack() {
    	return stack;
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
		this.ram[0x2] = aktuellerPC & 0xFF;
	}
	
	public int getCurrentCommand(int pc) {
		return this.progmem[pc];
	}
	public void incPc() {
		this.aktuellerPC++;
		this.ram[0x2] = aktuellerPC & 0xFF;
	}
	
	public int[] getPCtoRow() {
		return PCtoRow;
	}

	public void setPCtoRow(int[] pCtoRow) {
		PCtoRow = pCtoRow;
	}
	
	public void reset() {
		Ctr.demarkrow();
		this.wRegister = 0;
		this.aktuellerPC = 0;
		this.stackpointer = 0;
		for (int i = 0; i < 8; i++) {
			stack[i] = 0;
		}
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
	
	public void resetPCtoRow() {
		for (int i = 0; i < 1024; i++) {
			PCtoRow[i] = 0;
		}
	}

	public int getTmr0() {
		return ram[0x1];
	}

	public void setTmr0(int tmr0) {
		this.ram[0x1] = tmr0;
	}
	public void incTmr0() {
		
		
		int tmr = this.ram[0x1];
		if (tmr == 255) {
			this.ram[0x1] = 0;
			this.ram[0xB] = this.ram[0xB] | 0x4;
			this.ram[0x3] = this.ram[0x3] | 0x4;
			this.prescalevar = 0;
		}
		else {
		this.ram[0x1]++;
		}
	}
	public void prescaler() {
		int option = this.ram[0x81];
		int psa = option & 0x8;
		int ps2 = option & 0x7;
		this.prescalevar++;
		
		if(psa == 0) {
			switch (ps2) {
			case 0x0:
				if (this.prescalevar%2 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x1:
				if (this.prescalevar%4 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x2:
				if (this.prescalevar%8 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x3:
				if (this.prescalevar%16 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x4:
				if (this.prescalevar%32 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x5:
				if (this.prescalevar%64 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x6:
				if (this.prescalevar%128 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			case 0x7:
				if (this.prescalevar%256 ==0) {
					this.incTmr0();
					this.prescalevar = 0;				
				}
				break;
			
			}
			
		}
		else {
			this.incTmr0();
		}
		

			
		
	}
	
}
