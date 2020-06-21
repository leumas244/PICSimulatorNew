
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
			
			int code = ctr.getMem().getCurrentCommand(ctr.getMem().getAktuellerPC());
            ctr.getMk().vorsortieren(code);
            if(ctr.checkT0CS()==true) {
            	ctr.getMem().prescaler();
            }
			
            
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ctr.setRunning(false);
	}

}
