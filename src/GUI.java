import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class GUI {

	private JFrame frame;
	private Controller Ctr;
	private JTable pinreg;
	private JTable programcode;
	public JTable ramtable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		Ctr = new Controller(this);
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1076, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel spezialfunktionsregister = new JPanel();
		spezialfunktionsregister.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		spezialfunktionsregister.setBounds(26, 229, 191, 363);
		frame.getContentPane().add(spezialfunktionsregister);
		spezialfunktionsregister.setLayout(new BorderLayout(0, 0));
		
		JPanel pins = new JPanel();
		pins.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pins.setBounds(227, 24, 127, 196);
		frame.getContentPane().add(pins);
		
		pinreg = new JTable();
		pins.add(pinreg);
		
		JPanel text = new JPanel();
		text.setBorder(new CompoundBorder());
		text.setBounds(227, 229, 604, 364);
		frame.getContentPane().add(text);
		text.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		text.add(scrollPane_1);
		
		programcode = new JTable();
		programcode.setToolTipText("Programmcode");
		text.add(programcode);
		
		JPanel steuerung = new JPanel();
		steuerung.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		steuerung.setBounds(949, 229, 101, 249);
		frame.getContentPane().add(steuerung);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_5.setBounds(840, 229, 96, 248);
		frame.getContentPane().add(panel_5);
		
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
		led.setBounds(937, 24, 113, 128);
		frame.getContentPane().add(led);
		
		JPanel taster = new JPanel();
		taster.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		taster.setBounds(841, 24, 78, 128);
		frame.getContentPane().add(taster);
		
		JPanel laufzeit = new JPanel();
		laufzeit.setToolTipText("Laufzeit");
		laufzeit.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		laufzeit.setBounds(949, 163, 101, 57);
		frame.getContentPane().add(laufzeit);
		
		JButton reset_time = new JButton("resettime");
		reset_time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		laufzeit.add(reset_time);
		
		JScrollPane ram = new JScrollPane();
		ram.setBounds(26, 24, 191, 194);
		frame.getContentPane().add(ram);
		
		
		String[] columnNames = {"0","1","2","3","4","5","6","7"};
		Object[][] newram = Ctr.ramUmwandeln();
		ramtable = new JTable(newram, columnNames);
		ram.setViewportView(ramtable);
		
			
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JButton btnLoadFile = new JButton("Load File");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		mnFile.add(btnLoadFile);
	}
}
