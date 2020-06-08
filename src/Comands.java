
public class Comands {
	    Controller Ctr;

	    public Comands(Controller ctr) {
	    	this.Ctr = ctr;
	    }

	    public void nop() {
	        System.out.println("No Operation");
	        printCDCZ();
	    }

	    public void clrwdt(int befehl) {
	        System.out.println("START - Clear WatchdogTimer");
	        printCDCZ();
	        System.out.println("FINISH - Clear WatchdogTimer");
	    }

	    public int retfie() {
	        System.out.println("START - Return from Interrupt");
	        int i = Ctr.Mem.getStack();

	        int[] RAM = Ctr.Mem.getRam();
	        int intconReg = RAM[0xB];
	        intconReg |= 0x80;

	        RAM[0xB] = intconReg;
	        Ctr.Mem.setRam(RAM);
	        printCDCZ();
	        System.out.println("FINISH - Return from Interrupt");
	        return i;
	    }

	    public int returnbef() {
	        System.out.println("START - Retrun from Subroutine");
	        int i = Ctr.Mem.getStack();

	        int i3 = i +1;
	        System.out.println("Kehre zurück zu: "+ i3);
	        printCDCZ();
	        System.out.println("FINISH - Retrun from Subroutine");
	        return i;
	    }

	    public void sleep() {
	        System.out.println("START - Go into standbymode");
	        printCDCZ();
	        System.out.println("FINISH - Go into standbymode");
	    }

	    public void addwf(int befehl) {
	        System.out.println("START - Add w to f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
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
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Add w to f");
	    }

	    public void andwf(int befehl) {
	        System.out.println("START - And w with f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister & wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - And w with f");
	    }

	    public void comf(int befehl) {
	        System.out.println("START - Compliment f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebnis = fRegister ^ 0xFF;

	        hRegister = checkZflag(ergebnis, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebnis;
	        } else if (dest == 0) {
	            wRegister = ergebnis;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Compliment f");
	    }

	    public void decf(int befehl) {
	        System.out.println("START - Decrement f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister - 1;

	        if (ergebniss < 0x0) {
	            ergebniss = 0x100 + ergebniss;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Decrement f");
	    }

	    public int decfsz(int befehl, int i) {
	        System.out.println("START - Decrement f, Skip if 0");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister - 1;

	        if (ergebniss < 0x0) {
	            ergebniss = 0x100 + ergebniss;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (ergebniss == 0x0) {
	            i++;
	        }

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Decrement f, Skip if 0");
	        return i;
	    }

	    public void incf(int befehl) {
	        System.out.println("START - Increment f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister + 1;

	        if (ergebniss == 0x100) {
	            ergebniss = 0x0;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Increment f");
	    }

	    public int incfsz(int befehl, int i) {
	        System.out.println("START - Increment f, Skip if 0");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister + 1;

	        if (ergebniss == 0x100) {
	            ergebniss = 0x0;
	        }

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (ergebniss == 0x0) {
	            i++;
	        }

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Increment f, Skip if 0");
	        return i;
	    }

	    public void iorwf(int befehl) {
	        System.out.println("START - Inclusive Or w with f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister | wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Inclusive Or w with f");
	    }

	    public void movf(int befehl) {
	        System.out.println("START - Move f to destiantion w or f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        hRegister = checkZflag(fRegister, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = fRegister;
	        } else if (dest == 0) {
	            wRegister = fRegister;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Move f to destiantion w or f");
	    }

	    public void rlf(int befehl) {
	        System.out.println("START - Rotate Left f through Carry");

	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
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
	            Ctr.Mem.setRam(RAM);
	        } else if (dest == 0) {
	            Ctr.Mem.setwRegister(fRegisterNeu);
	        }

	        RAM[3] = (RAM[3] & 0xFE) + CNeu;

	        Ctr.Mem.setRam(RAM);

	        System.out.println(RAM[fAddr]);
	        System.out.println(CNeu);
	        printCDCZ();
	        System.out.println("FINISH - Rotate Left f through Carry");
	    }

	    public void rrf(int befehl) {
	        System.out.println("START - Rotate Right f through Carry");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
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
	            Ctr.Mem.setRam(RAM);
	        } else if (dest == 0) {
	            Ctr.Mem.setwRegister(fRegisterNeu);
	        }

	        RAM[3] = (RAM[3] & 0xFE) + CNeu;

	        Ctr.Mem.setRam(RAM);

	        System.out.println(RAM[fAddr]);
	        System.out.println(CNeu);
	        printCDCZ();
	        System.out.println("FINISH - Rotate Right f through Carry");
	    }

	    public void subwf(int befehl) {
	        System.out.println("START - Subtract W from f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int hRegister = RAM[3];
	        int wRegister = Ctr.Mem.getwRegister();

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
	        } else if (dest == 0) {
	            wRegister = ergebnis;
	        }

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Subtract W from f");
	    }

	    public void swapf(int befehl) {
	        System.out.println("START - Swap nibbles in f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();

	        int rechteSeite = fRegister & 0xF;
	        int linkeSeite = fRegister & 0xF0;

	        int neulinkeSeite = rechteSeite << 4;
	        int neurechteSeite = linkeSeite >> 4;
	        int ergebniss = neulinkeSeite + neurechteSeite;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Swap nibbles in f");
	    }

	    public void xorwf(int befehl) {
	        System.out.println("START - Exclusive OR W with f");

	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int dest = Ctr.Ac.byteOrientedDesination(lAcht);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        int ergebniss = fRegister ^ wRegister;

	        hRegister = checkZflag(ergebniss, hRegister);
	        RAM[3] = hRegister;

	        if (dest == 1) {
	            RAM[fAddr] = ergebniss;
	        } else if (dest == 0) {
	            wRegister = ergebniss;
	        }
	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Exclusive OR W with f");
	    }

	    public void andlw(int befehl) {
	        System.out.println("START - Add literal and W");
	        int k = Ctr.Ac.byteDefault(befehl);
	        int[] RAM = Ctr.Mem.getRam();
	        int wRegister = Ctr.Mem.getwRegister();
	        int hRegister = RAM[3];

	        wRegister &= k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Add literal and W");
	    }

	    public void iorlw(int befehl) {
	        System.out.println("START - Inclusive OR literal with W");

	        int k = Ctr.Ac.byteDefault(befehl);
	        int wRegister = Ctr.Mem.getwRegister();
	        int[] RAM = Ctr.Mem.getRam();
	        int hRegister = RAM[3];

	        wRegister = wRegister | k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);

	        printCDCZ();
	        System.out.println("FINISH - Inclusive OR literal with W");
	    }

	    public void xorlw(int befehl) {
	        System.out.println("START - Exclusive OR literal with W");

	        int k = Ctr.Ac.byteDefault(befehl);
	        int wRegister = Ctr.Mem.getwRegister();
	        int[] RAM = Ctr.Mem.getRam();
	        int hRegister = RAM[3];

	        wRegister = wRegister ^ k;

	        hRegister = checkZflag(wRegister, hRegister);
	        RAM[3] = hRegister;

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Exclusive OR literal with W");
	    }

	    public void clrf(int befehl) {
	        System.out.println("START - Clear f");
	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int Z = RAM[3] & 0x4;
	        RAM[fAddr] = 0;

	        if (Z == 0x0) {
	            RAM[3] += 0x4;
	        }

	        Ctr.Mem.setRam(RAM);
	        printCDCZ();

	        System.out.println("FINISH - Clear f");
	    }

	    public void clrw() {
	        System.out.println("START - Clear w");
	        int[] RAM = Ctr.Mem.getRam();
	        int Z = RAM[3] & 0x4;

	        int wRegister = 0;

	        if (Z == 0x0) {
	            RAM[3] += 0x4;
	        }

	        Ctr.Mem.setwRegister(wRegister);
	        Ctr.Mem.setRam(RAM);
	        printCDCZ();

	        System.out.println("FINISH - Clear w");
	    }

	    public void movwf(int befehl) {
	        System.out.println("START - Move W to f");

	        int lAcht = Ctr.Ac.byteDefault(befehl);
	        int fAddr = Ctr.Ac.getfAddress(lAcht);

	        int[] RAM = Ctr.Mem.getRam();
	        int wRegister = Ctr.Mem.getwRegister();

	        RAM[fAddr] = wRegister;

	        Ctr.Mem.setRam(RAM);
	        printCDCZ();
	        System.out.println("FINISH - Move W to f");
	    }

	    public void bcf(int befehl) {
	        System.out.println("START - Bit Clear f");
	        int lZehn = Ctr.Ac.bitOrientDefault(befehl);
	        int b = Ctr.Ac.bitConv(lZehn);
	        int fAddr = Ctr.Ac.getfAddress(lZehn);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];

	        switch (b) {
	            case 0:
	                fRegister &= 0xFE;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 1:
	                fRegister &= 0xFD;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 2:
	                fRegister &= 0xFB;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 3:
	                fRegister &= 0xF7;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 4:
	                fRegister &= 0xEF;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 5:
	                fRegister &= 0xDF;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 6:
	                fRegister &= 0xBF;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 7:
	                fRegister &= 0x7F;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	        }

	        System.out.println(fAddr);
	        System.out.println(RAM[fAddr]);
	        printCDCZ();
	        System.out.println("FINISH - Bit Clear f");
	    }

	    public void bsf(int befehl) {
	        System.out.println("START - Bit Set f");
	        int lZehn = Ctr.Ac.bitOrientDefault(befehl);
	        int b = Ctr.Ac.bitConv(lZehn);
	        int fAddr = Ctr.Ac.getfAddress(lZehn);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];

	        switch (b) {
	            case 0:
	                fRegister |= 0x1;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 1:
	                fRegister |= 0x2;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 2:
	                fRegister |= 0x4;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 3:
	                fRegister |= 0x8;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 4:
	                fRegister |= 0x10;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 5:
	                fRegister |= 0x20;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 6:
	                fRegister |= 0x40;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	            case 7:
	                fRegister |= 0x80;
	                RAM[fAddr] = fRegister;
	                Ctr.Mem.setRam(RAM);
	                break;
	        }

	        System.out.println(fAddr);
	        System.out.println(RAM[fAddr]);
	        printCDCZ();
	        System.out.println("FINISH - Bit Set f");
	    }

	    public int btfsc(int befehl, int i) {
	        System.out.println("START - Bit Test f, Skip if Clear");
	        int lZehn = Ctr.Ac.bitOrientDefault(befehl);
	        int b = Ctr.Ac.bitConv(lZehn);
	        int fAddr = Ctr.Ac.getfAddress(lZehn);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int ergebnis;

	        switch (b) {
	            case 0:
	                ergebnis = fRegister & 0x1;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 1:
	                ergebnis = fRegister & 0x2;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 2:
	                ergebnis = fRegister & 0x4;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 3:
	                ergebnis = fRegister & 0x8;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 4:
	                ergebnis = fRegister & 0x10;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 5:
	                ergebnis = fRegister & 0x20;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 6:
	                ergebnis = fRegister & 0x40;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	            case 7:
	                ergebnis = fRegister & 0x80;
	                if (ergebnis == 0x0) {
	                    i++;
	                }
	                break;
	        }
	        printCDCZ();
	        System.out.println("FINISH - Bit Test f, Skip if Clear");
	        return i;
	    }

	    public int btfss(int befehl, int i) {
	        System.out.println("START - Bit Test f, Skip if Set");
	        int lZehn = Ctr.Ac.bitOrientDefault(befehl);
	        int b = Ctr.Ac.bitConv(lZehn);
	        int fAddr = Ctr.Ac.getfAddress(lZehn);

	        int[] RAM = Ctr.Mem.getRam();
	        int fRegister = RAM[fAddr];
	        int ergebnis;

	        switch (b) {
	            case 0:
	                ergebnis = fRegister & 0x1;
	                if (ergebnis == 0x1) {
	                    i++;
	                }
	                break;
	            case 1:
	                ergebnis = fRegister & 0x2;
	                if (ergebnis == 0x2) {
	                    i++;
	                }
	                break;
	            case 2:
	                ergebnis = fRegister & 0x4;
	                if (ergebnis == 0x4) {
	                    i++;
	                }
	                break;
	            case 3:
	                ergebnis = fRegister & 0x8;
	                if (ergebnis == 0x8) {
	                    i++;
	                }
	                break;
	            case 4:
	                ergebnis = fRegister & 0x10;
	                if (ergebnis == 0x10) {
	                    i++;
	                }
	                break;
	            case 5:
	                ergebnis = fRegister & 0x20;
	                if (ergebnis == 0x20) {
	                    i++;
	                }
	                break;
	            case 6:
	                ergebnis = fRegister & 0x40;
	                if (ergebnis == 0x40) {
	                    i++;
	                }
	                break;
	            case 7:
	                ergebnis = fRegister & 0x80;
	                if (ergebnis == 0x80) {
	                    i++;
	                }
	                break;
	        }
	        printCDCZ();
	        System.out.println("FINISH - Bit Test f, Skip if Set");
	        return i;
	    }

	    public void movlw(int befehl) {
	        System.out.println("START - Move literal to W");

	        int k = Ctr.Ac.byteDefault(befehl);

	        Ctr.Mem.setwRegister(k);
	        printCDCZ();
	        System.out.println("FINISH - Move literal to W");
	    }

	    public int retlw(int befehl) {
	        System.out.println("START - Return with literal in W");
	        int i = Ctr.Mem.getStack();

	        int k = Ctr.Ac.byteDefault(befehl);
	        Ctr.Mem.setwRegister(k);

	        int i3 = i + 1;
	        System.out.println("Kehre zurück zu: "+ i3);
	        printCDCZ();
	        System.out.println("FINISH - Return with literal in W");
	        return i;
	    }

	    public void addlw(int befehl) {
	        System.out.println("START - Add literal and W");

	        int k = Ctr.Ac.byteDefault(befehl);
	        int[] RAM = Ctr.Mem.getRam();
	        int wRegister = Ctr.Mem.getwRegister();
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

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Add literal and W");
	    }

	    public void sublw(int befehl) {
	        System.out.println("START - Subtract W from literal");
	        int k = Ctr.Ac.byteDefault(befehl);
	        int[] RAM = Ctr.Mem.getRam();
	        int hRegister = RAM[3];
	        int wRegister = Ctr.Mem.getwRegister();

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

	        Ctr.Mem.setRam(RAM);
	        Ctr.Mem.setwRegister(wRegister);
	        printCDCZ();
	        System.out.println("FINISH - Subtract W from literal");

	    }

	    public int call(int befehl, int i) {
	        System.out.println("START - Call subroutine");
	        Ctr.Mem.setStack(i);

	        int k = Ctr.Ac.elvenbitconv(befehl);
	        int[] RAM = Ctr.Mem.getRam();
	        int pclath = RAM[0xA];

	        pclath &= 0x18;
	        pclath = pclath << 8;
	        int ineu = k + pclath - 1;

	        int iprint = ineu + 1;
	        System.out.println("Gehe zu: "+ iprint);
	        printCDCZ();
	        System.out.println("FINISH - Call subroutine");
	        return ineu;
	    }

	    public int gotobef(int befehl) {
	        System.out.println("START - Go to address");
	        int k = Ctr.Ac.elvenbitconv(befehl);
	        int[] RAM = Ctr.Mem.getRam();
	        int pclath = RAM[0xA];

	        pclath &= 0x18;
	        pclath = pclath << 8;
	        int i = k + pclath - 1;
	        int iprint = i +1;

	        System.out.println("Gehe zu: "+ iprint);
	        printCDCZ();
	        System.out.println("FINISH - Go to address");
	        return i;
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
	        int[] RAM = Ctr.Mem.getRam();
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

	        int wRegister = Ctr.Mem.getwRegister();
	        int wert1 = RAM[0xC];
	        int wert2 = RAM[0xD];
	        System.out.println("W = " + wRegister + "; wert1 = " + wert1 +"; wert2 = "+ wert2 + "; DC = " + DCflag + "; C = " + Cflag + "; Z = " + Zflag);
	    }
}
