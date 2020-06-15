import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Component;
import javax.swing.table.DefaultTableModel;

public class GUI {

	public JFrame frame;
	public Controller Ctr;
	public JTable table;
	public JLabel lblUsedFileValue;

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

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1076, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel RAM = new JPanel();
		RAM.setBounds(26, 24, 191, 196);
		frame.getContentPane().add(RAM);
		RAM.setLayout(null);
		
		JPanel Spezialfunktionsregister = new JPanel();
		Spezialfunktionsregister.setBounds(36, 231, 191, 363);
		frame.getContentPane().add(Spezialfunktionsregister);
		
		JLabel lbWReg = new JLabel("W-Register");
		lbWReg.setHorizontalAlignment(SwingConstants.LEFT);
		Spezialfunktionsregister.add(lbWReg);
		
		JLabel lblWRegValue = new JLabel(" ");
		Spezialfunktionsregister.add(lblWRegValue);
		
		JPanel Register = new JPanel();
		Register.setBounds(227, 24, 127, 196);
		frame.getContentPane().add(Register);
		
		JPanel Steuerung = new JPanel();
		Steuerung.setBounds(949, 229, 101, 249);
		frame.getContentPane().add(Steuerung);
		Steuerung.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setVerticalAlignment(SwingConstants.BOTTOM);
		btnStart.setBounds(5, 215, 89, 23);
		Steuerung.add(btnStart);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(840, 229, 96, 248);
		frame.getContentPane().add(panel_5);
		
		JPanel LCD = new JPanel();
		LCD.setBounds(364, 24, 466, 52);
		frame.getContentPane().add(LCD);
		
		JPanel Segmentanzeige = new JPanel();
		Segmentanzeige.setBounds(363, 87, 231, 65);
		frame.getContentPane().add(Segmentanzeige);
		
		JPanel Schalter = new JPanel();
		Schalter.setBounds(602, 88, 228, 62);
		frame.getContentPane().add(Schalter);
		
		JPanel LED = new JPanel();
		LED.setBounds(937, 24, 113, 124);
		frame.getContentPane().add(LED);
		
		JPanel Taster = new JPanel();
		Taster.setBounds(841, 24, 78, 121);
		frame.getContentPane().add(Taster);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setVerifyInputWhenFocusTarget(false);
		scrollPane.setBounds(227, 229, 602, 363);
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
		lblUsedFile.setBounds(227, 603, 63, 14);
		frame.getContentPane().add(lblUsedFile);
		
		JLabel lblUsedFileValue = new JLabel(" ");
		lblUsedFileValue.setBounds(287, 603, 543, 14);
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
				String filename = Ctr.Mem.getFilename();
				lblUsedFileValue.setText(filename);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}
	
	public void setlblUsedFileValue(String text) {
		lblUsedFileValue.setText("Resdt");
	}
}
