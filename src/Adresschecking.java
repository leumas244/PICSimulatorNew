
public class Adresschecking {
	Controller Ctr;
	
	public Adresschecking(Controller ctr) {
        this.Ctr = ctr;
    }
	
	public int  byteDefault(int befehl) {
        return befehl & 0x00FF;
    }

    public int byteOrientedDesination(int lAcht){
        int dest = lAcht & 0x80;
        int destination;
        if (dest == 0x80) {
            destination = 1;
        } else {
            destination = 0;
        }
        return destination;
    }

    public int getfAddress(int lAcht) {
    	int adresse = 0;
    	int Status = Ctr.getMem().getRam()[0x3];
    	if ((Status & 0x20) == 0x20) {
    		adresse = (lAcht & 0x7F) + 0x80;
    	} else {
    		adresse = lAcht & 0x7F;
    	}
    	if (adresse == 0x0) {
    		return Ctr.getMem().getRam()[0x4];
    	} else {
    		return adresse;
    	}
    }

    public int bitOrientDefault(int befehl) {
        return befehl & 0x3FF;
    }

    public int bitConv(int lZehn) {
        int bit = lZehn & 0x380;
        int b = 0;
        switch (bit){
            case 0x00:
                b = 0;
                break;
            case 0x80:
                b = 1;
                break;
            case 0x100:
                b = 2;
                break;
            case 0x180:
                b = 3;
                break;
            case 0x200:
                b = 4;
                break;
            case 0x280:
                b = 5;
                break;
            case 0x300:
                b = 6;
                break;
            case 0x380:
                b = 6;
                break;
        }
        return b;
    }

    public int elvenbitconv(int befehl){
        return befehl & 0x07FF;
    }
    
    public int getfAddressbit(int lAcht) {
		int adresse = lAcht & 0x7F;
		if (adresse == 0x0) {
    		return Ctr.getMem().getRam()[0x4];
    	} else {
    		return adresse;
    	}
    }
}
