package zx.guis;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class PrimeSearcher extends JFrame implements WindowListener {

	private static final long serialVersionUID = -925448249482542622L;

	private JPanel contentPane;

	private JTextField txtDigits;
	private JTextField txtAmount;
	private JTextField txtAttempts;
	private JTextField txtGblAttempts;
	private JTextField txtCurrent;
	private JTextField txtPrime;

	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_2;

	private JTextArea txtarLog;

	private Thread primeThread;

	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public PrimeSearcher(Thread primeThread, int digits, int amount) {
		this.primeThread = primeThread;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("Digits");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txtDigits = new JTextField("" + digits);
		txtDigits.setEditable(false);
		GridBagConstraints gbc_txtDigits = new GridBagConstraints();
		gbc_txtDigits.insets = new Insets(0, 0, 5, 0);
		gbc_txtDigits.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDigits.gridx = 1;
		gbc_txtDigits.gridy = 0;
		contentPane.add(txtDigits, gbc_txtDigits);
		txtDigits.setColumns(10);

		lblNewLabel_2 = new JLabel("Amount");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		txtAmount = new JTextField("" + amount);
		txtAmount.setEditable(false);
		GridBagConstraints gbc_txtAmount = new GridBagConstraints();
		gbc_txtAmount.insets = new Insets(0, 0, 5, 0);
		gbc_txtAmount.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAmount.gridx = 1;
		gbc_txtAmount.gridy = 1;
		contentPane.add(txtAmount, gbc_txtAmount);
		txtAmount.setColumns(10);

		lblNewLabel_1 = new JLabel("Log");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);

		txtarLog = new JTextArea();
		txtarLog.setEditable(false);
		scrollPane.setViewportView(txtarLog);

		lblNewLabel_3 = new JLabel("Attempts");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtAttempts = new JTextField();
		txtAttempts.setEditable(false);
		GridBagConstraints gbc_txtAttempts = new GridBagConstraints();
		gbc_txtAttempts.insets = new Insets(0, 0, 5, 0);
		gbc_txtAttempts.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAttempts.gridx = 1;
		gbc_txtAttempts.gridy = 3;
		contentPane.add(txtAttempts, gbc_txtAttempts);
		txtAttempts.setColumns(10);

		lblNewLabel_4 = new JLabel("Global Attempts");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		contentPane.add(lblNewLabel_4, gbc_lblNewLabel_4);

		txtGblAttempts = new JTextField();
		txtGblAttempts.setEditable(false);
		GridBagConstraints gbc_txtGblAttempts = new GridBagConstraints();
		gbc_txtGblAttempts.insets = new Insets(0, 0, 5, 0);
		gbc_txtGblAttempts.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGblAttempts.gridx = 1;
		gbc_txtGblAttempts.gridy = 4;
		contentPane.add(txtGblAttempts, gbc_txtGblAttempts);
		txtGblAttempts.setColumns(10);

		lblNewLabel_5 = new JLabel("Current");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 5;
		contentPane.add(lblNewLabel_5, gbc_lblNewLabel_5);

		txtCurrent = new JTextField();
		txtCurrent.setEditable(false);
		GridBagConstraints gbc_txtCurrent = new GridBagConstraints();
		gbc_txtCurrent.insets = new Insets(0, 0, 5, 0);
		gbc_txtCurrent.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurrent.gridx = 1;
		gbc_txtCurrent.gridy = 5;
		contentPane.add(txtCurrent, gbc_txtCurrent);
		txtCurrent.setColumns(10);

		lblNewLabel_6 = new JLabel("Prime");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 6;
		contentPane.add(lblNewLabel_6, gbc_lblNewLabel_6);

		txtPrime = new JTextField();
		txtPrime.setEditable(false);
		GridBagConstraints gbc_txtPrime = new GridBagConstraints();
		gbc_txtPrime.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrime.gridx = 1;
		gbc_txtPrime.gridy = 6;
		contentPane.add(txtPrime, gbc_txtPrime);
		txtPrime.setColumns(10);
	}

	public void openWindow() {
		setVisible(true);
	}

	public JTextComponent[] getOrderedLogFields() {
		return new JTextComponent[] { txtarLog, txtPrime, txtAttempts, txtGblAttempts, txtCurrent };
	}

	public void windowClosing(WindowEvent e) {
		primeThread.interrupt();
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
