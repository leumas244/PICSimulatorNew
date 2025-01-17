import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class Controller {
	private GUI gui;
	private Memory Mem;
	private Comands Com;
	private Adresschecking Ac;
	private Masks Mk;
	private Read Rd;
	private Processor prc;
	private boolean isRunning = false;
	private boolean isDebugMode = false;
	private boolean nextStep = false;
	private int laufzeit = 0;
	private boolean fileisread = false;

	public Controller(GUI gui) {
		this.gui = gui;
		this.Mem = new Memory(this);
		this.initializeRamtable();
		this.Mem.start();
		this.Com = new Comands(this);
		this.Ac = new Adresschecking(this);
		this.Mk = new Masks(this);
		this.Rd = new Read(this);
	}

	public void loadFile() {
		gui.programtableloeschen();
		this.getMem().resetPCtoRow();
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(gui.frame);

		File file = fc.getSelectedFile();
		String filename = file.getAbsolutePath();
		Mem.setFilename(filename);

		this.gui.setFileValue(filename);

		FileReader fileReader;
		BufferedReader bufferedReader;
		try {
			fileReader = new FileReader(filename);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String pCounter = line.substring(0, 4);
				boolean BP = new Boolean(false);
				String pCode = line.substring(5, 9);
				String row = line.substring(20, 25);
				String label = " ";
				String comand = " ";
				String coment = " ";
				if (!(line.substring(27, 28).equals(" "))) {
					label = line.substring(27, 36);
					comand = " ";
					coment = " ";
				} else {
					label = " ";
					if (line.length() >= 37) {
						if (line.substring(36, 37).equals(";")) {
							comand = " ";
							coment = line.substring(36, line.length());
						} else {
							if (line.length() >= 56) {
								comand = line.substring(36, 56);
								coment = line.substring(56, line.length());
							} else {
								comand = line.substring(36, line.length());
								coment = " ";
							}

						}
					} else {
						comand = " ";
						coment = " ";
					}
				}

				DefaultTableModel model = (DefaultTableModel) gui.table.getModel();
				model.addRow(new Object[] { BP, pCounter, pCode, row, label, comand, coment });
				if (!(pCounter.equals("    "))) {
					int PC = Integer.parseInt(pCounter, 16);
					int rownuber = Integer.parseInt(row);
					int[] PCtoRow = this.getMem().getPCtoRow();
					PCtoRow[PC] = rownuber;
					this.getMem().setPCtoRow(PCtoRow);
				}
			}
			bufferedReader.close();

			Rd.readLines(filename);
      this.fileisread = true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateRamGui(int x, int y, int value) {
		gui.updateRamtable(x, y, Integer.toHexString(value));
	}

	public void updateSFR() {
		int[] RAM = getMem().getRam();
		gui.updateWReg(Integer.toHexString(getMem().getwRegister()));
		gui.updatePC(Integer.toHexString(getMem().getAktuellerPC()));
		gui.updateFSR(Integer.toHexString(RAM[0x4]));
		gui.updatePCL(Integer.toHexString(RAM[0x2]));
		gui.updatePCLATH(Integer.toHexString(RAM[0xA]));

		this.updateSFRStatus();
		this.updateSFROption();
		this.updateSFRIntcon();
	}

	public void updateSFRStatus() {
		int[] RAM = getMem().getRam();
		int Status = RAM[0x3];
		gui.updateStatus(Integer.toHexString(Status));
		if ((Status & 0x80) == 0x80) {
			gui.updateStatusIRP("1");
		} else {
			gui.updateStatusIRP("0");
		}
		if ((Status & 0x40) == 0x40) {
			gui.updateStatusRP1("1");
		} else {
			gui.updateStatusRP1("0");
		}
		if ((Status & 0x20) == 0x20) {
			gui.updateStatusRP0("1");
		} else {
			gui.updateStatusRP0("0");
		}
		if ((Status & 0x10) == 0x10) {
			gui.updateStatusTo("1");
		} else {
			gui.updateStatusTo("0");
		}
		if ((Status & 0x8) == 0x8) {
			gui.updateStatusPd("1");
		} else {
			gui.updateStatusPd("0");
		}
		if ((Status & 0x4) == 0x4) {
			gui.updateStatusZ("1");
		} else {
			gui.updateStatusZ("0");
		}
		if ((Status & 0x2) == 0x2) {
			gui.updateStatusDc("1");
		} else {
			gui.updateStatusDc("0");
		}
		if ((Status & 0x1) == 0x1) {
			gui.updateStatusC("1");
		} else {
			gui.updateStatusC("0");
		}
	}

	public void updateSFROption() {
		int[] RAM = getMem().getRam();
		int Option = RAM[0x81];
		gui.updateOption(Integer.toHexString(Option));
		if ((Option & 0x80) == 0x80) {
			gui.updateOptionRBP("1");
		} else {
			gui.updateOptionRBP("0");
		}
		if ((Option & 0x40) == 0x40) {
			gui.updateOptionIntEdg("1");
		} else {
			gui.updateOptionIntEdg("0");
		}
		if ((Option & 0x20) == 0x20) {
			gui.updateOptionT0CS("1");
		} else {
			gui.updateOptionT0CS("0");
		}
		if ((Option & 0x10) == 0x10) {
			gui.updateOptionT0SE("1");
		} else {
			gui.updateOptionT0SE("0");
		}
		if ((Option & 0x8) == 0x8) {
			gui.updateOptionPsa("1");
		} else {
			gui.updateOptionPsa("0");
		}
		if ((Option & 0x4) == 0x4) {
			gui.updateOptionPs2("1");
		} else {
			gui.updateOptionPs2("0");
		}
		if ((Option & 0x2) == 0x2) {
			gui.updateOptionPs1("1");
		} else {
			gui.updateOptionPs1("0");
		}
		if ((Option & 0x1) == 0x1) {
			gui.updateOptionPs0("1");
		} else {
			gui.updateOptionPs0("0");
		}
	}

	public void updateSFRIntcon() {
		int[] RAM = getMem().getRam();
		int Intcon = RAM[0xB];
		gui.updateIntcon(Integer.toHexString(Intcon));
		if ((Intcon & 0x80) == 0x80) {
			gui.updateIntconGIE("1");
		} else {
			gui.updateIntconGIE("0");
		}
		if ((Intcon & 0x40) == 0x40) {
			gui.updateIntconEIE("1");
		} else {
			gui.updateIntconEIE("0");
		}
		if ((Intcon & 0x20) == 0x20) {
			gui.updateIntconTIE("1");
		} else {
			gui.updateIntconTIE("0");
		}
		if ((Intcon & 0x10) == 0x10) {
			gui.updateIntconIE("1");
		} else {
			gui.updateIntconIE("0");
		}
		if ((Intcon & 0x8) == 0x8) {
			gui.updateIntconRIE("1");
		} else {
			gui.updateIntconRIE("0");
		}
		if ((Intcon & 0x4) == 0x4) {
			gui.updateIntconTIF("1");
		} else {
			gui.updateIntconTIF("0");
		}
		if ((Intcon & 0x2) == 0x2) {
			gui.updateIntconIF("1");
		} else {
			gui.updateIntconIF("0");
		}
		if ((Intcon & 0x1) == 0x1) {
			gui.updateIntconRIF("1");
		} else {
			gui.updateIntconRIF("0");
		}
	}

	public void updateStack() {
		int[] stack = getMem().getganzenStack();
		gui.updateStack_0(Integer.toHexString(stack[0]));
		gui.updateStack_1(Integer.toHexString(stack[1]));
		gui.updateStack_2(Integer.toHexString(stack[2]));
		gui.updateStack_3(Integer.toHexString(stack[3]));
		gui.updateStack_4(Integer.toHexString(stack[4]));
		gui.updateStack_5(Integer.toHexString(stack[5]));
		gui.updateStack_6(Integer.toHexString(stack[6]));
		gui.updateStack_7(Integer.toHexString(stack[7]));
	}

	public void markrow(int x) {
		int[] PCtoRow = this.getMem().getPCtoRow();
		int row = PCtoRow[x];
		if (row != 0) {
			gui.markarow(row);
		}
	}

	public void demarkrow() {
		gui.demarkallrows();
	}

	private void initializeRamtable() {
		for (int i = 0; i < 32; i++) {
			gui.addRowToRam(
					new Object[] { Integer.toHexString(i * 8), "00", "00", "00", "00", "00", "00", "00", "00" });
		}
	}

	public void startProcessor() {
		if (fileisread) {
			if (isDebugMode) {
				nextStep();
				setDebugMode(false);
			} else if(isRunning==false) {
				prc = new Processor(this);
				prc.start();
			}
		}
	}

	public void stopProcessor() {
		if (fileisread) {
			if(isRunning) {
				nextStep();
				setDebugMode(false);
				prc.exit=true;
			}
		}
	}
	
	public void interupt() {
		
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public Memory getMem() {
		return Mem;
	}

	public Comands getCom() {
		return Com;
	}

	public Adresschecking getAc() {
		return Ac;
	}

	public Masks getMk() {
		return Mk;
	}

	public void resetMem() {
		this.getMem().reset();
		this.setLaufzeit(0);
	}

	public void timerinc() {
		this.getMem().checkT0cs();
	}

	public boolean getisDebugMode() {
		return isDebugMode;
	}

	public void setDebugMode(boolean isDebugMode) {
		this.isDebugMode = isDebugMode;
	}

	public boolean getnextStep() {
		return nextStep;
	}

	public void setnextStep(boolean nextStep) {
		this.nextStep = nextStep;
	}

	public void nextStep() {
		if (isDebugMode) {
			this.nextStep = true;
		}
	}

	public Boolean getBP(int x) {
		Boolean BP = gui.getBP(x);
		return BP;
	}

	public int getrows() {
		int rowCount = gui.getrows();
		return rowCount;
	}

	public void checkBPs() {
		Boolean[] BreakPoint = getMem().getBreakPoint();
		int anzahlrows = getrows();
		if (anzahlrows != 0) {
			for (int i = 0; i < anzahlrows; i++) {
				if (!(gui.getPCfromfile(i).equals("    "))) {
					int PC = Integer.parseInt(gui.getPCfromfile(i), 16);
					BreakPoint[PC] = getBP(i);
				}
			}
		}
		getMem().setBreakPoint(BreakPoint);
	}

	public void incTimer() {
		this.getMem().prescaler();
	}
	public void incWatchMem() {
		if(gui.getCheckbxWatchdog()==true) {
			this.Mem.prewatchdog();
		}
	}

	public int getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(int laufzeit) {
		this.laufzeit = laufzeit;
	}
	public void incLaufzeit() {
		this.laufzeit++;
	}
	public void updateLaufzeit() {
		gui.setLaufzeit(Integer.toString(laufzeit) + "μs");
		
	}
	public void updateWatchdog() {
		gui.setWatchdog(Integer.toString(this.getMem().getWatchdog()) + "μs");
	}
	
	public void updateTrisA() {
		int[] RAM = getMem().getRam();
		int TrisA = RAM[0x85];
		if ((TrisA & 0x1) == 0x1) {
			gui.setchckbxAT0(true);
		} else {
			gui.setchckbxAT0(false);
		}
		if ((TrisA & 0x2) == 0x2) {
			gui.setchckbxAT1(true);
		} else {
			gui.setchckbxAT1(false);
		}
		if ((TrisA & 0x4) == 0x4) {
			gui.setchckbxAT2(true);
		} else {
			gui.setchckbxAT2(false);
		}
		if ((TrisA & 0x8) == 0x8) {
			gui.setchckbxAT3(true);
		} else {
			gui.setchckbxAT3(false);
		}
		if ((TrisA & 0x10) == 0x10) {
			gui.setchckbxAT4(true);
		} else {
			gui.setchckbxAT4(false);
		}
	}
	
	public void updateTrisB() {
		int[] RAM = getMem().getRam();
		int TrisB = RAM[0x86];
		if ((TrisB & 0x1) == 0x1) {
			gui.setchckbxBT0(true);
		} else {
			gui.setchckbxBT0(false);
		}
		if ((TrisB & 0x2) == 0x2) {
			gui.setchckbxBT1(true);
		} else {
			gui.setchckbxBT1(false);
		}
		if ((TrisB & 0x4) == 0x4) {
			gui.setchckbxBT2(true);
		} else {
			gui.setchckbxBT2(false);
		}
		if ((TrisB & 0x8) == 0x8) {
			gui.setchckbxBT3(true);
		} else {
			gui.setchckbxBT3(false);
		}
		if ((TrisB & 0x10) == 0x10) {
			gui.setchckbxBT4(true);
		} else {
			gui.setchckbxBT4(false);
		}
		if ((TrisB & 0x20) == 0x20) {
			gui.setchckbxBT5(true);
		} else {
			gui.setchckbxBT5(false);
		}
		if ((TrisB & 0x40) == 0x40) {
			gui.setchckbxBT6(true);
		} else {
			gui.setchckbxBT6(false);
		}
		if ((TrisB & 0x80) == 0x80) {
			gui.setchckbxBT7(true);
		} else {
			gui.setchckbxBT7(false);
		}
	}
	
	public void updatePortA() {
		int[] RAM = getMem().getRam();
		int TrisA = RAM[0x85];
		int PortA = RAM[0x5];
		if ((TrisA & 0x1) == 0x0) {
			if ((PortA & 0x1) == 0x1) {
				gui.setchckbxAP0(true);
			} else {
				gui.setchckbxAP0(false);
			}
		} else {
			boolean entry = gui.getchckbxAP0();
			if (entry) {
				RAM[0x5] |= 0x1;
				getMem().setRam(RAM);
			} else {
				RAM[0x5] &= 0xFE;
				getMem().setRam(RAM);
			}
		}
		if ((TrisA & 0x2) == 0x0) {
			if ((PortA & 0x2) == 0x2) {
				gui.setchckbxAP1(true);
			} else {
				gui.setchckbxAP1(false);
			}
		} else {
			boolean entry = gui.getchckbxAP1();
			if (entry) {
				RAM[0x5] |= 0x2;
				getMem().setRam(RAM);
			} else {
				RAM[0x5] &= 0xFD;
				getMem().setRam(RAM);
			}
		}
		if ((TrisA & 0x4) == 0x0) {
			if ((PortA & 0x4) == 0x4) {
				gui.setchckbxAP2(true);
			} else {
				gui.setchckbxAP2(false);
			}
		} else {
			boolean entry = gui.getchckbxAP2();
			if (entry) {
				RAM[0x5] |= 0x4;
				getMem().setRam(RAM);
			} else {
				RAM[0x5] &= 0xFB;
				getMem().setRam(RAM);
			}
		}
		if ((TrisA & 0x8) == 0x0) {
			if ((PortA & 0x8) == 0x8) {
				gui.setchckbxAP3(true);
			} else {
				gui.setchckbxAP3(false);
			}
		} else {
			boolean entry = gui.getchckbxAP3();
			if (entry) {
				RAM[0x5] |= 0x8;
				getMem().setRam(RAM);
			} else {
				RAM[0x5] &= 0xF7;
				getMem().setRam(RAM);
			}
		}
		if ((TrisA & 0x10) == 0x0) {
			if ((PortA & 0x10) == 0x10) {
				gui.setchckbxAP4(true);
			} else {
				gui.setchckbxAP4(false);
			}
		} else {
			boolean entry = gui.getchckbxAP4();
			if (entry) {
				RAM[0x5] |= 0x10;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x5] & 0x10;
				int newValue = (RAM[0x5] & 0xEF) & 0x10;
				if ((oldValue == 0x10) && (newValue == 0x0)) {
					getMem().prescaler();
				}
				RAM[0x5] &= 0xEF;
				getMem().setRam(RAM);
			}
		}
	}
	
	public void updatePortB() {
		int[] RAM = getMem().getRam();
		int TrisB = RAM[0x86];
		int PortB = RAM[0x6];
		if ((TrisB & 0x1) == 0x0) {
			if ((PortB & 0x1) == 0x1) {
				gui.setchckbxBP0(true);
			} else {
				gui.setchckbxBP0(false);
			}
		} else {
			boolean entry = gui.getchckbxBP0();
			if (entry) {
				RAM[0x6] |= 0x1;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x6] & 0x1;
				int newValue = (RAM[0x6] &= 0xFE) & 0x1;
				if ((oldValue == 0x1) && (newValue == 0x0)) {
					RAM[0xB] |= 0x2;
				}
				RAM[0x6] &= 0xFE;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x2) == 0x0) {
			if ((PortB & 0x2) == 0x2) {
				gui.setchckbxBP1(true);
			} else {
				gui.setchckbxBP1(false);
			}
		} else {
			boolean entry = gui.getchckbxBP1();
			if (entry) {
				RAM[0x6] |= 0x2;
				getMem().setRam(RAM);
			} else {
				RAM[0x6] &= 0xFD;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x4) == 0x0) {
			if ((PortB & 0x4) == 0x4) {
				gui.setchckbxBP2(true);
			} else {
				gui.setchckbxBP2(false);
			}
		} else {
			boolean entry = gui.getchckbxBP2();
			if (entry) {
				RAM[0x6] |= 0x4;
				getMem().setRam(RAM);
			} else {
				RAM[0x6] &= 0xFB;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x8) == 0x0) {
			if ((PortB & 0x8) == 0x8) {
				gui.setchckbxBP3(true);
			} else {
				gui.setchckbxBP3(false);
			}
		} else {
			boolean entry = gui.getchckbxBP3();
			if (entry) {
				RAM[0x6] |= 0x8;
				getMem().setRam(RAM);
			} else {
				RAM[0x6] &= 0xF7;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x10) == 0x0) {
			if ((PortB & 0x10) == 0x10) {
				gui.setchckbxBP4(true);
			} else {
				gui.setchckbxBP4(false);
			}
		} else {
			boolean entry = gui.getchckbxBP4();
			if (entry) {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] |= 0x10;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] |= 0x10;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] &= 0xEF;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] &= 0xEF;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x20) == 0x0) {
			if ((PortB & 0x20) == 0x20) {
				gui.setchckbxBP5(true);
			} else {
				gui.setchckbxBP5(false);
			}
		} else {
			boolean entry = gui.getchckbxBP5();
			if (entry) {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] |= 0x20;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] |= 0x20;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] &= 0xDF;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] &= 0xDF;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x40) == 0x0) {
			if ((PortB & 0x40) == 0x40) {
				gui.setchckbxBP6(true);
			} else {
				gui.setchckbxBP6(false);
			}
		} else {
			boolean entry = gui.getchckbxBP6();
			if (entry) {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] |= 0x40;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] |= 0x40;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] &= 0xBF;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] &= 0xBF;
				getMem().setRam(RAM);
			}
		}
		if ((TrisB & 0x80) == 0x0) {
			if ((PortB & 0x80) == 0x80) {
				gui.setchckbxBP7(true);
			} else {
				gui.setchckbxBP7(false);
			}
		} else {
			boolean entry = gui.getchckbxBP7();
			if (entry) {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] |= 0x80;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] |= 0x80;
				getMem().setRam(RAM);
			} else {
				int oldValue = RAM[0x6];
				int newValue = RAM[0x6] &= 0x7F;
				if (oldValue != newValue) {
					RAM[0xB] |= 0x1;
				}
				RAM[0x6] &= 0x7F;
				getMem().setRam(RAM);
			}
		}
	}
	
	public void resetPortAB() {
		gui.setchckbxAP0(false);
		gui.setchckbxAP1(false);
		gui.setchckbxAP2(false);
		gui.setchckbxAP3(false);
		gui.setchckbxAP4(false);
		
		gui.setchckbxBP0(false);
		gui.setchckbxBP1(false);
		gui.setchckbxBP2(false);
		gui.setchckbxBP3(false);
		gui.setchckbxBP4(false);
		gui.setchckbxBP5(false);
		gui.setchckbxBP6(false);
		gui.setchckbxBP7(false);
		
	}
	
	public void updateTris() {
		updateTrisA();
		updateTrisB();
	}
	
	public void checkinterrupt() {
		checkinterruptTMR0();
		checkinterruptRB0();
		checkinterruptRB47();
	}
	
	public void checkinterruptTMR0() {
		int[] RAM = getMem().getRam();
		int IntCon = RAM[0xB];
		if ((IntCon & 0xA4) == 0xA4) {
			RAM[0xB] &= 0x7F;
			getMem().setStack(getMem().getAktuellerPC() - 1);
			getMem().setRam(RAM);
			getMem().setPC(0x4);
		}
	}
	
	public void checkinterruptRB0() {
		int[] RAM = getMem().getRam();
		int IntCon = RAM[0xB];
		if ((IntCon & 0x92) == 0x92) {
			RAM[0xB] &= 0x7F;
			getMem().setStack(getMem().getAktuellerPC() - 1);
			getMem().setRam(RAM);
			getMem().setPC(0x4);
		}
	}
	
	public void checkinterruptRB47() {
		int[] RAM = getMem().getRam();
		int IntCon = RAM[0xB];
		if ((IntCon & 0x89) == 0x89) {
			RAM[0xB] &= 0x7F;
			getMem().setStack(getMem().getAktuellerPC() - 1);
			getMem().setRam(RAM);
			getMem().setPC(0x4);
		}
	}
}