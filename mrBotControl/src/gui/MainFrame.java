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
	public JButton btnForeward;
	public JButton btnBackward;
	public JButton btnSpinCW;
	public JButton btnSpinCCW;

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
		setBounds(100, 100, 451, 238);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;default)"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
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
		this.spLeftSpeed.setModel(new SpinnerNumberModel(0, -31, 31, 5));
		getContentPane().add(this.spLeftSpeed, "4, 6");
		
		this.btnSend = new JButton("Send");
		getContentPane().add(this.btnSend, "8, 6, 1, 3");
		
		JLabel lblNewLabel_3 = new JLabel("Right speed:");
		getContentPane().add(lblNewLabel_3, "2, 8, right, default");
		
		this.spRightSpeed = new JSpinner();
		this.spRightSpeed.setModel(new SpinnerNumberModel(0, -31, 31, 5));
		getContentPane().add(this.spRightSpeed, "4, 8");
		
		panelStatus = new JPanel();
		panelStatus.setBackground(Color.RED);
		getContentPane().add(panelStatus, "6, 6, 1, 3, fill, fill");
		
		this.chckbxRepeat = new JCheckBox("repeat");
		chckbxRepeat.setEnabled(false);
		getContentPane().add(this.chckbxRepeat, "8, 10");
		
		btnForeward = new JButton("Foreward");
		getContentPane().add(btnForeward, "2, 12");
		
		btnBackward = new JButton("Backward");
		getContentPane().add(btnBackward, "4, 12");
		
		btnSpinCW = new JButton("Spin CW");
		getContentPane().add(btnSpinCW, "6, 12");
		
		btnSpinCCW = new JButton("Spin CCW");
		getContentPane().add(btnSpinCCW, "8, 12");
		
		this.lblStatus = new JLabel("Status: Not connected");
		this.lblStatus.setForeground(new Color(128, 128, 128));
		getContentPane().add(this.lblStatus, "2, 14, 7, 1");
	}
}
