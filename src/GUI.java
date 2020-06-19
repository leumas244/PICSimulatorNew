import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import java.awt.Canvas;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JInternalFrame;
import javax.swing.border.LineBorder;

public class GUI {

	public JFrame frame;
	public Controller Ctr;
	public JTable ramtable;
	public JTable table;
	private JLabel lblUsedFileValue;
	
	private DefaultTableModel ramtbl;
	
	private JLabel lblWRegValue;
	private JLabel lblFSRValue;
	private JLabel lblPCValue;
	private JLabel lblPCLValue;
	private JLabel lblPCLATHValue;
	
	private JLabel lblStatusValue;
	private JLabel lblIRPValue;
	private JLabel lblRP1Value;
	private JLabel lblRP0Value;
	private JLabel lblToValue;
	private JLabel lblPdValue;
	private JLabel lblZValue;
	private JLabel lblDcValue;
	private JLabel lblCValue;
	
	private JLabel lblOptionValue;
	private JLabel lblRBPValue;
	private JLabel lblIntEdgValue;
	private JLabel lblT0CSValue;
	private JLabel lblT0SEValue;
	private JLabel lblPsaValue;
	private JLabel lblPs2Value;
	private JLabel lblPs1Value;
	private JLabel lblPs0Value;
	
	private JLabel lblIntconValue;
	private JLabel lblGIEValue;
	private JLabel lblEIEValue;
	private JLabel lblTIEValue;
	private JLabel lblIEValue;
	private JLabel lblRIEValue;
	private JLabel lblTIFValue;
	private JLabel lblIFValue;
	private JLabel lblRIFValue;
	
	private JLabel lblStack_7Value;
	private JLabel lblStack_6Value;
	private JLabel lblStack_5Value;
	private JLabel lblStack_4Value;
	private JLabel lblStack_3Value;
	private JLabel lblStack_2Value;
	private JLabel lblStack_1Value;
	private JLabel lblStack_0Value;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		Ctr = new Controller(this);
	}

	public void updateVariables(Object[][] newram, String[] columnNames) {
		  DefaultTableModel dtm = new DefaultTableModel(newram, columnNames);
		  dtm.fireTableDataChanged();
		}
	

	/**
	 * Initialize the contents of the frame.
	 */
	
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1221, 722);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel Steuerung = new JPanel();
		Steuerung.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		Steuerung.setBounds(1080, 504, 113, 146);
		frame.getContentPane().add(Steuerung);
		Steuerung.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setVerticalAlignment(SwingConstants.BOTTOM);
		btnStart.setBounds(10, 113, 89, 23);
		Steuerung.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.startProcessor();
			}
		});
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(10, 11, 89, 23);
		Steuerung.add(btnStop);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 79, 89, 23);
		Steuerung.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(10, 45, 89, 23);
		Steuerung.add(btnNewButton_1);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.stopProcessor();
			}
		});
		
		JPanel sfr = new JPanel();
		sfr.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sfr.setBounds(301, 24, 375, 259);
		frame.getContentPane().add(sfr);
		sfr.setLayout(null);
		
		JPanel panelWRegSFR = new JPanel();
		panelWRegSFR.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelWRegSFR.setBounds(10, 75, 104, 117);
		sfr.add(panelWRegSFR);
		panelWRegSFR.setLayout(null);
		
		JLabel lblWReg = new JLabel("W-Register");
		lblWReg.setBounds(8, 7, 54, 14);
		panelWRegSFR.add(lblWReg);
		
		JLabel lblFSR = new JLabel("FSR");
		lblFSR.setBounds(8, 26, 19, 14);
		panelWRegSFR.add(lblFSR);
		
		JLabel lblPC = new JLabel("PC");
		lblPC.setBounds(8, 45, 19, 14);
		panelWRegSFR.add(lblPC);
		
		JLabel lblPCL = new JLabel("PCL");
		lblPCL.setBounds(8, 66, 18, 14);
		panelWRegSFR.add(lblPCL);
		
		JLabel lblPCLath = new JLabel("PCLATH");
		lblPCLath.setBounds(8, 86, 38, 14);
		panelWRegSFR.add(lblPCLath);
		
		lblWRegValue = new JLabel("00");
		lblWRegValue.setBounds(72, 7, 12, 14);
		panelWRegSFR.add(lblWRegValue);
		
		lblFSRValue = new JLabel("00");
		lblFSRValue.setBounds(72, 26, 12, 14);
		panelWRegSFR.add(lblFSRValue);
		
		lblPCLValue = new JLabel("00");
		lblPCLValue.setBounds(72, 66, 12, 14);
		panelWRegSFR.add(lblPCLValue);
		
		lblPCValue = new JLabel("00");
		lblPCValue.setBounds(72, 45, 12, 14);
		panelWRegSFR.add(lblPCValue);
		
		lblPCLATHValue = new JLabel("00");
		lblPCLATHValue.setBounds(72, 86, 12, 14);
		panelWRegSFR.add(lblPCLATHValue);
		
		JPanel panel = new JPanel();
		panel.setBounds(81, 51, 1, 1);
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelWRegSFR.add(panel);
		
		JPanel panelStatus = new JPanel();
		panelStatus.setLayout(null);
		panelStatus.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelStatus.setBounds(124, 4, 241, 79);
		sfr.add(panelStatus);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(10, 11, 46, 14);
		panelStatus.add(lblStatus);
		
		lblStatusValue = new JLabel("00");
		lblStatusValue.setBounds(66, 11, 20, 14);
		panelStatus.add(lblStatusValue);
		
		JLabel lblIRP = new JLabel("IRP");
		lblIRP.setBounds(10, 36, 26, 14);
		panelStatus.add(lblIRP);
		
		JLabel lblRP1 = new JLabel("RP1");
		lblRP1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblRP1.setBounds(40, 36, 26, 14);
		panelStatus.add(lblRP1);
		
		JLabel lblRP0 = new JLabel("RP0");
		lblRP0.setBounds(76, 36, 26, 14);
		panelStatus.add(lblRP0);
		
		JLabel lblTo = new JLabel("TO");
		lblTo.setBounds(106, 36, 26, 14);
		panelStatus.add(lblTo);
		
		JLabel lblPd = new JLabel("PD");
		lblPd.setBounds(135, 36, 26, 14);
		panelStatus.add(lblPd);
		
		JLabel lblZ = new JLabel("Z");
		lblZ.setBounds(161, 36, 26, 14);
		panelStatus.add(lblZ);
		
		JLabel lblDc = new JLabel("DC");
		lblDc.setBounds(187, 36, 26, 14);
		panelStatus.add(lblDc);
		
		JLabel lblC = new JLabel("C");
		lblC.setBounds(213, 36, 26, 14);
		panelStatus.add(lblC);
		
		lblIRPValue = new JLabel("0");
		lblIRPValue.setBounds(10, 61, 26, 14);
		panelStatus.add(lblIRPValue);
		
		lblRP1Value = new JLabel("0");
		lblRP1Value.setBounds(40, 61, 26, 14);
		panelStatus.add(lblRP1Value);
		
		lblRP0Value = new JLabel("0");
		lblRP0Value.setBounds(76, 61, 26, 14);
		panelStatus.add(lblRP0Value);
		
		lblToValue = new JLabel("0");
		lblToValue.setBounds(106, 61, 26, 14);
		panelStatus.add(lblToValue);
		
		lblPdValue = new JLabel("0");
		lblPdValue.setBounds(135, 61, 26, 14);
		panelStatus.add(lblPdValue);
		
		lblZValue = new JLabel("0");
		lblZValue.setBounds(161, 61, 26, 14);
		panelStatus.add(lblZValue);
		
		lblDcValue = new JLabel("0");
		lblDcValue.setBounds(187, 61, 26, 14);
		panelStatus.add(lblDcValue);
		
		lblCValue = new JLabel("0");
		lblCValue.setBounds(213, 61, 26, 14);
		panelStatus.add(lblCValue);
		
		JPanel panelOption = new JPanel();
		panelOption.setLayout(null);
		panelOption.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelOption.setBounds(124, 90, 241, 79);
		sfr.add(panelOption);
		
		JLabel lblOption = new JLabel("Option");
		lblOption.setBounds(10, 11, 46, 14);
		panelOption.add(lblOption);
		
		lblOptionValue = new JLabel("00");
		lblOptionValue.setBounds(66, 11, 20, 14);
		panelOption.add(lblOptionValue);
		
		JLabel lblRBP = new JLabel("RBP");
		lblRBP.setBounds(10, 36, 26, 14);
		panelOption.add(lblRBP);
		
		JLabel lblIntedg = new JLabel("IntEdg");
		lblIntedg.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblIntedg.setBounds(40, 36, 37, 14);
		panelOption.add(lblIntedg);
		
		JLabel lblT0CS = new JLabel("T0CS");
		lblT0CS.setBounds(76, 36, 26, 14);
		panelOption.add(lblT0CS);
		
		JLabel lblT0SE = new JLabel("T0SE");
		lblT0SE.setBounds(106, 36, 26, 14);
		panelOption.add(lblT0SE);
		
		JLabel lblPsa = new JLabel("PSA");
		lblPsa.setBounds(135, 36, 26, 14);
		panelOption.add(lblPsa);
		
		JLabel lblPs2 = new JLabel("PS2");
		lblPs2.setBounds(161, 36, 26, 14);
		panelOption.add(lblPs2);
		
		JLabel lblPs1 = new JLabel("PS1");
		lblPs1.setBounds(187, 36, 26, 14);
		panelOption.add(lblPs1);
		
		JLabel lblPs0 = new JLabel("PS0");
		lblPs0.setBounds(213, 36, 26, 14);
		panelOption.add(lblPs0);
		
		lblRBPValue = new JLabel("0");
		lblRBPValue.setBounds(10, 61, 26, 14);
		panelOption.add(lblRBPValue);
		
		lblIntEdgValue = new JLabel("0");
		lblIntEdgValue.setBounds(40, 61, 26, 14);
		panelOption.add(lblIntEdgValue);
		
		lblT0CSValue = new JLabel("0");
		lblT0CSValue.setBounds(76, 61, 26, 14);
		panelOption.add(lblT0CSValue);
		
		lblT0SEValue = new JLabel("0");
		lblT0SEValue.setBounds(106, 61, 26, 14);
		panelOption.add(lblT0SEValue);
		
		lblPsaValue = new JLabel("0");
		lblPsaValue.setBounds(135, 61, 26, 14);
		panelOption.add(lblPsaValue);
		
		lblPs2Value = new JLabel("0");
		lblPs2Value.setBounds(161, 61, 26, 14);
		panelOption.add(lblPs2Value);
		
		lblPs1Value = new JLabel("0");
		lblPs1Value.setBounds(187, 61, 26, 14);
		panelOption.add(lblPs1Value);
		
		lblPs0Value = new JLabel("0");
		lblPs0Value.setBounds(213, 61, 26, 14);
		panelOption.add(lblPs0Value);
		
		JPanel panelIntCon = new JPanel();
		panelIntCon.setLayout(null);
		panelIntCon.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelIntCon.setBounds(124, 173, 241, 79);
		sfr.add(panelIntCon);
		
		JLabel lblIntcon = new JLabel("Intcon");
		lblIntcon.setBounds(10, 11, 46, 14);
		panelIntCon.add(lblIntcon);
		
		lblIntconValue = new JLabel("00");
		lblIntconValue.setBounds(66, 11, 20, 14);
		panelIntCon.add(lblIntconValue);
		
		JLabel lblGIE = new JLabel("GIE");
		lblGIE.setBounds(10, 36, 26, 14);
		panelIntCon.add(lblGIE);
		
		JLabel lblEIE = new JLabel("EIE");
		lblEIE.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblEIE.setBounds(40, 36, 37, 14);
		panelIntCon.add(lblEIE);
		
		JLabel lblTIE = new JLabel("TIE");
		lblTIE.setBounds(76, 36, 26, 14);
		panelIntCon.add(lblTIE);
		
		JLabel lblIE = new JLabel("IE");
		lblIE.setBounds(106, 36, 26, 14);
		panelIntCon.add(lblIE);
		
		JLabel lblRIE = new JLabel("RIE");
		lblRIE.setBounds(135, 36, 26, 14);
		panelIntCon.add(lblRIE);
		
		JLabel lblTIF = new JLabel("TIF");
		lblTIF.setBounds(161, 36, 26, 14);
		panelIntCon.add(lblTIF);
		
		JLabel lblIF = new JLabel("IF");
		lblIF.setBounds(187, 36, 26, 14);
		panelIntCon.add(lblIF);
		
		JLabel lblRIF = new JLabel("RIF");
		lblRIF.setBounds(213, 36, 26, 14);
		panelIntCon.add(lblRIF);
		
		lblGIEValue = new JLabel("0");
		lblGIEValue.setBounds(10, 61, 26, 14);
		panelIntCon.add(lblGIEValue);
		
		lblEIEValue = new JLabel("0");
		lblEIEValue.setBounds(40, 61, 26, 14);
		panelIntCon.add(lblEIEValue);
		
		lblTIEValue = new JLabel("0");
		lblTIEValue.setBounds(76, 61, 26, 14);
		panelIntCon.add(lblTIEValue);
		
		lblIEValue = new JLabel("0");
		lblIEValue.setBounds(106, 61, 26, 14);
		panelIntCon.add(lblIEValue);
		
		lblRIEValue = new JLabel("0");
		lblRIEValue.setBounds(135, 61, 26, 14);
		panelIntCon.add(lblRIEValue);
		
		lblTIFValue = new JLabel("0");
		lblTIFValue.setBounds(161, 61, 26, 14);
		panelIntCon.add(lblTIFValue);
		
		lblIFValue = new JLabel("0");
		lblIFValue.setBounds(187, 61, 26, 14);
		panelIntCon.add(lblIFValue);
		
		lblRIFValue = new JLabel("0");
		lblRIFValue.setBounds(213, 61, 26, 14);
		panelIntCon.add(lblRIFValue);
		
		JPanel laufzeit = new JPanel();
		laufzeit.setToolTipText("Laufzeit");
		laufzeit.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		laufzeit.setBounds(1082, 333, 111, 57);
		frame.getContentPane().add(laufzeit);
		
		JButton reset_time = new JButton("resettime");
		reset_time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		laufzeit.add(reset_time);
		
		JScrollPane ram = new JScrollPane();
		ram.setInheritsPopupMenu(true);
		ram.setIgnoreRepaint(true);
		ram.setBounds(26, 24, 265, 544);
		frame.getContentPane().add(ram);
		
		
		String[] columnNames = {" ","+0","+1","+2","+3","+4","+5","+6","+7"};
		ramtable = new JTable();
		ramtable.setEnabled(false);
		ramtbl = new DefaultTableModel();
		ramtbl.setColumnIdentifiers(columnNames);
		ramtable.setModel(ramtbl);
		ram.setViewportView(ramtable);
		
		
		JScrollPane FileViewer = new JScrollPane();
		FileViewer.setBackground(Color.WHITE);
		FileViewer.setVerifyInputWhenFocusTarget(false);
		FileViewer.setBounds(301, 287, 771, 363);
		frame.getContentPane().add(FileViewer);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"BP", "ProgramCounter", "ProgramCode", "Row", "Labels", "Comand", "Coment"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setMaxWidth(35);
		table.getColumnModel().getColumn(2).setPreferredWidth(35);
		table.getColumnModel().getColumn(2).setMaxWidth(35);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(3).setMaxWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setMaxWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(130);
		table.getColumnModel().getColumn(5).setMaxWidth(200);
		FileViewer.setViewportView(table);
		
		JLabel lblUsedFile = new JLabel("Used File:");
		lblUsedFile.setBounds(26, 579, 63, 14);
		frame.getContentPane().add(lblUsedFile);
		
		lblUsedFileValue = new JLabel(" ");
		lblUsedFileValue.setBounds(26, 609, 265, 14);
		frame.getContentPane().add(lblUsedFileValue);
		
		JLabel lblSpecialFunctionRegister = new JLabel("Special Function Register");
		lblSpecialFunctionRegister.setBounds(330, 0, 125, 25);
		frame.getContentPane().add(lblSpecialFunctionRegister);
		
		JLabel lblRAM = new JLabel("RAM");
		lblRAM.setBounds(41, 0, 37, 25);
		frame.getContentPane().add(lblRAM);
		
		JPanel stack = new JPanel();
		stack.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		stack.setBounds(686, 24, 77, 196);
		frame.getContentPane().add(stack);
		stack.setLayout(null);
		
		JPanel panelStack = new JPanel();
		panelStack.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelStack.setBounds(10, 11, 57, 174);
		stack.add(panelStack);
		
		lblStack_7Value = new JLabel("0");
		lblStack_7Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_6Value = new JLabel("0");
		lblStack_6Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_5Value = new JLabel("0");
		lblStack_5Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_4Value = new JLabel("0");
		lblStack_4Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_3Value = new JLabel("0");
		lblStack_3Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_2Value = new JLabel("0");
		lblStack_2Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_1Value = new JLabel("0");
		lblStack_1Value.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblStack_0Value = new JLabel("0");
		lblStack_0Value.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panelStack = new GroupLayout(panelStack);
		gl_panelStack.setHorizontalGroup(
			gl_panelStack.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStack.createSequentialGroup()
					.addGroup(gl_panelStack.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelStack.createSequentialGroup()
							.addGap(10)
							.addComponent(lblStack_7Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_6Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_5Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_4Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_3Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_2Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_1Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelStack.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStack_0Value, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelStack.setVerticalGroup(
			gl_panelStack.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelStack.createSequentialGroup()
					.addGap(11)
					.addComponent(lblStack_7Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_6Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_5Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_4Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_3Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_2Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_1Value)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStack_0Value)
					.addGap(9))
		);
		panelStack.setLayout(gl_panelStack);
		
		JLabel lblStack = new JLabel("Stack");
		lblStack.setBounds(695, 5, 46, 14);
		frame.getContentPane().add(lblStack);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JButton btnLoadFile = new JButton("Load File");
		mnFile.add(btnLoadFile);
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.loadFile();

			}
		});
	}
	public void updateRamtable(int x,int y, String value) {
		this.ramtable.getModel().setValueAt(value, y, x+1);
	}
	
	public void addRowToRam(Object[] data) {
		this.ramtbl.addRow(data);
	}

	public DefaultTableModel getRamtbl() {
		return ramtbl;
	}
	
	public void setFileValue(String file) {
		this.lblUsedFileValue.setText(file);
	}
	
	public void updateWReg(String text) {
		this.lblWRegValue.setText(text);
	}
	
	public void updatePC(String text) {
		this.lblPCValue.setText(text);
	}

	public void updateStatus(String text) {
		this.lblStatusValue.setText(text);
	}
	
	public void updateStatusIRP(String text) {
		this.lblIRPValue.setText(text);
	}
	
	public void updateStatusRP1(String text) {
		this.lblRP1Value.setText(text);
	}
	
	public void updateStatusRP0(String text) {
		this.lblRP0Value.setText(text);
	}
	
	public void updateStatusTo(String text) {
		this.lblToValue.setText(text);
	}
	
	public void updateStatusPd(String text) {
		this.lblPdValue.setText(text);
	}
	
	public void updateStatusZ(String text) {
		this.lblZValue.setText(text);
	}
	
	public void updateStatusDc(String text) {
		this.lblDcValue.setText(text);
	}
	
	public void updateStatusC(String text) {
		this.lblCValue.setText(text);
	}
	
	public void updateOption(String text) {
		this.lblOptionValue.setText(text);
	}
	
	public void updateOptionRBP(String text) {
		this.lblRBPValue.setText(text);
	}
	
	public void updateOptionIntEdg(String text) {
		this.lblIntEdgValue.setText(text);
	}
	
	public void updateOptionT0CS(String text) {
		this.lblT0CSValue.setText(text);
	}
	
	public void updateOptionT0SE(String text) {
		this.lblT0SEValue.setText(text);
	}
	
	public void updateOptionPsa(String text) {
		this.lblPsaValue.setText(text);
	}
	
	public void updateOptionPs2(String text) {
		this.lblPs2Value.setText(text);
	}
	
	public void updateOptionPs1(String text) {
		this.lblPs1Value.setText(text);
	}
	
	public void updateOptionPs0(String text) {
		this.lblPs0Value.setText(text);
	}
	
	public void updateIntcon(String text) {
		this.lblIntconValue.setText(text);
	}
	
	public void updateIntconGIE(String text) {
		this.lblGIEValue.setText(text);
	}
	
	public void updateIntconEIE(String text) {
		this.lblEIEValue.setText(text);
	}
	
	public void updateIntconTIE(String text) {
		this.lblTIEValue.setText(text);
	}
	
	public void updateIntconIE(String text) {
		this.lblIEValue.setText(text);
	}
	
	public void updateIntconRIE(String text) {
		this.lblRIEValue.setText(text);
	}
	
	public void updateIntconTIF(String text) {
		this.lblTIFValue.setText(text);
	}
	
	public void updateIntconIF(String text) {
		this.lblIFValue.setText(text);
	}
	
	public void updateIntconRIF(String text) {
		this.lblRIFValue.setText(text);
	}
	
	public void updateFSR(String text) {
		this.lblFSRValue.setText(text);
	}
	
	public void updatePCL(String text) {
		this.lblPCLValue.setText(text);
	}
	
	public void updatePCLATH(String text) {
		this.lblPCLATHValue.setText(text);
	}
	
	public void updateStack_7(String text) {
		this.lblStack_7Value.setText(text);
	}
	
	public void updateStack_6(String text) {
		this.lblStack_6Value.setText(text);
	}
	
	public void updateStack_5(String text) {
		this.lblStack_5Value.setText(text);
	}
	
	public void updateStack_4(String text) {
		this.lblStack_4Value.setText(text);
	}
	
	public void updateStack_3(String text) {
		this.lblStack_3Value.setText(text);
	}
	
	public void updateStack_2(String text) {
		this.lblStack_2Value.setText(text);
	}
	
	public void updateStack_1(String text) {
		this.lblStack_1Value.setText(text);
	}
	
	public void updateStack_0(String text) {
		this.lblStack_0Value.setText(text);
	}
	
}
