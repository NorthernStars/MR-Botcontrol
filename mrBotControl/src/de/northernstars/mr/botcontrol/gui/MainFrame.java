package de.northernstars.mr.botcontrol.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.hanneseilers.jserial.core.Baudrates;
import de.hanneseilers.jserial.core.DataBits;
import de.hanneseilers.jserial.core.Parity;
import de.hanneseilers.jserial.core.StopBits;
import de.northernstars.mr.botcontrol.core.interfaces.GuiFrameListener;
import de.northernstars.mr.botcontrol.core.protocol.ProtocolVersions;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.JTabbedPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.northernstars.mr.botcontrol.core.protocol.LEDAnimations;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JCheckBox;

/**
 * Main frame GUI.
 * @author Hannes Eilers
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements WindowListener {
	
	private GuiFrameListener mListener; 

	private JPanel contentPane;
	public JMenuBar menuBar;
	public JMenu mnFile;
	public JMenuItem mntmQuit;
	public JMenuItem mntmConnect;
	public JMenuItem mntmDisconnect;
	public JMenuItem mntmReconnect;
	public JTabbedPane tabbedPane;
	public JPanel panelSettings;
	public JPanel panelBotControl;
	public JLabel lblDevice;
	public JButton btnConnect;
	public JComboBox<String> cmbDevices;
	public JTextField txtDevice;
	public JLabel lblBaudrate;
	public JComboBox<Baudrates> cmbBaudrate;
	public JLabel lblSerialLibrary;
	public JComboBox<String> cmbSerialLibraries;
	public JLabel lblData;
	public JComboBox<DataBits> cmbDataBits;
	public JLabel lblStopBits;
	public JComboBox<StopBits> cmbStopBits;
	public JLabel lblParity;
	public JComboBox<Parity> cmbParity;
	public JLabel lblStatusInfo;
	public JLabel lblStatus;
	public JLabel lblProtocolVersion;
	public JComboBox<ProtocolVersions> cmbProtocolVersion;
	private JLabel lblBotId;
	public JTextField txtBotID;
	private JLabel lblChangeBotIDTo;
	public JTextField txtBotIDChangeTo;
	public JButton btnChangeBotID;
	public JButton btnTestBots;
	private JLabel lblLeftSpeed;
	private JLabel lblRightSpeed;
	public JTextField txtMotorLeft;
	public JTextField txtMotorRight;
	public JButton btnMotorsStop;
	public JButton btnMotorLeftForeward;
	public JButton btnMotorRightForeward;
	public JButton btnMotorLeftBackward;
	public JButton btnMotorRightBackward;
	public JButton btnMotorsForeward;
	public JButton btnMotorsBackward;
	private JLabel lblBothMotors;
	public JButton btnLEDStatus;
	public JButton btnLEDAllOn;
	private JLabel lblLEDAnimation;
	public JComboBox<LEDAnimations> cmbLEDAnimation;
	private JPanel panel_6;
	private JLabel lblLEDColorRed;
	private JLabel lblLEDColorGreen;
	private JLabel lblLEDColorBlue;
	private JLabel lblLED1;
	private JLabel lblLED2;
	private JLabel lblLED3;
	private JLabel lblLED4;
	public JTextField txtLED1Red;
	public JTextField txtLED1Green;
	public JTextField txtLED1Blue;
	public JTextField txtLED2Red;
	public JTextField txtLED2Green;
	public JTextField txtLED2Blue;
	public JTextField txtLED3Red;
	public JTextField txtLED3Green;
	public JTextField txtLED3Blue;
	public JTextField txtLED4Red;
	public JTextField txtLED4Green;
	public JTextField txtLED4Blue;
	public JButton btnLED1;
	public JButton btnLED2;
	public JButton btnLED3;
	public JButton btnLED4;
	public JButton btnRefresh;
	public JLabel lblTestBotsStatus;
	public JPanel panelTest;
	private JLabel lblQuickTestTitle;
	private JLabel lblNormalTestTitle;
	private JLabel lblCompetitionTestTitle;
	public JButton btnQuickTest;
	public JButton btnNormalTest;
	public JButton btnCompetitionTestTest;
	private JTextPane txtpnQuickSeconds;
	private JTextPane txtpnTestWithIncreasing;
	private JTextPane txtpnSimulationTestOf;
	public JLabel lblTestStatus;
	public JButton btnStopTest;
	public JCheckBox chkAutoStop;
	private JLabel lblMaxSpeed;
	public JTextField txtMaxSpeed;

	/**
	 * Launch the gui.
	 */
	public static void showMainFrame(final GuiFrameListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(listener);
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
	public MainFrame(GuiFrameListener listener) {
		mListener = listener;
		
		setTitle("MRBotControl");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 769, 600);
		
		addWindowListener(this);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmConnect = new JMenuItem("Connect");
		mntmConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmConnect);
		
		mntmReconnect = new JMenuItem("Reconnect");
		mntmReconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnFile.add(mntmReconnect);
		
		mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		mnFile.add(mntmDisconnect);
		
		mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmQuit);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		panelSettings = new JPanel();
		tabbedPane.addTab("Settings", null, panelSettings, null);
		panelSettings.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSettings.add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(56dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
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
		
		lblSerialLibrary = new JLabel("Library:");
		lblSerialLibrary.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblSerialLibrary, "1, 1, right, default");
		
		cmbSerialLibraries = new JComboBox<String>();
		panel.add(cmbSerialLibraries, "3, 1, 5, 1, fill, default");
		
		lblDevice = new JLabel("Device:");
		lblDevice.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblDevice, "1, 3, right, default");
		
		cmbDevices = new JComboBox<String>();
		panel.add(cmbDevices, "3, 3, 5, 1, fill, default");
		
		btnConnect = new JButton("Connect");
		btnConnect.setMnemonic('C');
		panel.add(btnConnect, "11, 1, 1, 7");
		getRootPane().setDefaultButton(btnConnect);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setMnemonic('R');
		panel.add(btnRefresh, "9, 1, 1, 3");
		
		txtDevice = new JTextField();
		panel.add(txtDevice, "3, 5, 7, 1, fill, default");
		txtDevice.setColumns(10);
		
		lblBaudrate = new JLabel("Baudrate:");
		lblBaudrate.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblBaudrate, "1, 7, right, default");
		
		cmbBaudrate = new JComboBox<Baudrates>();
		cmbBaudrate.setModel(new DefaultComboBoxModel<Baudrates>(Baudrates.values()));
		cmbBaudrate.setSelectedIndex(12);
		panel.add(cmbBaudrate, "3, 7, fill, default");
		
		lblParity = new JLabel("Parity:");
		lblParity.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblParity, "5, 7, right, default");
		
		cmbParity = new JComboBox<Parity>();
		cmbParity.setModel(new DefaultComboBoxModel<Parity>(Parity.values()));
		cmbParity.setSelectedIndex(0);
		panel.add(cmbParity, "7, 7, fill, default");
		
		lblData = new JLabel("Data:");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblData, "1, 9, right, default");
		
		cmbDataBits = new JComboBox<DataBits>();
		cmbDataBits.setModel(new DefaultComboBoxModel<DataBits>(DataBits.values()));
		cmbDataBits.setSelectedIndex(3);
		panel.add(cmbDataBits, "3, 9, fill, default");
		
		lblStopBits = new JLabel("Stop bits:");
		lblStopBits.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblStopBits, "5, 9, right, default");
		
		cmbStopBits = new JComboBox<StopBits>();
		cmbStopBits.setModel(new DefaultComboBoxModel<StopBits>(StopBits.values()));
		cmbStopBits.setSelectedIndex(0);
		panel.add(cmbStopBits, "7, 9, fill, default");
		
		lblProtocolVersion = new JLabel("Protocol version:");
		panel.add(lblProtocolVersion, "1, 11, right, default");
		
		cmbProtocolVersion = new JComboBox<ProtocolVersions>();
		cmbProtocolVersion.setEnabled(false);
		cmbProtocolVersion.setModel(new DefaultComboBoxModel<ProtocolVersions>(ProtocolVersions.values()));
		panel.add(cmbProtocolVersion, "3, 11, fill, default");
		
		lblStatusInfo = new JLabel("Status:");
		lblStatusInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblStatusInfo, "1, 13");
		
		lblStatus = new JLabel("Disconnected");
		panel.add(lblStatus, "3, 13, 7, 1");
		
		panelBotControl = new JPanel();
		tabbedPane.addTab("BotControl", null, panelBotControl, null);
		panelBotControl.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JPanel panelBotControlBotID = new JPanel();
		panelBotControlBotID.setBorder(new TitledBorder(null, "Bot", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBotControl.add(panelBotControlBotID, "2, 2, fill, fill");
		GridBagLayout gbl_panelBotControlBotID = new GridBagLayout();
		gbl_panelBotControlBotID.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelBotControlBotID.rowHeights = new int[]{0, 0};
		gbl_panelBotControlBotID.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelBotControlBotID.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelBotControlBotID.setLayout(gbl_panelBotControlBotID);
		
		lblBotId = new JLabel("Bot ID:");
		lblBotId.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblBotId = new GridBagConstraints();
		gbc_lblBotId.insets = new Insets(0, 0, 0, 5);
		gbc_lblBotId.anchor = GridBagConstraints.EAST;
		gbc_lblBotId.gridx = 0;
		gbc_lblBotId.gridy = 0;
		panelBotControlBotID.add(lblBotId, gbc_lblBotId);
		
		txtBotID = new JTextField();
		txtBotID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtBotID.selectAll();
			}
		});
		txtBotID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				txtBotIDChangeTo.setText(txtBotID.getText());
			}
		});
		txtBotID.setHorizontalAlignment(SwingConstants.CENTER);
		txtBotID.setText("0");
		GridBagConstraints gbc_txtBotID = new GridBagConstraints();
		gbc_txtBotID.insets = new Insets(0, 0, 0, 5);
		gbc_txtBotID.gridx = 1;
		gbc_txtBotID.gridy = 0;
		panelBotControlBotID.add(txtBotID, gbc_txtBotID);
		txtBotID.setColumns(10);
		
		lblChangeBotIDTo = new JLabel("change to:");
		GridBagConstraints gbc_lblChangeBotIDTo = new GridBagConstraints();
		gbc_lblChangeBotIDTo.insets = new Insets(0, 0, 0, 5);
		gbc_lblChangeBotIDTo.anchor = GridBagConstraints.EAST;
		gbc_lblChangeBotIDTo.gridx = 2;
		gbc_lblChangeBotIDTo.gridy = 0;
		panelBotControlBotID.add(lblChangeBotIDTo, gbc_lblChangeBotIDTo);
		
		txtBotIDChangeTo = new JTextField();
		txtBotIDChangeTo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtBotIDChangeTo.selectAll();
			}
		});
		txtBotIDChangeTo.setHorizontalAlignment(SwingConstants.CENTER);
		txtBotIDChangeTo.setText("0");
		GridBagConstraints gbc_txtBotIDChangeTo = new GridBagConstraints();
		gbc_txtBotIDChangeTo.insets = new Insets(0, 0, 0, 5);
		gbc_txtBotIDChangeTo.gridx = 3;
		gbc_txtBotIDChangeTo.gridy = 0;
		panelBotControlBotID.add(txtBotIDChangeTo, gbc_txtBotIDChangeTo);
		txtBotIDChangeTo.setColumns(10);
		
		btnChangeBotID = new JButton("Change");
		GridBagConstraints gbc_btnChangeBotID = new GridBagConstraints();
		gbc_btnChangeBotID.insets = new Insets(0, 0, 0, 5);
		gbc_btnChangeBotID.gridx = 4;
		gbc_btnChangeBotID.gridy = 0;
		panelBotControlBotID.add(btnChangeBotID, gbc_btnChangeBotID);
		
		chkAutoStop = new JCheckBox("Auto Stop");
		GridBagConstraints gbc_chkAutoStop = new GridBagConstraints();
		gbc_chkAutoStop.insets = new Insets(0, 0, 0, 5);
		gbc_chkAutoStop.gridx = 5;
		gbc_chkAutoStop.gridy = 0;
		panelBotControlBotID.add(chkAutoStop, gbc_chkAutoStop);
		
		JPanel panelBotControlMotors = new JPanel();
		panelBotControlMotors.setBorder(new TitledBorder(null, "Motors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBotControl.add(panelBotControlMotors, "2, 4, fill, fill");
		GridBagLayout gbl_panelBotControlMotors = new GridBagLayout();
		gbl_panelBotControlMotors.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelBotControlMotors.rowHeights = new int[]{0, 40, 40, 40, 0};
		gbl_panelBotControlMotors.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelBotControlMotors.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelBotControlMotors.setLayout(gbl_panelBotControlMotors);
		
		lblLeftSpeed = new JLabel("Left Speed");
		GridBagConstraints gbc_lblLeftSpeed = new GridBagConstraints();
		gbc_lblLeftSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftSpeed.gridx = 0;
		gbc_lblLeftSpeed.gridy = 0;
		panelBotControlMotors.add(lblLeftSpeed, gbc_lblLeftSpeed);
		
		lblRightSpeed = new JLabel("Right Speed");
		GridBagConstraints gbc_lblRightSpeed = new GridBagConstraints();
		gbc_lblRightSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightSpeed.gridx = 1;
		gbc_lblRightSpeed.gridy = 0;
		panelBotControlMotors.add(lblRightSpeed, gbc_lblRightSpeed);
		
		lblBothMotors = new JLabel("Both motors");
		GridBagConstraints gbc_lblBothMotors = new GridBagConstraints();
		gbc_lblBothMotors.insets = new Insets(0, 0, 5, 5);
		gbc_lblBothMotors.gridx = 2;
		gbc_lblBothMotors.gridy = 0;
		panelBotControlMotors.add(lblBothMotors, gbc_lblBothMotors);
		
		txtMotorLeft = new JTextField();
		txtMotorLeft.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMotorLeft.selectAll();
			}
		});
		txtMotorLeft.setText("0");
		txtMotorLeft.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtMotorLeft = new GridBagConstraints();
		gbc_txtMotorLeft.insets = new Insets(0, 0, 5, 5);
		gbc_txtMotorLeft.fill = GridBagConstraints.BOTH;
		gbc_txtMotorLeft.gridx = 0;
		gbc_txtMotorLeft.gridy = 1;
		panelBotControlMotors.add(txtMotorLeft, gbc_txtMotorLeft);
		txtMotorLeft.setColumns(10);
		
		txtMotorRight = new JTextField();
		txtMotorRight.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtMotorRight.selectAll();
			}
		});
		txtMotorRight.setText("0");
		txtMotorRight.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtMotorRight = new GridBagConstraints();
		gbc_txtMotorRight.insets = new Insets(0, 0, 5, 5);
		gbc_txtMotorRight.fill = GridBagConstraints.BOTH;
		gbc_txtMotorRight.gridx = 1;
		gbc_txtMotorRight.gridy = 1;
		panelBotControlMotors.add(txtMotorRight, gbc_txtMotorRight);
		txtMotorRight.setColumns(10);
		
		btnMotorsStop = new JButton("Stop");
		btnMotorsStop.setMnemonic('S');
		GridBagConstraints gbc_btnMotorsStop = new GridBagConstraints();
		gbc_btnMotorsStop.fill = GridBagConstraints.BOTH;
		gbc_btnMotorsStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnMotorsStop.gridx = 2;
		gbc_btnMotorsStop.gridy = 1;
		panelBotControlMotors.add(btnMotorsStop, gbc_btnMotorsStop);
		
		btnTestBots = new JButton("Test First 30 Bots");
		btnTestBots.setMnemonic('T');
		GridBagConstraints gbc_btnTestBots = new GridBagConstraints();
		gbc_btnTestBots.insets = new Insets(0, 0, 5, 5);
		gbc_btnTestBots.fill = GridBagConstraints.BOTH;
		gbc_btnTestBots.gridheight = 2;
		gbc_btnTestBots.gridx = 3;
		gbc_btnTestBots.gridy = 1;
		panelBotControlMotors.add(btnTestBots, gbc_btnTestBots);
		
		lblMaxSpeed = new JLabel("Max Speed:");
		GridBagConstraints gbc_lblMaxSpeed = new GridBagConstraints();
		gbc_lblMaxSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblMaxSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxSpeed.gridx = 4;
		gbc_lblMaxSpeed.gridy = 1;
		panelBotControlMotors.add(lblMaxSpeed, gbc_lblMaxSpeed);
		
		txtMaxSpeed = new JTextField();
		txtMaxSpeed.setText("100");
		GridBagConstraints gbc_txtMaxSpeed = new GridBagConstraints();
		gbc_txtMaxSpeed.insets = new Insets(0, 0, 5, 0);
		gbc_txtMaxSpeed.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxSpeed.gridx = 5;
		gbc_txtMaxSpeed.gridy = 1;
		panelBotControlMotors.add(txtMaxSpeed, gbc_txtMaxSpeed);
		txtMaxSpeed.setColumns(10);
		
		btnMotorLeftForeward = new JButton("Foreward");
		GridBagConstraints gbc_btnMotorLeftForeward = new GridBagConstraints();
		gbc_btnMotorLeftForeward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorLeftForeward.insets = new Insets(0, 0, 5, 5);
		gbc_btnMotorLeftForeward.gridx = 0;
		gbc_btnMotorLeftForeward.gridy = 2;
		panelBotControlMotors.add(btnMotorLeftForeward, gbc_btnMotorLeftForeward);
		
		btnMotorRightForeward = new JButton("Foreward");
		GridBagConstraints gbc_btnMotorRightForeward = new GridBagConstraints();
		gbc_btnMotorRightForeward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorRightForeward.insets = new Insets(0, 0, 5, 5);
		gbc_btnMotorRightForeward.gridx = 1;
		gbc_btnMotorRightForeward.gridy = 2;
		panelBotControlMotors.add(btnMotorRightForeward, gbc_btnMotorRightForeward);
		
		btnMotorsForeward = new JButton("Foreward");
		btnMotorsForeward.setMnemonic('F');
		GridBagConstraints gbc_btnMotorsForeward = new GridBagConstraints();
		gbc_btnMotorsForeward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorsForeward.insets = new Insets(0, 0, 5, 5);
		gbc_btnMotorsForeward.gridx = 2;
		gbc_btnMotorsForeward.gridy = 2;
		panelBotControlMotors.add(btnMotorsForeward, gbc_btnMotorsForeward);
		
		btnMotorLeftBackward = new JButton("Backward");
		GridBagConstraints gbc_btnMotorLeftBackward = new GridBagConstraints();
		gbc_btnMotorLeftBackward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorLeftBackward.insets = new Insets(0, 0, 0, 5);
		gbc_btnMotorLeftBackward.gridx = 0;
		gbc_btnMotorLeftBackward.gridy = 3;
		panelBotControlMotors.add(btnMotorLeftBackward, gbc_btnMotorLeftBackward);
		
		btnMotorRightBackward = new JButton("Backward");
		GridBagConstraints gbc_btnMotorRightBackward = new GridBagConstraints();
		gbc_btnMotorRightBackward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorRightBackward.insets = new Insets(0, 0, 0, 5);
		gbc_btnMotorRightBackward.gridx = 1;
		gbc_btnMotorRightBackward.gridy = 3;
		panelBotControlMotors.add(btnMotorRightBackward, gbc_btnMotorRightBackward);
		
		btnMotorsBackward = new JButton("Backward");
		btnMotorsBackward.setMnemonic('B');
		GridBagConstraints gbc_btnMotorsBackward = new GridBagConstraints();
		gbc_btnMotorsBackward.insets = new Insets(0, 0, 0, 5);
		gbc_btnMotorsBackward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorsBackward.gridx = 2;
		gbc_btnMotorsBackward.gridy = 3;
		panelBotControlMotors.add(btnMotorsBackward, gbc_btnMotorsBackward);
		
		lblTestBotsStatus = new JLabel("");
		lblTestBotsStatus.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTestBotsStatus = new GridBagConstraints();
		gbc_lblTestBotsStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblTestBotsStatus.fill = GridBagConstraints.BOTH;
		gbc_lblTestBotsStatus.gridx = 3;
		gbc_lblTestBotsStatus.gridy = 3;
		panelBotControlMotors.add(lblTestBotsStatus, gbc_lblTestBotsStatus);
		
		JPanel panelBotControlLEDs = new JPanel();
		panelBotControlLEDs.setBorder(new TitledBorder(null, "LEDs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBotControl.add(panelBotControlLEDs, "2, 6, fill, fill");
		GridBagLayout gbl_panelBotControlLEDs = new GridBagLayout();
		gbl_panelBotControlLEDs.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelBotControlLEDs.rowHeights = new int[]{0, 0, 0};
		gbl_panelBotControlLEDs.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelBotControlLEDs.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelBotControlLEDs.setLayout(gbl_panelBotControlLEDs);
		
		btnLEDStatus = new JButton("Status LED on");
		btnLEDStatus.setMnemonic('L');
		GridBagConstraints gbc_btnLEDStatus = new GridBagConstraints();
		gbc_btnLEDStatus.insets = new Insets(0, 0, 5, 5);
		gbc_btnLEDStatus.gridx = 0;
		gbc_btnLEDStatus.gridy = 0;
		panelBotControlLEDs.add(btnLEDStatus, gbc_btnLEDStatus);
		
		btnLEDAllOn = new JButton("All RGB LEDs on");
		btnLEDAllOn.setMnemonic('R');
		GridBagConstraints gbc_btnLEDAllOn = new GridBagConstraints();
		gbc_btnLEDAllOn.insets = new Insets(0, 0, 5, 5);
		gbc_btnLEDAllOn.gridx = 1;
		gbc_btnLEDAllOn.gridy = 0;
		panelBotControlLEDs.add(btnLEDAllOn, gbc_btnLEDAllOn);
		
		lblLEDAnimation = new JLabel("LED Animation:");
		lblLEDAnimation.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblLEDAnimation = new GridBagConstraints();
		gbc_lblLEDAnimation.insets = new Insets(0, 0, 5, 5);
		gbc_lblLEDAnimation.anchor = GridBagConstraints.EAST;
		gbc_lblLEDAnimation.gridx = 2;
		gbc_lblLEDAnimation.gridy = 0;
		panelBotControlLEDs.add(lblLEDAnimation, gbc_lblLEDAnimation);
		
		cmbLEDAnimation = new JComboBox<LEDAnimations>();
		cmbLEDAnimation.setModel(new DefaultComboBoxModel<LEDAnimations>(LEDAnimations.values()));
		GridBagConstraints gbc_cmbLEDAnimation = new GridBagConstraints();
		gbc_cmbLEDAnimation.insets = new Insets(0, 0, 5, 5);
		gbc_cmbLEDAnimation.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbLEDAnimation.gridx = 3;
		gbc_cmbLEDAnimation.gridy = 0;
		panelBotControlLEDs.add(cmbLEDAnimation, gbc_cmbLEDAnimation);
		
		panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.anchor = GridBagConstraints.NORTH;
		gbc_panel_6.gridwidth = 4;
		gbc_panel_6.insets = new Insets(0, 0, 0, 5);
		gbc_panel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panelBotControlLEDs.add(panel_6, gbc_panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gbl_panel_6.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		panel_6.setLayout(gbl_panel_6);
		
		lblLED1 = new JLabel("LED 1");
		GridBagConstraints gbc_lblLED1 = new GridBagConstraints();
		gbc_lblLED1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLED1.gridx = 1;
		gbc_lblLED1.gridy = 0;
		panel_6.add(lblLED1, gbc_lblLED1);
		
		lblLED2 = new JLabel("LED 2");
		GridBagConstraints gbc_lblLED2 = new GridBagConstraints();
		gbc_lblLED2.insets = new Insets(0, 0, 5, 5);
		gbc_lblLED2.gridx = 2;
		gbc_lblLED2.gridy = 0;
		panel_6.add(lblLED2, gbc_lblLED2);
		
		lblLED3 = new JLabel("LED 3");
		GridBagConstraints gbc_lblLED3 = new GridBagConstraints();
		gbc_lblLED3.insets = new Insets(0, 0, 5, 5);
		gbc_lblLED3.gridx = 3;
		gbc_lblLED3.gridy = 0;
		panel_6.add(lblLED3, gbc_lblLED3);
		
		lblLED4 = new JLabel("LED 4");
		GridBagConstraints gbc_lblLED4 = new GridBagConstraints();
		gbc_lblLED4.insets = new Insets(0, 0, 5, 5);
		gbc_lblLED4.gridx = 4;
		gbc_lblLED4.gridy = 0;
		panel_6.add(lblLED4, gbc_lblLED4);
		
		lblLEDColorRed = new JLabel("Red:");
		lblLEDColorRed.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblLEDColorRed = new GridBagConstraints();
		gbc_lblLEDColorRed.anchor = GridBagConstraints.EAST;
		gbc_lblLEDColorRed.insets = new Insets(0, 0, 5, 5);
		gbc_lblLEDColorRed.gridx = 0;
		gbc_lblLEDColorRed.gridy = 1;
		panel_6.add(lblLEDColorRed, gbc_lblLEDColorRed);
		
		txtLED1Red = new JTextField();
		txtLED1Red.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED1Red.selectAll();
			}
		});
		txtLED1Red.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED1Red.setText("0");
		GridBagConstraints gbc_txtLED1Red = new GridBagConstraints();
		gbc_txtLED1Red.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED1Red.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED1Red.gridx = 1;
		gbc_txtLED1Red.gridy = 1;
		panel_6.add(txtLED1Red, gbc_txtLED1Red);
		txtLED1Red.setColumns(10);
		
		txtLED1Green = new JTextField();
		txtLED1Green.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED1Green.selectAll();
			}
		});
		txtLED1Green.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED1Green.setText("0");
		GridBagConstraints gbc_txtLED1Green = new GridBagConstraints();
		gbc_txtLED1Green.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED1Green.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED1Green.gridx = 1;
		gbc_txtLED1Green.gridy = 2;
		panel_6.add(txtLED1Green, gbc_txtLED1Green);
		txtLED1Green.setColumns(10);		
		txtLED1Blue = new JTextField();
		txtLED1Blue.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED1Blue.selectAll();
			}
		});
		txtLED1Blue.setText("0");
		txtLED1Blue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtLED1Blue = new GridBagConstraints();
		gbc_txtLED1Blue.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED1Blue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED1Blue.gridx = 1;
		gbc_txtLED1Blue.gridy = 3;
		panel_6.add(txtLED1Blue, gbc_txtLED1Blue);
		txtLED1Blue.setColumns(10);
		
		txtLED2Red = new JTextField();
		txtLED2Red.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED2Red.selectAll();
			}
		});
		txtLED2Red.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED2Red.setText("0");
		GridBagConstraints gbc_txtLED2Red = new GridBagConstraints();
		gbc_txtLED2Red.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED2Red.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED2Red.gridx = 2;
		gbc_txtLED2Red.gridy = 1;
		panel_6.add(txtLED2Red, gbc_txtLED2Red);
		txtLED2Red.setColumns(10);
		
		txtLED2Green = new JTextField();
		txtLED2Green.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED2Green.selectAll();
			}
		});		
		txtLED2Green.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED2Green.setText("0");
		GridBagConstraints gbc_txtLED2Green = new GridBagConstraints();
		gbc_txtLED2Green.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED2Green.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED2Green.gridx = 2;
		gbc_txtLED2Green.gridy = 2;
		panel_6.add(txtLED2Green, gbc_txtLED2Green);
		txtLED2Green.setColumns(10);		
		txtLED2Blue = new JTextField();
		txtLED2Blue.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED2Blue.selectAll();
			}
		});
		txtLED2Blue.setText("0");
		txtLED2Blue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtLED2Blue = new GridBagConstraints();
		gbc_txtLED2Blue.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED2Blue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED2Blue.gridx = 2;
		gbc_txtLED2Blue.gridy = 3;
		panel_6.add(txtLED2Blue, gbc_txtLED2Blue);
		txtLED2Blue.setColumns(10);
		
		txtLED3Red = new JTextField();
		txtLED3Red.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED3Red.selectAll();
			}
		});
		
		txtLED3Red.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED3Red.setText("0");
		GridBagConstraints gbc_txtLED3Red = new GridBagConstraints();
		gbc_txtLED3Red.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED3Red.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED3Red.gridx = 3;
		gbc_txtLED3Red.gridy = 1;
		panel_6.add(txtLED3Red, gbc_txtLED3Red);
		txtLED3Red.setColumns(10);
		
		txtLED3Green = new JTextField();
		txtLED3Green.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED3Green.selectAll();
			}
		});		
		txtLED3Green.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED3Green.setText("0");
		GridBagConstraints gbc_txtLED3Green = new GridBagConstraints();
		gbc_txtLED3Green.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED3Green.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED3Green.gridx = 3;
		gbc_txtLED3Green.gridy = 2;
		panel_6.add(txtLED3Green, gbc_txtLED3Green);
		txtLED3Green.setColumns(10);		
		txtLED3Blue = new JTextField();
		txtLED3Blue.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED3Blue.selectAll();
			}
		});
		txtLED3Blue.setText("0");
		txtLED3Blue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtLED3Blue = new GridBagConstraints();
		gbc_txtLED3Blue.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED3Blue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED3Blue.gridx = 3;
		gbc_txtLED3Blue.gridy = 3;
		panel_6.add(txtLED3Blue, gbc_txtLED3Blue);
		txtLED3Blue.setColumns(10);
		
		txtLED4Red = new JTextField();
		txtLED4Red.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED4Red.selectAll();
			}
		});
		
		txtLED4Red.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED4Red.setText("0");
		GridBagConstraints gbc_txtLED4Red = new GridBagConstraints();
		gbc_txtLED4Red.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED4Red.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED4Red.gridx = 4;
		gbc_txtLED4Red.gridy = 1;
		panel_6.add(txtLED4Red, gbc_txtLED4Red);
		txtLED4Red.setColumns(10);
		
		txtLED4Green = new JTextField();
		txtLED4Green.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED4Green.selectAll();
			}
		});		
		txtLED4Green.setHorizontalAlignment(SwingConstants.CENTER);
		txtLED4Green.setText("0");
		GridBagConstraints gbc_txtLED4Green = new GridBagConstraints();
		gbc_txtLED4Green.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED4Green.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED4Green.gridx = 4;
		gbc_txtLED4Green.gridy = 2;
		panel_6.add(txtLED4Green, gbc_txtLED4Green);
		txtLED4Green.setColumns(10);		
		txtLED4Blue = new JTextField();
		txtLED4Blue.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtLED4Blue.selectAll();
			}
		});
		txtLED4Blue.setText("0");
		txtLED4Blue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtLED4Blue = new GridBagConstraints();
		gbc_txtLED4Blue.insets = new Insets(0, 0, 5, 5);
		gbc_txtLED4Blue.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLED4Blue.gridx = 4;
		gbc_txtLED4Blue.gridy = 3;
		panel_6.add(txtLED4Blue, gbc_txtLED4Blue);
		txtLED4Blue.setColumns(10);
		
		lblLEDColorGreen = new JLabel("Green:");
		lblLEDColorGreen.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblLEDColorGreen = new GridBagConstraints();
		gbc_lblLEDColorGreen.anchor = GridBagConstraints.EAST;
		gbc_lblLEDColorGreen.insets = new Insets(0, 0, 5, 5);
		gbc_lblLEDColorGreen.gridx = 0;
		gbc_lblLEDColorGreen.gridy = 2;
		panel_6.add(lblLEDColorGreen, gbc_lblLEDColorGreen);
		
		lblLEDColorBlue = new JLabel("Blue:");
		lblLEDColorBlue.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblLEDColorBlue = new GridBagConstraints();
		gbc_lblLEDColorBlue.anchor = GridBagConstraints.EAST;
		gbc_lblLEDColorBlue.insets = new Insets(0, 0, 5, 5);
		gbc_lblLEDColorBlue.gridx = 0;
		gbc_lblLEDColorBlue.gridy = 3;
		panel_6.add(lblLEDColorBlue, gbc_lblLEDColorBlue);
		
		btnLED1 = new JButton("LED 1 on");
		GridBagConstraints gbc_btnLED1 = new GridBagConstraints();
		gbc_btnLED1.fill = GridBagConstraints.BOTH;
		gbc_btnLED1.insets = new Insets(0, 0, 0, 5);
		gbc_btnLED1.gridx = 1;
		gbc_btnLED1.gridy = 4;
		panel_6.add(btnLED1, gbc_btnLED1);
		
		btnLED2 = new JButton("LED 2 on");
		GridBagConstraints gbc_btnLED2 = new GridBagConstraints();
		gbc_btnLED2.fill = GridBagConstraints.BOTH;
		gbc_btnLED2.insets = new Insets(0, 0, 0, 5);
		gbc_btnLED2.gridx = 2;
		gbc_btnLED2.gridy = 4;
		panel_6.add(btnLED2, gbc_btnLED2);
		
		btnLED3 = new JButton("LED 3 on");
		GridBagConstraints gbc_btnLED3 = new GridBagConstraints();
		gbc_btnLED3.fill = GridBagConstraints.BOTH;
		gbc_btnLED3.insets = new Insets(0, 0, 0, 5);
		gbc_btnLED3.gridx = 3;
		gbc_btnLED3.gridy = 4;
		panel_6.add(btnLED3, gbc_btnLED3);
		
		btnLED4 = new JButton("LED 4 on");
		GridBagConstraints gbc_btnLED4 = new GridBagConstraints();
		gbc_btnLED4.fill = GridBagConstraints.BOTH;
		gbc_btnLED4.insets = new Insets(0, 0, 0, 5);
		gbc_btnLED4.gridx = 4;
		gbc_btnLED4.gridy = 4;
		panel_6.add(btnLED4, gbc_btnLED4);
		
		panelTest = new JPanel();
		tabbedPane.addTab("Hardware Test", null, panelTest, null);
		panelTest.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblQuickTestTitle = new JLabel("Quick Test");
		lblQuickTestTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTest.add(lblQuickTestTitle, "2, 2");
		
		lblNormalTestTitle = new JLabel("Interval Test");
		lblNormalTestTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTest.add(lblNormalTestTitle, "4, 2");
		
		lblCompetitionTestTitle = new JLabel("Competition Test");
		lblCompetitionTestTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTest.add(lblCompetitionTestTitle, "6, 2");
		
		txtpnQuickSeconds = new JTextPane();
		txtpnQuickSeconds.setText("Quick 30 seconds test with heavy load.");
		txtpnQuickSeconds.setEditable(false);
		txtpnQuickSeconds.setBackground(UIManager.getColor("Label.background"));
		panelTest.add(txtpnQuickSeconds, "2, 4, fill, fill");
		
		txtpnTestWithIncreasing = new JTextPane();
		txtpnTestWithIncreasing.setText("Test with increasing intervalls with heavy load. Increases time from 10 seconds to 60 minutes. Overall testing time ca. 3h 30m.");
		txtpnTestWithIncreasing.setEditable(false);
		txtpnTestWithIncreasing.setBackground(UIManager.getColor("Label.background"));
		panelTest.add(txtpnTestWithIncreasing, "4, 4, fill, fill");
		
		txtpnSimulationTestOf = new JTextPane();
		txtpnSimulationTestOf.setText("Simulation test of whole competition day. Overall testing time 8 hours.");
		txtpnSimulationTestOf.setEditable(false);
		txtpnSimulationTestOf.setBackground(UIManager.getColor("Label.background"));
		panelTest.add(txtpnSimulationTestOf, "6, 4, fill, fill");
		
		btnQuickTest = new JButton("Start");
		panelTest.add(btnQuickTest, "2, 6");
		
		btnNormalTest = new JButton("Start");
		panelTest.add(btnNormalTest, "4, 6");
		
		btnCompetitionTestTest = new JButton("Start");
		panelTest.add(btnCompetitionTestTest, "6, 6");
		
		lblTestStatus = new JLabel("");
		panelTest.add(lblTestStatus, "2, 8, 3, 1");
		
		btnStopTest = new JButton("Stop Test");
		panelTest.add(btnStopTest, "6, 8");
		
		// call listener
		if( mListener != null ){
			mListener.frameLoaded(this);
		}
	}
	
	@Override
	public void windowOpened(WindowEvent arg0) {}			
	@Override
	public void windowIconified(WindowEvent arg0) {}			
	@Override
	public void windowDeiconified(WindowEvent arg0) {}			
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if( mListener != null ){
			mListener.frameCloseing(this);
		}
	}
	
	@Override
	public void windowClosed(WindowEvent arg0) {}			
	@Override
	public void windowActivated(WindowEvent arg0) {}
}
