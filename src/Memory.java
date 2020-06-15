
public class Memory {
	int[] ram = new int[256];
    int wRegister = 0;
    int[] stack = new int[8];
    int stackpointer = 0;
    int[] progcount = new int[1024];

    Controller Ctr;

    public Memory(Controller ctr) {
    	this.Ctr = ctr;
        wRegister = 0;
        for (int i = 0; i < 256; i++) {
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

    public int[] getRam() {
        return ram;
    }

    public void setRam(int[] ram) {
        this.ram = ram;
		Ctr.Gui.ramtable.repaint();
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
        return progcount;
    }
    
    public void setProgcount(int[] progcount) {
        this.progcount = progcount;
    }
}
