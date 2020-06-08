import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private Controller Ctr;

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
		initialize();
		Ctr = new Controller(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1076, 689);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel RAM = new JPanel();
		RAM.setBounds(26, 24, 191, 196);
		frame.getContentPane().add(RAM);
		RAM.setLayout(null);
		
		JPanel Spezialfunktionsregister = new JPanel();
		Spezialfunktionsregister.setBounds(26, 229, 191, 363);
		frame.getContentPane().add(Spezialfunktionsregister);
		
		JPanel Register = new JPanel();
		Register.setBounds(227, 24, 127, 196);
		frame.getContentPane().add(Register);
		
		JPanel Text = new JPanel();
		Text.setBounds(226, 228, 604, 364);
		frame.getContentPane().add(Text);
		
		JPanel Steuerung = new JPanel();
		Steuerung.setBounds(949, 229, 101, 249);
		frame.getContentPane().add(Steuerung);
		
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
