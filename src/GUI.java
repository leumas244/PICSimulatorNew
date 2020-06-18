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

public class GUI {

	public JFrame frame;
	public Controller Ctr;
	public JTable ramtable;
	public JTable table;
	private JLabel lblUsedFileValue;
	private DefaultTableModel ramtbl; 


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
		Steuerung.setBounds(1082, 343, 113, 249);
		frame.getContentPane().add(Steuerung);
		Steuerung.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setVerticalAlignment(SwingConstants.BOTTOM);
		btnStart.setBounds(10, 215, 89, 23);
		Steuerung.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.stopProcessor();
			}
		});
		btnStop.setBounds(10, 181, 89, 23);
		Steuerung.add(btnStop);
		
		JPanel lcd = new JPanel();
		lcd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lcd.setBounds(364, 24, 466, 52);
		frame.getContentPane().add(lcd);
		
		JPanel segmentanzeige = new JPanel();
		segmentanzeige.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		segmentanzeige.setBounds(363, 87, 231, 65);
		frame.getContentPane().add(segmentanzeige);
		
		JPanel schalter = new JPanel();
		schalter.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		schalter.setBounds(602, 87, 228, 65);
		frame.getContentPane().add(schalter);
		
		JPanel led = new JPanel();
		led.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		led.setBounds(1082, 24, 113, 128);
		frame.getContentPane().add(led);
		
		JPanel taster = new JPanel();
		taster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		taster.setBounds(841, 24, 78, 128);
		frame.getContentPane().add(taster);
		
		JPanel laufzeit = new JPanel();
		laufzeit.setToolTipText("Laufzeit");
		laufzeit.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		laufzeit.setBounds(1082, 163, 111, 57);
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
		
		
			
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setVerifyInputWhenFocusTarget(false);
		scrollPane.setBounds(301, 229, 771, 363);
		frame.getContentPane().add(scrollPane);
		
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
		scrollPane.setViewportView(table);
		
		JLabel lblUsedFile = new JLabel("Used File:");
		lblUsedFile.setBounds(311, 603, 63, 14);
		frame.getContentPane().add(lblUsedFile);
		
		lblUsedFileValue = new JLabel(" ");
		lblUsedFileValue.setBounds(364, 603, 519, 14);
		frame.getContentPane().add(lblUsedFileValue);
		
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
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ctr.startProcessor();
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
}
