package de.northernstars.mr.botcontrol.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.hanneseilers.jftdiserial.core.Baudrates;
import de.northernstars.mr.botcontrol.core.interfaces.GuiFrameListener;

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
	public JPanel panelLedControl;
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
				ColumnSpec.decode("max(50dlu;default)"),
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
		panel.add(btnConnect, "11, 1, 1, 7");
		
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
		
		lblStatusInfo = new JLabel("Status:");
		lblStatusInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblStatusInfo, "1, 11");
		
		lblStatus = new JLabel("");
		panel.add(lblStatus, "3, 11, 7, 1");
		
		panelBotControl = new JPanel();
		tabbedPane.addTab("BotControl", null, panelBotControl, null);
		panelBotControl.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
		panelLedControl = new JPanel();
		tabbedPane.addTab("LED Control", null, panelLedControl, null);
		panelLedControl.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
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
