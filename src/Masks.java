
public class Masks {
	Controller Ctr;

    public Masks(Controller ctr) {
        this.Ctr = ctr;
    }

    public void vorsortieren(int befehl) {
        /*
        Methode um die festen Befehle rauszupicken, welche immer die gleichen Befehlsziffern haben.
        Ist der Befehl hier nicht dabei, wird die sortier Methode mit Masken aufgerufen.
        Beim erkennen eines Befehls wird dieser zusätlich aufgerufen.
         */
        switch (befehl) {
            case 0x0000:
            case 0x0020:
            case 0x0040:
            case 0x0060:
                System.out.println("NOP");
                Ctr.getCom().nop();
                break;
            case 0x0064:
                System.out.println("CLRWDT");
                Ctr.getCom().clrwdt(befehl);
                break;
            case 0x0009:
                System.out.println("RETFIE");
                Ctr.getCom().retfie();
                break;
            case 0x0008:
                System.out.println("RETURN");
                Ctr.getCom().returnbef();
                break;
            case 0x0063:
                System.out.println("SLEEP");
                Ctr.getCom().sleep();
                break;
            default:
                sortieren(befehl);
        }
    }

    public void sortieren(int befehl) {
        /*
        Methode um die Befhele zu Identifizieren, welche keine festen Befehlsziffern haben.
        Der Befhels String wird in eine Hexzahl umgewandelt und mit den jeweiligen Masken logisch verundet.
        Es gibt 4 Masken: 3F00, 3F80, 3C00, 3E00, 3800
        Beim erkennen eines Befehls wird dieser zusätlich aufgerufen.
         */
//        int hexbefehl = Integer.parseInt(befehl, 16);
        switch (befehl & 0x3F00) {
            case 0x0700:
                System.out.println("ADDWF");
                Ctr.getCom().addwf(befehl);
                break;
            case 0x0500:
                System.out.println("ANDWF");
                Ctr.getCom().andwf(befehl);
                break;
            case 0x0900:
                System.out.println("COMF");
                Ctr.getCom().comf(befehl);
                break;
            case 0x0300:
                System.out.println("DECF");
                Ctr.getCom().decf(befehl);
                break;
            case 0x0B00:
                System.out.println("DECFSZ");
                Ctr.getCom().decfsz(befehl);
                break;
            case 0x0A00:
                System.out.println("INCF");
                Ctr.getCom().incf(befehl);
                break;
            case 0x0F00:
                System.out.println("INCFSZ");
                Ctr.getCom().incfsz(befehl);
                break;
            case 0x0400:
                System.out.println("IORWF");
                Ctr.getCom().iorwf(befehl);
                break;
            case 0x0800:
                System.out.println("MOVF");
                Ctr.getCom().movf(befehl);
                break;
            case 0x0D00:
                System.out.println("RLF");
                Ctr.getCom().rlf(befehl);
                break;
            case 0x0C00:
                System.out.println("RRF");
                Ctr.getCom().rrf(befehl);
                break;
            case 0x0200:
                System.out.println("SUBWF");
                Ctr.getCom().subwf(befehl);
                break;
            case 0x0E00:
                System.out.println("SWAPF");
                Ctr.getCom().swapf(befehl);
                break;
            case 0x0600:
                System.out.println("XORWF");
                Ctr.getCom().xorwf(befehl);
                break;
            case 0x3900:
                System.out.println("ANDLW");
                Ctr.getCom().andlw(befehl);
                break;
            case 0x3800:
                System.out.println("IORLW");
                Ctr.getCom().iorlw(befehl);
                break;
            case 0x3A00:
                System.out.println("XORLW");
                Ctr.getCom().xorlw(befehl);
                break;
        }
        switch (befehl & 0x3F80) {
            case 0x0180:
                System.out.println("CLRF");
                Ctr.getCom().clrf(befehl);
                break;
            case 0x0100:
                System.out.println("CLRW");
                Ctr.getCom().clrw();
                break;
            case 0x0080:
                System.out.println("MOVEF");
                Ctr.getCom().movwf(befehl);
                break;
        }
        switch (befehl & 0x3C00) {
            case 0x1000:
                System.out.println("BCF");
                Ctr.getCom().bcf(befehl);
                break;
            case 0x1400:
                System.out.println("BSF");
                Ctr.getCom().bsf(befehl);
                break;
            case 0x1800:
                System.out.println("BTSFC");
                Ctr.getCom().btfsc(befehl);
                break;
            case 0x1C00:
                System.out.println("BTFSS");
                Ctr.getCom().btfss(befehl);
                break;
            case 0x3000:
                System.out.println("MOVLW");
                Ctr.getCom().movlw(befehl);
                break;
            case 0x3400:
                System.out.println("RETLW");
                Ctr.getCom().retlw(befehl);
                break;
        }
        switch (befehl & 0x3E00) {
            case 0x3E00:
                System.out.println("ADDLW");
                Ctr.getCom().addlw(befehl);
                break;
            case 0x3C00:
                System.out.println("SUBLW");
                Ctr.getCom().sublw(befehl);
                break;
        }
        switch (befehl & 0x3800) {
            case 0x2000:
                System.out.println("CALL");
                Ctr.getCom().call(befehl);
                break;
            case 0x2800:
                System.out.println("GOTO");
                Ctr.getCom().gotobef(befehl);
                // brauch i
                break;
        }
    }
}
