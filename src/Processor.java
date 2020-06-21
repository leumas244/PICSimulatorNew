
public class Processor extends Thread {
	private Controller ctr;
	protected boolean exit = false;

	public Processor(Controller ctr) {
		this.ctr = ctr;
	}
	@Override
	public void run() {
		ctr.setRunning(true);
		while (!exit) {
			if(ctr.getMem().getBreakPoint()[ctr.getMem().getAktuellerPC() + 1] == true) {
				ctr.setDebugMode(true);
			}
			
			int code = ctr.getMem().getCurrentCommand(ctr.getMem().getAktuellerPC());
            ctr.getMk().vorsortieren(code);
            if(ctr.checkT0CS()==true) {
            	ctr.getMem().incTmr0();
            }
			
            if (ctr.getisDebugMode()) {
            	while(!ctr.getnextStep()) {
            		try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	ctr.setnextStep(false);
            } else {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
		}
		ctr.setRunning(false);
	}

}
