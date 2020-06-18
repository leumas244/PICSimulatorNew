
public class Processor extends Thread {
	private Controller ctr;
	protected boolean exit = false;

	public Processor(Controller ctr) {
		this.ctr = ctr;
	}
	public void run() {
		while (!exit) {
			
			int code = ctr.getMem().getCurrentCommand(ctr.getMem().getAktuellerPC());
            int pc = ctr.getMk().vorsortieren(code, ctr.getMem().getAktuellerPC());
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ctr.setRunning(false);
	}

}
