package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.Color;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	public JTextField txtComport;
	public JButton btnConnect;
	public JButton btnSend;
	public JCheckBox chckbxRepeat;
	public JSpinner spLeftSpeed;
	public JSpinner spRightSpeed;
	public JLabel lblStatus;
	@SuppressWarnings("rawtypes")
	public JComboBox cmbBotID;
	public JPanel panelStatus;
	public JButton btnTestBots;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		initGUI();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initGUI() {
		setBounds(100, 100, 411, 176);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Device:");
		getContentPane().add(lblNewLabel, "2, 2, right, default");
		
		txtComport = new JTextField();
		this.txtComport.setText("/dev/ttyUSB0");
		getContentPane().add(txtComport, "4, 2, 3, 1, fill, default");
		txtComport.setColumns(10);
		
		this.btnConnect = new JButton("Connect");
		getContentPane().add(this.btnConnect, "8, 2");
		
		JLabel lblNewLabel_1 = new JLabel("Bot ID:");
		getContentPane().add(lblNewLabel_1, "2, 4, right, default");
		
		this.cmbBotID = new JComboBox();
		this.cmbBotID.setModel(new DefaultComboBoxModel(BotNumbers.values()));
		getContentPane().add(this.cmbBotID, "4, 4, 3, 1, fill, default");
		
		btnTestBots = new JButton("Test Bots");
		getContentPane().add(btnTestBots, "8, 4");
		
		JLabel lblNewLabel_2 = new JLabel("Left speed:");
		getContentPane().add(lblNewLabel_2, "2, 6, right, default");
		
		this.spLeftSpeed = new JSpinner();
		this.spLeftSpeed.setModel(new SpinnerNumberModel(0, -100, 100, 10));
		getContentPane().add(this.spLeftSpeed, "4, 6");
		
		JLabel lblNewLabel_3 = new JLabel("Right speed:");
		getContentPane().add(lblNewLabel_3, "6, 6, right, default");
		
		this.spRightSpeed = new JSpinner();
		this.spRightSpeed.setModel(new SpinnerNumberModel(0, -100, 100, 10));
		getContentPane().add(this.spRightSpeed, "8, 6");
		
		panelStatus = new JPanel();
		panelStatus.setBackground(Color.RED);
		getContentPane().add(panelStatus, "4, 8, fill, fill");
		
		this.chckbxRepeat = new JCheckBox("repeat");
		chckbxRepeat.setEnabled(false);
		getContentPane().add(this.chckbxRepeat, "6, 8");
		
		this.btnSend = new JButton("Send");
		getContentPane().add(this.btnSend, "8, 8");
		
		this.lblStatus = new JLabel("Status: Not connected");
		this.lblStatus.setForeground(new Color(128, 128, 128));
		getContentPane().add(this.lblStatus, "2, 10, 7, 1");
	}
}
