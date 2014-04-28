package de.northernstars.mr.botcontrol.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.hanneseilers.jftdiserial.core.Baudrates;
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
import de.hanneseilers.jftdiserial.core.DataBits;
import de.hanneseilers.jftdiserial.core.StopBits;
import de.hanneseilers.jftdiserial.core.Parity;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Main frame GUI.
 * @author Hannes Eilers
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

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
	public JPanel panelDebugInterface;
	public JPanel panel;
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
	public JPanel panel_1;
	public JLabel lblDebugPublisher;
	public JLabel lblDebugProduct;
	public JLabel lblDebugVersion;
	public JPanel panel_2;
	public JButton btnDebugLedStatus;
	public JPanel panel_3;
	public JPanel panel_4;
	public JLabel lblDebugBaudrate;
	public JComboBox<Baudrates> cmbDebugBaudrate;
	public JLabel lblDebugDevice;
	public JComboBox<String> cmbDebugDevices;
	public JButton btnDebugConnect;
	public JButton btnDebugLedRGB;
	public JLabel lblDebugLibrary;
	public JComboBox<String> cmbDebugSerialLibraries;
	public JTextField txtDebugDevice;
	public JLabel lblDebugBotID;
	public JLabel lblProtocolVersion;
	public JComboBox<ProtocolVersions> cmbProtocolVersion;
	public JPanel panelBotControlBotID;
	public JLabel lblBotId;
	public JTextField txtBotID;
	public JLabel lblChangeBotIDTo;
	public JTextField txtBotIDChangeTo;
	public JButton btnChangeBotID;
	public JButton btnTestFirst;
	public JPanel panelBotControlMotors;
	public JLabel lblLeftSpeed;
	public JLabel lblRightSpeed;
	public JTextField txtMotorLeft;
	public JTextField txtMotorRight;
	public JButton btnMotorsStop;
	public JButton btnMotorLeftForeward;
	public JButton btnMotorRightForeward;
	public JButton btnMotorLeftBackward;
	public JButton btnMotorRightBackward;
	public JButton btnMotorsForeward;
	public JButton btnMotorsBackward;
	public JLabel lblBothMotors;

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
		setTitle("MRBotControl");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
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
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSettings.add(panel, "2, 2, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(56dlu;default)"),},
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
		panel.add(cmbSerialLibraries, "3, 1, 7, 1, fill, default");
		
		lblDevice = new JLabel("Device:");
		lblDevice.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblDevice, "1, 3, right, default");
		
		cmbDevices = new JComboBox<String>();
		panel.add(cmbDevices, "3, 3, 7, 1, fill, default");
		
		btnConnect = new JButton("Connect");
		btnConnect.setMnemonic('C');
		panel.add(btnConnect, "11, 1, 1, 7");
		getRootPane().setDefaultButton(btnConnect);
		
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		panelBotControlBotID = new JPanel();
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
		
		btnTestFirst = new JButton("Test First 30 Bots");
		GridBagConstraints gbc_btnTestFirst = new GridBagConstraints();
		gbc_btnTestFirst.gridx = 6;
		gbc_btnTestFirst.gridy = 0;
		panelBotControlBotID.add(btnTestFirst, gbc_btnTestFirst);
		
		panelBotControlMotors = new JPanel();
		panelBotControlMotors.setBorder(new TitledBorder(null, "Motors", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelBotControl.add(panelBotControlMotors, "2, 4, fill, fill");
		GridBagLayout gbl_panelBotControlMotors = new GridBagLayout();
		gbl_panelBotControlMotors.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelBotControlMotors.rowHeights = new int[]{0, 40, 40, 40, 0};
		gbl_panelBotControlMotors.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_lblBothMotors.insets = new Insets(0, 0, 5, 0);
		gbc_lblBothMotors.gridx = 2;
		gbc_lblBothMotors.gridy = 0;
		panelBotControlMotors.add(lblBothMotors, gbc_lblBothMotors);
		
		txtMotorLeft = new JTextField();
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
		gbc_btnMotorsStop.insets = new Insets(0, 0, 5, 0);
		gbc_btnMotorsStop.gridx = 2;
		gbc_btnMotorsStop.gridy = 1;
		panelBotControlMotors.add(btnMotorsStop, gbc_btnMotorsStop);
		
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
		GridBagConstraints gbc_btnMotorsForeward = new GridBagConstraints();
		gbc_btnMotorsForeward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorsForeward.insets = new Insets(0, 0, 5, 0);
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
		GridBagConstraints gbc_btnMotorsBackward = new GridBagConstraints();
		gbc_btnMotorsBackward.fill = GridBagConstraints.BOTH;
		gbc_btnMotorsBackward.gridx = 2;
		gbc_btnMotorsBackward.gridy = 3;
		panelBotControlMotors.add(btnMotorsBackward, gbc_btnMotorsBackward);
		
		panelDebugInterface = new JPanel();
		tabbedPane.addTab("Debugging", null, panelDebugInterface, null);
		panelDebugInterface.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("min:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(150dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		panel_2 = new JPanel();
		panelDebugInterface.add(panel_2, "2, 2, fill, fill");
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		btnDebugLedStatus = new JButton("LED Status on");
		GridBagConstraints gbc_btnDebugLedStatus = new GridBagConstraints();
		gbc_btnDebugLedStatus.insets = new Insets(0, 0, 0, 5);
		gbc_btnDebugLedStatus.gridx = 0;
		gbc_btnDebugLedStatus.gridy = 0;
		panel_2.add(btnDebugLedStatus, gbc_btnDebugLedStatus);
		
		btnDebugLedRGB = new JButton("RGB LEDS on");
		GridBagConstraints gbc_btnDebugLedRGB = new GridBagConstraints();
		gbc_btnDebugLedRGB.gridx = 1;
		gbc_btnDebugLedRGB.gridy = 0;
		panel_2.add(btnDebugLedRGB, gbc_btnDebugLedRGB);
		
		panel_1 = new JPanel();
		panelDebugInterface.add(panel_1, "4, 2, fill, fill");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		lblDebugLibrary = new JLabel("Library:");
		lblDebugLibrary.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDebugLibrary = new GridBagConstraints();
		gbc_lblDebugLibrary.anchor = GridBagConstraints.EAST;
		gbc_lblDebugLibrary.insets = new Insets(0, 0, 5, 5);
		gbc_lblDebugLibrary.gridx = 0;
		gbc_lblDebugLibrary.gridy = 0;
		panel_3.add(lblDebugLibrary, gbc_lblDebugLibrary);
		
		cmbDebugSerialLibraries = new JComboBox<String>();
		GridBagConstraints gbc_cmbDebugSerialLibraries = new GridBagConstraints();
		gbc_cmbDebugSerialLibraries.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDebugSerialLibraries.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbDebugSerialLibraries.gridx = 1;
		gbc_cmbDebugSerialLibraries.gridy = 0;
		panel_3.add(cmbDebugSerialLibraries, gbc_cmbDebugSerialLibraries);
		
		lblDebugDevice = new JLabel("Device:");
		lblDebugDevice.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDebugDevice = new GridBagConstraints();
		gbc_lblDebugDevice.anchor = GridBagConstraints.EAST;
		gbc_lblDebugDevice.insets = new Insets(0, 0, 5, 5);
		gbc_lblDebugDevice.gridx = 0;
		gbc_lblDebugDevice.gridy = 1;
		panel_3.add(lblDebugDevice, gbc_lblDebugDevice);
		
		cmbDebugDevices = new JComboBox<String>();
		GridBagConstraints gbc_cmbDebugDevice = new GridBagConstraints();
		gbc_cmbDebugDevice.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDebugDevice.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbDebugDevice.gridx = 1;
		gbc_cmbDebugDevice.gridy = 1;
		panel_3.add(cmbDebugDevices, gbc_cmbDebugDevice);
		
		txtDebugDevice = new JTextField();
		GridBagConstraints gbc_txtDebugDevice = new GridBagConstraints();
		gbc_txtDebugDevice.insets = new Insets(0, 0, 5, 0);
		gbc_txtDebugDevice.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDebugDevice.gridx = 1;
		gbc_txtDebugDevice.gridy = 2;
		panel_3.add(txtDebugDevice, gbc_txtDebugDevice);
		txtDebugDevice.setColumns(10);
		
		lblDebugBaudrate = new JLabel("Baudrate:");
		lblDebugBaudrate.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblDebugBaudrate = new GridBagConstraints();
		gbc_lblDebugBaudrate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDebugBaudrate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDebugBaudrate.gridx = 0;
		gbc_lblDebugBaudrate.gridy = 3;
		panel_3.add(lblDebugBaudrate, gbc_lblDebugBaudrate);
		
		cmbDebugBaudrate = new JComboBox<Baudrates>();
		cmbDebugBaudrate.setModel(new DefaultComboBoxModel<Baudrates>(Baudrates.values()));
		GridBagConstraints gbc_cmbDebugBaudrate = new GridBagConstraints();
		gbc_cmbDebugBaudrate.insets = new Insets(0, 0, 5, 0);
		gbc_cmbDebugBaudrate.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbDebugBaudrate.gridx = 1;
		gbc_cmbDebugBaudrate.gridy = 3;
		panel_3.add(cmbDebugBaudrate, gbc_cmbDebugBaudrate);
		
		btnDebugConnect = new JButton("Connect");
		GridBagConstraints gbc_btnDebugConnect = new GridBagConstraints();
		gbc_btnDebugConnect.gridwidth = 2;
		gbc_btnDebugConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDebugConnect.gridx = 0;
		gbc_btnDebugConnect.gridy = 4;
		panel_3.add(btnDebugConnect, gbc_btnDebugConnect);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblLabelPusblisher = new JLabel("Pusblisher:");
		GridBagConstraints gbc_lblLabelPusblisher = new GridBagConstraints();
		gbc_lblLabelPusblisher.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLabelPusblisher.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabelPusblisher.gridx = 0;
		gbc_lblLabelPusblisher.gridy = 0;
		panel_4.add(lblLabelPusblisher, gbc_lblLabelPusblisher);
		lblLabelPusblisher.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblDebugPublisher = new JLabel("unknown");
		GridBagConstraints gbc_lblDebugPublisher = new GridBagConstraints();
		gbc_lblDebugPublisher.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDebugPublisher.insets = new Insets(0, 0, 5, 0);
		gbc_lblDebugPublisher.gridx = 1;
		gbc_lblDebugPublisher.gridy = 0;
		panel_4.add(lblDebugPublisher, gbc_lblDebugPublisher);
		
		JLabel lblLabelProduct = new JLabel("Product:");
		GridBagConstraints gbc_lblLabelProduct = new GridBagConstraints();
		gbc_lblLabelProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLabelProduct.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabelProduct.gridx = 0;
		gbc_lblLabelProduct.gridy = 1;
		panel_4.add(lblLabelProduct, gbc_lblLabelProduct);
		lblLabelProduct.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblDebugProduct = new JLabel("unknown");
		GridBagConstraints gbc_lblDebugProduct = new GridBagConstraints();
		gbc_lblDebugProduct.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDebugProduct.insets = new Insets(0, 0, 5, 0);
		gbc_lblDebugProduct.gridx = 1;
		gbc_lblDebugProduct.gridy = 1;
		panel_4.add(lblDebugProduct, gbc_lblDebugProduct);
		
		JLabel lblLabelVersion = new JLabel("Version:");
		GridBagConstraints gbc_lblLabelVersion = new GridBagConstraints();
		gbc_lblLabelVersion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLabelVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblLabelVersion.gridx = 0;
		gbc_lblLabelVersion.gridy = 2;
		panel_4.add(lblLabelVersion, gbc_lblLabelVersion);
		lblLabelVersion.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblDebugVersion = new JLabel("unknown");
		GridBagConstraints gbc_lblDebugVersion = new GridBagConstraints();
		gbc_lblDebugVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblDebugVersion.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDebugVersion.gridx = 1;
		gbc_lblDebugVersion.gridy = 2;
		panel_4.add(lblDebugVersion, gbc_lblDebugVersion);
		
		JLabel lblLabelBotID = new JLabel("ID:");
		lblLabelBotID.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblLabelBotID = new GridBagConstraints();
		gbc_lblLabelBotID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblLabelBotID.insets = new Insets(0, 0, 0, 5);
		gbc_lblLabelBotID.gridx = 0;
		gbc_lblLabelBotID.gridy = 3;
		panel_4.add(lblLabelBotID, gbc_lblLabelBotID);
		
		lblDebugBotID = new JLabel("unknown");
		GridBagConstraints gbc_lblDebugBotID = new GridBagConstraints();
		gbc_lblDebugBotID.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDebugBotID.gridx = 1;
		gbc_lblDebugBotID.gridy = 3;
		panel_4.add(lblDebugBotID, gbc_lblDebugBotID);
		
		// call listener
		if( listener != null ){
			listener.frameLoaded(this);
		}
	}

}
