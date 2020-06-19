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
		Steuerung.setBounds(1082, 401, 113, 249);
		frame.getContentPane().add(Steuerung);
		Steuerung.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setVerticalAlignment(SwingConstants.BOTTOM);
		btnStart.setBounds(10, 215, 89, 23);
		Steuerung.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.startProcessor();
			}
		});
		
		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(10, 181, 89, 23);
		Steuerung.add(btnStop);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.stopProcessor();
			}
		});
		
		JPanel sfr = new JPanel();
		sfr.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		sfr.setBounds(301, 24, 393, 252);
		frame.getContentPane().add(sfr);
		sfr.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(0, 0, 112, 178);
		sfr.add(panel_1);
		
		JLabel lblWReg = new JLabel("W-Register");
		panel_1.add(lblWReg);
		
		JLabel lblFSR = new JLabel("FSR");
		panel_1.add(lblFSR);
		
		JLabel lblPC = new JLabel("PC");
		panel_1.add(lblPC);
		
		JLabel lblPCL = new JLabel("PCL");
		panel_1.add(lblPCL);
		
		JLabel lblPCLath = new JLabel("PCLATH");
		panel_1.add(lblPCLath);
		
		lblWRegValue = new JLabel("00");
		panel_1.add(lblWRegValue);
		
		lblFSRValue = new JLabel("00");
		panel_1.add(lblFSRValue);
		
		lblPCValue = new JLabel("00");
		panel_1.add(lblPCValue);
		
		lblPCLValue = new JLabel("00");
		panel_1.add(lblPCLValue);
		
		lblPCLATHValue = new JLabel("00");
		panel_1.add(lblPCLATHValue);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(110, 0, 246, 93);
		sfr.add(panel_2);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(10, 11, 46, 14);
		panel_2.add(lblStatus);
		
		lblStatusValue = new JLabel("00");
		lblStatusValue.setBounds(66, 11, 20, 14);
		panel_2.add(lblStatusValue);
		
		JLabel lblIRP = new JLabel("IRP");
		lblIRP.setBounds(10, 36, 26, 14);
		panel_2.add(lblIRP);
		
		JLabel lblRP1 = new JLabel("RP1");
		lblRP1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblRP1.setBounds(40, 36, 26, 14);
		panel_2.add(lblRP1);
		
		JLabel lblRP0 = new JLabel("RP0");
		lblRP0.setBounds(76, 36, 26, 14);
		panel_2.add(lblRP0);
		
		JLabel lblTo = new JLabel("TO");
		lblTo.setBounds(106, 36, 26, 14);
		panel_2.add(lblTo);
		
		JLabel lblPd = new JLabel("PD");
		lblPd.setBounds(135, 36, 26, 14);
		panel_2.add(lblPd);
		
		JLabel lblZ = new JLabel("Z");
		lblZ.setBounds(161, 36, 26, 14);
		panel_2.add(lblZ);
		
		JLabel lblDc = new JLabel("DC");
		lblDc.setBounds(187, 36, 26, 14);
		panel_2.add(lblDc);
		
		JLabel lblC = new JLabel("C");
		lblC.setBounds(213, 36, 26, 14);
		panel_2.add(lblC);
		
		lblIRPValue = new JLabel("0");
		lblIRPValue.setBounds(10, 61, 26, 14);
		panel_2.add(lblIRPValue);
		
		lblRP1Value = new JLabel("0");
		lblRP1Value.setBounds(40, 61, 26, 14);
		panel_2.add(lblRP1Value);
		
		lblRP0Value = new JLabel("0");
		lblRP0Value.setBounds(76, 61, 26, 14);
		panel_2.add(lblRP0Value);
		
		lblToValue = new JLabel("0");
		lblToValue.setBounds(106, 61, 26, 14);
		panel_2.add(lblToValue);
		
		lblPdValue = new JLabel("0");
		lblPdValue.setBounds(135, 61, 26, 14);
		panel_2.add(lblPdValue);
		
		lblZValue = new JLabel("0");
		lblZValue.setBounds(161, 61, 26, 14);
		panel_2.add(lblZValue);
		
		lblDcValue = new JLabel("0");
		lblDcValue.setBounds(187, 61, 26, 14);
		panel_2.add(lblDcValue);
		
		lblCValue = new JLabel("0");
		lblCValue.setBounds(213, 61, 26, 14);
		panel_2.add(lblCValue);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(110, 93, 246, 88);
		sfr.add(panel_3);
		
		JLabel lblOption = new JLabel("Option");
		lblOption.setBounds(10, 11, 46, 14);
		panel_3.add(lblOption);
		
		lblOptionValue = new JLabel("00");
		lblOptionValue.setBounds(66, 11, 20, 14);
		panel_3.add(lblOptionValue);
		
		JLabel lblRBP = new JLabel("RBP");
		lblRBP.setBounds(10, 36, 26, 14);
		panel_3.add(lblRBP);
		
		JLabel lblIntedg = new JLabel("IntEdg");
		lblIntedg.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblIntedg.setBounds(40, 36, 37, 14);
		panel_3.add(lblIntedg);
		
		JLabel lblT0CS = new JLabel("T0CS");
		lblT0CS.setBounds(76, 36, 26, 14);
		panel_3.add(lblT0CS);
		
		JLabel lblT0SE = new JLabel("T0SE");
		lblT0SE.setBounds(106, 36, 26, 14);
		panel_3.add(lblT0SE);
		
		JLabel lblPsa = new JLabel("PSA");
		lblPsa.setBounds(135, 36, 26, 14);
		panel_3.add(lblPsa);
		
		JLabel lblPs2 = new JLabel("PS2");
		lblPs2.setBounds(161, 36, 26, 14);
		panel_3.add(lblPs2);
		
		JLabel lblPs1 = new JLabel("PS1");
		lblPs1.setBounds(187, 36, 26, 14);
		panel_3.add(lblPs1);
		
		JLabel lblPs0 = new JLabel("PS0");
		lblPs0.setBounds(213, 36, 26, 14);
		panel_3.add(lblPs0);
		
		lblRBPValue = new JLabel("0");
		lblRBPValue.setBounds(10, 61, 26, 14);
		panel_3.add(lblRBPValue);
		
		lblIntEdgValue = new JLabel("0");
		lblIntEdgValue.setBounds(40, 61, 26, 14);
		panel_3.add(lblIntEdgValue);
		
		lblT0CSValue = new JLabel("0");
		lblT0CSValue.setBounds(76, 61, 26, 14);
		panel_3.add(lblT0CSValue);
		
		lblT0SEValue = new JLabel("0");
		lblT0SEValue.setBounds(106, 61, 26, 14);
		panel_3.add(lblT0SEValue);
		
		lblPsaValue = new JLabel("0");
		lblPsaValue.setBounds(135, 61, 26, 14);
		panel_3.add(lblPsaValue);
		
		lblPs2Value = new JLabel("0");
		lblPs2Value.setBounds(161, 61, 26, 14);
		panel_3.add(lblPs2Value);
		
		lblPs1Value = new JLabel("0");
		lblPs1Value.setBounds(187, 61, 26, 14);
		panel_3.add(lblPs1Value);
		
		lblPs0Value = new JLabel("0");
		lblPs0Value.setBounds(213, 61, 26, 14);
		panel_3.add(lblPs0Value);
		
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
				"ProgramCounter", "ProgramCode", "Row", "Labels", "Comand", "Coment"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setMaxWidth(35);
		table.getColumnModel().getColumn(2).setPreferredWidth(45);
		table.getColumnModel().getColumn(2).setMaxWidth(45);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(130);
		table.getColumnModel().getColumn(4).setMaxWidth(200);
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
		
		JPanel led = new JPanel();
		led.setLayout(null);
		led.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		led.setBounds(937, 160, 135, 116);
		frame.getContentPane().add(led);
		
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
	
	public void updateFSR(String text) {
		this.lblFSRValue.setText(text);
	}
	
	public void updatePCL(String text) {
		this.lblPCLValue.setText(text);
	}
	
	public void updatePCLATH(String text) {
		this.lblPCLATHValue.setText(text);
	}
}
