
public class Comands {
	    Controller Ctr;

	    public Comands(Controller ctr) {
	    	this.Ctr = ctr;
	    }

	    public void nop() {
	        System.out.println("No Operation");
	        printCDCZ();
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void clrwdt(int befehl) {
	        System.out.println("START - Clear WatchdogTimer");
	        Ctr.getMem().setWatchdog(0);
	        int[] ram = Ctr.getMem().getRam();
	        ram[0x83] = ram[0x83] | 0x18;
	        ram[0x81] = ram[0x83] & 0xF8;
	        printCDCZ();
	        System.out.println("FINISH - Clear WatchdogTimer");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void retfie() {
	        System.out.println("START - Return from Interrupt");
	        int pc = Ctr.getMem().getStack();

	        int[] RAM = Ctr.getMem().getRam();
	        int intconReg = RAM[0xB];
	        intconReg |= 0x80;

	        RAM[0xB] = intconReg;
	        Ctr.getMem().setRam(RAM);
	        printCDCZ();
	        System.out.println("FINISH - Return from Interrupt");
	        Ctr.getMem().setPC(pc);
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	        Ctr.incLaufzeit();
	    }

	    public void returnbef() {
	        System.out.println("START - Retrun from Subroutine");
	        int pc = Ctr.getMem().getStack();

	        printCDCZ();
	        System.out.println("FINISH - Retrun from Subroutine");
	        Ctr.getMem().setPC(pc);
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	        Ctr.incLaufzeit();
	    }

	    public void sleep() {
	        System.out.println("START - Go into standbymode");
	        printCDCZ();
	        System.out.println("FINISH - Go into standbymode");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void addwf(int befehl) {
	        System.out.println("START - Add w to f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        // setzen des DC
	        int DChilfeW = wRegister & 0xF;
	        int DChilfeK = fRegister & 0xF;

	        if ((DChilfeK + DChilfeW) > 0xF) {
	            hRegister |= 0x2;

	        } else {
	            if ((hRegister & 0x2) == 0x2) {
	                hRegister &= 0xFD;
	            }
	        }

	        // hier wird die normale Rechnung ausgeführt
	        int ergebniss = fRegister + wRegister;

	        // ergenis auf postiv, null oder negativ prüfen wegen Carry und Zero Setzung
	        if (ergebniss < 0xFF) {
	            // setze Carry Flag auf 0
	            if ((hRegister & 0x1) == 0x1) {
	                hRegister &= 0xFE;
	            }
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        } else if (ergebniss == 0x100) {
	            ergebniss = 0;
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;

	            // setze Zero Flag auf 1
	            hRegister |= 0x4;
	        } else {
	            ergebniss -= 0x100;
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        }
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Add w to f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void andwf(int befehl) {
	        System.out.println("START - And w with f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister & wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - And w with f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void comf(int befehl) {
	        System.out.println("START - Compliment f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebnis = fRegister ^ 0xFF;

	        hRegister = checkZflag(ergebnis, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebnis;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebnis;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Compliment f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void decf(int befehl) {
	        System.out.println("START - Decrement f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister - 1;

	        if (ergebniss < 0x0) {
	            ergebniss = 0x100 + ergebniss;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Decrement f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void decfsz(int befehl) {
	        System.out.println("START - Decrement f, Skip if 0");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister - 1;

	        if (ergebniss < 0x0) {
	            ergebniss = 0x100 + ergebniss;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (ergebniss == 0x0) {
	        	Ctr.getMem().incPc();
	        	Ctr.incLaufzeit();
	        }

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Decrement f, Skip if 0");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void incf(int befehl) {
	        System.out.println("START - Increment f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister + 1;

	        if (ergebniss == 0x100) {
	            ergebniss = 0x0;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Increment f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void incfsz(int befehl) {
	        System.out.println("START - Increment f, Skip if 0");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister + 1;

	        if (ergebniss == 0x100) {
	            ergebniss = 0x0;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (ergebniss == 0x0) {
	        	Ctr.getMem().incPc();
	        	Ctr.incLaufzeit();
	        }

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Increment f, Skip if 0");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void iorwf(int befehl) {
	        System.out.println("START - Inclusive Or w with f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister | wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Inclusive Or w with f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void movf(int befehl) {
	        System.out.println("START - Move f to destiantion w or f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        hRegister = checkZflag(fRegister, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = fRegister;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = fRegister;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Move f to destiantion w or f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void rlf(int befehl) {
	        System.out.println("START - Rotate Left f through Carry");

	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int C = RAM[3] & 0x1;

	        int CNeu = 0x80 & fRegister;
	        if (CNeu == 0x80) {
	            CNeu = 0x1;
	        }

	        int fRegisterNeu;
	        fRegisterNeu = (fRegister << 1) + C;
	        fRegisterNeu &= 0xFF;

	        if (dest == 1) {
	            RAM[fAddr] = fRegisterNeu;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	            Ctr.getMem().setRam(RAM);
	        } else if (dest == 0) {
	            Ctr.getMem().setwRegister(fRegisterNeu);
	        }

	        RAM[3] = (RAM[3] & 0xFE) + CNeu;

	        Ctr.getMem().setRam(RAM);

	        System.out.println(RAM[fAddr]);
	        System.out.println(CNeu);
	        printCDCZ();
	        System.out.println("FINISH - Rotate Left f through Carry");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void rrf(int befehl) {
	        System.out.println("START - Rotate Right f through Carry");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int C = RAM[3] & 0x1;
	        if (C == 0x1) {
	            C = 0x80;
	        }

	        int CNeu = 0x1 & fRegister;


	        int fRegisterNeu;
	        fRegisterNeu = (fRegister >> 1) + C;
	        fRegisterNeu &= 0xFF;

	        if (dest == 1) {
	            RAM[fAddr] = fRegisterNeu;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	            Ctr.getMem().setRam(RAM);
	        } else if (dest == 0) {
	            Ctr.getMem().setwRegister(fRegisterNeu);
	        }

	        RAM[3] = (RAM[3] & 0xFE) + CNeu;

	        Ctr.getMem().setRam(RAM);

	        System.out.println(RAM[fAddr]);
	        System.out.println(CNeu);
	        printCDCZ();
	        System.out.println("FINISH - Rotate Right f through Carry");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void subwf(int befehl) {
	        System.out.println("START - Subtract W from f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int hRegister = RAM[3];
	        int wRegister = Ctr.getMem().getwRegister();

	        // setzen des DC
	        int DChilfeW = wRegister & 0xF;
	        int DChilfeK = fRegister & 0xF;

	        if (DChilfeK >= DChilfeW) {
	            hRegister |= 0x2;
	        } else {
	            if ((hRegister & 0x2) == 0x2) {
	                hRegister &= 0xFD;
	            }
	        }

	        int ergebnis = fRegister - wRegister;

	        // ergenis auf postiv, null oder negativ prüfen
	        if (ergebnis > 0x0) {
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        } else if (ergebnis == 0x0) {
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 1
	            hRegister |= 0x4;
	        } else {
	            ergebnis = 0x100 + ergebnis;
	            // setze Carry Flag auf 0
	            if ((hRegister & 0x1) == 0x1) {
	                hRegister &= 0xFE;
	            }
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        }
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebnis;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebnis;
	        }

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Subtract W from f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void swapf(int befehl) {
	        System.out.println("START - Swap nibbles in f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();

	        int rechteSeite = fRegister & 0xF;
	        int linkeSeite = fRegister & 0xF0;

	        int neulinkeSeite = rechteSeite << 4;
	        int neurechteSeite = linkeSeite >> 4;
	        int ergebniss = neulinkeSeite + neurechteSeite;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Swap nibbles in f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void xorwf(int befehl) {
	        System.out.println("START - Exclusive OR W with f");

	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int dest = Ctr.getAc().byteOrientedDesination(lAcht);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister ^ wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	            if (fAddr == 0x1) {
	            	Ctr.getMem().setprescalevar(0);
	            }
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Exclusive OR W with f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void andlw(int befehl) {
	        System.out.println("START - Add literal and W");
	        int k = Ctr.getAc().byteDefault(befehl);
	        int[] RAM = Ctr.getMem().getRam();
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        wRegister &= k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Add literal and W");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void iorlw(int befehl) {
	        System.out.println("START - Inclusive OR literal with W");

	        int k = Ctr.getAc().byteDefault(befehl);
	        int wRegister = Ctr.getMem().getwRegister();
	        int[] RAM = Ctr.getMem().getRam();
	        int hRegister = RAM[3];

	        wRegister = wRegister | k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Inclusive OR literal with W");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void xorlw(int befehl) {
	        System.out.println("START - Exclusive OR literal with W");

	        int k = Ctr.getAc().byteDefault(befehl);
	        int wRegister = Ctr.getMem().getwRegister();
	        int[] RAM = Ctr.getMem().getRam();
	        int hRegister = RAM[3];

	        wRegister = wRegister ^ k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Exclusive OR literal with W");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void clrf(int befehl) {
	        System.out.println("START - Clear f");
	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int Z = RAM[3] & 0x4;
	        RAM[fAddr] = 0;
            if (fAddr == 0x1) {
            	RAM[0x81] = RAM[0x81] | 0xf;
            }

	        if (Z == 0x0) {
	            RAM[3] += 0x4;
	        }

	        Ctr.getMem().setRam(RAM);
	        printCDCZ();

	        System.out.println("FINISH - Clear f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void clrw() {
	        System.out.println("START - Clear w");
	        int[] RAM = Ctr.getMem().getRam();
	        int Z = RAM[3] & 0x4;

	        int wRegister = 0;

	        if (Z == 0x0) {
	            RAM[3] += 0x4;
	        }

	        Ctr.getMem().setwRegister(wRegister);
	        Ctr.getMem().setRam(RAM);
	        printCDCZ();

	        System.out.println("FINISH - Clear w");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void movwf(int befehl) {
	        System.out.println("START - Move W to f");

	        int lAcht = Ctr.getAc().byteDefault(befehl);
	        int fAddr = Ctr.getAc().getfAddress(lAcht);

	        int[] RAM = Ctr.getMem().getRam();
	        int wRegister = Ctr.getMem().getwRegister();
	        if (fAddr == 0x1) {
            	Ctr.getMem().setprescalevar(0);
            }

	        RAM[fAddr] = wRegister;

	        Ctr.getMem().setRam(RAM);
	        printCDCZ();
	        System.out.println("FINISH - Move W to f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void bcf(int befehl) {
	        System.out.println("START - Bit Clear f");
	        int lZehn = Ctr.getAc().bitOrientDefault(befehl);
	        int b = Ctr.getAc().bitConv(lZehn);
	        int fAddr = Ctr.getAc().getfAddressbit(lZehn);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];

	        switch (b) {
	            case 0:
	                fRegister &= 0xFE;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 1:
	                fRegister &= 0xFD;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 2:
	                fRegister &= 0xFB;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 3:
	                fRegister &= 0xF7;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 4:
	                fRegister &= 0xEF;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 5:
	                fRegister &= 0xDF;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 6:
	                fRegister &= 0xBF;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 7:
	                fRegister &= 0x7F;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	        }

	        System.out.println(fAddr);
	        System.out.println(RAM[fAddr]);
	        printCDCZ();
	        System.out.println("FINISH - Bit Clear f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void bsf(int befehl) {
	        System.out.println("START - Bit Set f");
	        int lZehn = Ctr.getAc().bitOrientDefault(befehl);
	        int b = Ctr.getAc().bitConv(lZehn);
	        int fAddr = Ctr.getAc().getfAddressbit(lZehn);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];

	        switch (b) {
	            case 0:
	                fRegister |= 0x1;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 1:
	                fRegister |= 0x2;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 2:
	                fRegister |= 0x4;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 3:
	                fRegister |= 0x8;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 4:
	                fRegister |= 0x10;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 5:
	                fRegister |= 0x20;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 6:
	                fRegister |= 0x40;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	            case 7:
	                fRegister |= 0x80;
	                RAM[fAddr] = fRegister;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                Ctr.getMem().setRam(RAM);
	                break;
	        }

	        System.out.println(fAddr);
	        System.out.println(RAM[fAddr]);
	        printCDCZ();
	        System.out.println("FINISH - Bit Set f");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void btfsc(int befehl) {
	        System.out.println("START - Bit Test f, Skip if Clear");
	        int lZehn = Ctr.getAc().bitOrientDefault(befehl);
	        int b = Ctr.getAc().bitConv(lZehn);
	        int fAddr = Ctr.getAc().getfAddressbit(lZehn);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int ergebnis;

	        switch (b) {
	            case 0:
	                ergebnis = fRegister & 0x1;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 1:
	                ergebnis = fRegister & 0x2;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 2:
	                ergebnis = fRegister & 0x4;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 3:
	                ergebnis = fRegister & 0x8;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 4:
	                ergebnis = fRegister & 0x10;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 5:
	                ergebnis = fRegister & 0x20;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 6:
	                ergebnis = fRegister & 0x40;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 7:
	                ergebnis = fRegister & 0x80;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x0) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	        }
	        printCDCZ();
	        System.out.println("FINISH - Bit Test f, Skip if Clear");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void btfss(int befehl) {
	        System.out.println("START - Bit Test f, Skip if Set");
	        int lZehn = Ctr.getAc().bitOrientDefault(befehl);
	        int b = Ctr.getAc().bitConv(lZehn);
	        int fAddr = Ctr.getAc().getfAddressbit(lZehn);

	        int[] RAM = Ctr.getMem().getRam();
	        int fRegister = RAM[fAddr];
	        int ergebnis;

	        switch (b) {
	            case 0:
	                ergebnis = fRegister & 0x1;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x1) {
	    	        	Ctr.getMem().incPc();
	                }
	                break;
	            case 1:
	                ergebnis = fRegister & 0x2;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x2) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 2:
	                ergebnis = fRegister & 0x4;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x4) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 3:
	                ergebnis = fRegister & 0x8;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x8) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 4:
	                ergebnis = fRegister & 0x10;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x10) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 5:
	                ergebnis = fRegister & 0x20;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x20) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 6:
	                ergebnis = fRegister & 0x40;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x40) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	            case 7:
	                ergebnis = fRegister & 0x80;
	                if (fAddr == 0x1) {
		            	Ctr.getMem().setprescalevar(0);
		            }
	                if (ergebnis == 0x80) {
	    	        	Ctr.getMem().incPc();
	    	        	Ctr.incLaufzeit();
	                }
	                break;
	        }
	        printCDCZ();
	        System.out.println("FINISH - Bit Test f, Skip if Set");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void movlw(int befehl) {
	        System.out.println("START - Move literal to W");

	        int k = Ctr.getAc().byteDefault(befehl);

	        Ctr.getMem().setwRegister(k);
	        printCDCZ();
	        System.out.println("FINISH - Move literal to W");
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	    }

	    public void retlw(int befehl) {
	        System.out.println("START - Return with literal in W");
	        int pc = Ctr.getMem().getStack();

	        int k = Ctr.getAc().byteDefault(befehl);
	        Ctr.getMem().setwRegister(k);

	        printCDCZ();
	        System.out.println("FINISH - Return with literal in W");
	        Ctr.getMem().setPC(pc);
	        Ctr.getMem().incPc();
	        Ctr.incLaufzeit();
	        Ctr.incLaufzeit();
	    }

	    public void addlw(int befehl) {
	        System.out.println("START - Add literal and W");

	        int k = Ctr.getAc().byteDefault(befehl);
	        int[] RAM = Ctr.getMem().getRam();
	        int wRegister = Ctr.getMem().getwRegister();
	        int hRegister = RAM[3];

	        // setzen des DC
	        int DChilfeW = wRegister & 0xF;
	        int DChilfeK = k & 0xF;

	        if ((DChilfeK + DChilfeW) > 0xF) {
	            hRegister |= 0x2;

	        } else {
	            if ((hRegister & 0x2) == 0x2) {
	                hRegister &= 0xFD;
	            }
	        }

	        // hier wird die normale Rechnung ausgeführt
	        wRegister += k;

	        // ergenis auf postiv, null oder negativ prüfen wegen Carry und Zero Setzung
	        if (wRegister > 0x0) {
	            // setze Carry Flag auf 0
	            if ((hRegister & 0x1) == 0x1) {
	                hRegister &= 0xFE;
	            }
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        } else if (wRegister == 0x0) {
	            // setze Carry Flag auf 0
	            if ((hRegister & 0x1) == 0x1) {
	                hRegister &= 0xFE;
	            }
	            // setze Zero Flag auf 1
	            hRegister |= 0x4;
	        } else {
	            wRegister = 0x100 + wRegister;
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        }
	        RAM[3] = hRegister;

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Add literal and W");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void sublw(int befehl) {
	        System.out.println("START - Subtract W from literal");
	        int k = Ctr.getAc().byteDefault(befehl);
	        int[] RAM = Ctr.getMem().getRam();
	        int hRegister = RAM[3];
	        int wRegister = Ctr.getMem().getwRegister();

	        // setzen des DC
	        int DChilfeW = wRegister & 0xF;
	        int DChilfeK = k & 0xF;

	        if (DChilfeK >= DChilfeW) {
	            hRegister |= 0x2;
	        } else {
	            if ((hRegister & 0x2) == 0x2) {
	                hRegister &= 0xFD;
	            }
	        }

	        int ergebnis = k - wRegister;

	        // ergenis auf postiv, null oder negativ prüfen
	        if (ergebnis > 0x0) {
	            wRegister = ergebnis;
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        } else if (ergebnis == 0x0) {
	            wRegister = ergebnis;
	            // setze Carry Flag auf 1
	            hRegister |= 0x1;
	            // setze Zero Flag auf 1
	            hRegister |= 0x4;
	        } else {
	            wRegister = 0x100 + ergebnis;
	            // setze Carry Flag auf 0
	            if ((hRegister & 0x1) == 0x1) {
	                hRegister &= 0xFE;
	            }
	            // setze Zero Flag auf 0
	            if ((hRegister & 0x4) == 0x4) {
	                hRegister &= 0xFB;
	            }
	        }
	        RAM[3] = hRegister;

	        Ctr.getMem().setRam(RAM);
	        Ctr.getMem().setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Subtract W from literal");
        	Ctr.getMem().incPc();
        	Ctr.incLaufzeit();
	    }

	    public void call(int befehl) {
	        System.out.println("START - Call subroutine");
	        Ctr.getMem().setStack(Ctr.getMem().getAktuellerPC());

	        int k = Ctr.getAc().elvenbitconv(befehl);
	        int[] RAM = Ctr.getMem().getRam();
	        int pclath = RAM[0xA];

	        pclath &= 0x18;
	        pclath = pclath << 8;
	        int pcneu = k + pclath;

	        printCDCZ();
	        System.out.println("FINISH - Call subroutine");
	        Ctr.getMem().setPC(pcneu);
	        Ctr.incLaufzeit();
	        Ctr.incLaufzeit();
	    }

	    public void gotobef(int befehl) {
	        System.out.println("START - Go to address");
	        int k = Ctr.getAc().elvenbitconv(befehl);
	        int[] RAM = Ctr.getMem().getRam();
	        int pclath = RAM[0xA];

	        pclath &= 0x18;
	        pclath = pclath << 8;
	        int pcneu = k + pclath;

	        printCDCZ();
	        System.out.println("FINISH - Go to address");
	        Ctr.getMem().setPC(pcneu);
	        Ctr.incLaufzeit();
	        Ctr.incLaufzeit();
	    }

	    public int checkZflag(int ergebnis, int RAM3) {
	        if (ergebnis == 0x0) {
	            RAM3 |= 0x4;
	        } else {
	            if ((RAM3 & 0x4) == 0x4) {
	                RAM3 &= 0xFB;
	            }
	        }
	        return RAM3;
	    }

	    public void printCDCZ() {
	        int[] RAM = Ctr.getMem().getRam();
	        int sRegister = RAM[3];
	        int Cflag;
	        int DCflag;
	        int Zflag;

	        if ((sRegister & 0x1) == 0x1) {
	            Cflag = 1;
	        } else {
	            Cflag = 0;
	        }
	        if ((sRegister & 0x2) == 0x2) {
	            DCflag = 1;
	        } else {
	            DCflag = 0;
	        }
	        if ((sRegister & 0x4) == 0x4) {
	            Zflag = 1;
	        } else {
	            Zflag = 0;
	        }

	        int wRegister = Ctr.getMem().getwRegister();
	        int wert1 = RAM[0xC];
	        int wert2 = RAM[0xD];
	        System.out.println("W = " + wRegister + "; wert1 = " + wert1 +"; wert2 = "+ wert2 + "; DC = " + DCflag + "; C = " + Cflag + "; Z = " + Zflag);
	    }
}
