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
		setBounds(100, 100, 800, 300);
		
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
		panelDebugInterface.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
		// call listener
		if( listener != null ){
			listener.frameLoaded(this);
		}
	}

}
