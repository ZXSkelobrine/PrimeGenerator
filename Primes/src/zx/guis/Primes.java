package zx.guis;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class Primes extends JFrame {

	private JPanel contentPane;

	private JTextField txtDigits;
	private JTextField txtTimes;
	private JTextField txtPrime;
	private JTextField txtAttempts;

	private JTextArea txtarLog;

	private JButton btnNewButton;
	private JButton btnNewButton_1;

	private JLabel lblDigits;
	private JLabel lblTimes;
	private JLabel lblLog;
	private JLabel lblPrime;
	private JLabel lblAttempts;

	private int generations;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Primes frame = new Primes();
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
	public Primes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		//Digits section

		//Digits label
		lblDigits = new JLabel("Digits: ");
		//Digits label grid bag constraints
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblDigits, gbc_lblNewLabel);

		txtDigits = new JTextField();
		//Digits text field grid bag constraints
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		gbc_textField.gridwidth = 2;
		contentPane.add(txtDigits, gbc_textField);
		txtDigits.setColumns(10);

		lblTimes = new JLabel("Amount: ");
		//Amount label grid bag constraints
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblTimes, gbc_lblNewLabel_1);

		txtTimes = new JTextField();
		//Times text field grid bag constraints
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		gbc_textField_1.gridwidth = 2;
		contentPane.add(txtTimes, gbc_textField_1);
		txtTimes.setColumns(10);

		chckbxNewCheckBox = new JCheckBox("Disable begin message");
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 2;
		contentPane.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);

		lblLog = new JLabel("Log: ");
		//Log label grid bag constraints
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		contentPane.add(lblLog, gbc_lblNewLabel_2);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		contentPane.add(scrollPane, gbc_scrollPane);

		txtarLog = new JTextArea();
		scrollPane.setViewportView(txtarLog);
		txtarLog.setEditable(false);

		lblPrime = new JLabel("Prime: ");
		//Prime label grid bag constraints
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 4;
		contentPane.add(lblPrime, gbc_lblNewLabel_3);

		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 4;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		txtPrime = new JTextField();
		scrollPane_1.setViewportView(txtPrime);
		txtPrime.setEditable(false);
		txtPrime.setColumns(10);

		lblAttempts = new JLabel("Attempts: ");
		//Attempts label grid bag constraints
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 5;
		contentPane.add(lblAttempts, gbc_lblNewLabel_4);

		txtAttempts = new JTextField();
		txtAttempts.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		gbc_textField_3.gridwidth = 2;
		contentPane.add(txtAttempts, gbc_textField_3);
		txtAttempts.setColumns(10);

		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (parseable(txtDigits.getText()) && parseable(txtTimes.getText())) {//If both the inputs in digits and times are numbers
					generations++;//Increment generations
					final PrimeGenerator gen = new PrimeGenerator(Integer.parseInt(txtDigits.getText()), Integer.parseInt(txtTimes.getText()));//Create a new PrimeGenerator
					gen.setFile(new File("Outputs/pg-log (" + new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss a").format(Calendar.getInstance().getTime()) + ").txt"));//Set the file
					gen.setLogOutput(txtarLog, txtPrime, txtAttempts);//Set log outputs
					if (chckbxNewCheckBox.isSelected())//If they ticked 'Disable begin messages'
						gen.disableBeginMessage();//Then disable the begin message
					new Thread("Prime Generator Loop #" + generations) {//Create a new thread with the given name (Becomes 'Prime Generator Loop #3 For example)
						public void run() {
							gen.startPrimeLoop();//Start the loop
						};
					}.start();//Start the thread
				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 6;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

	public static boolean parseable(String s) {
		try {//Try to 
			Integer.parseInt(s);//Parse the int
			return true;//And return true
		} catch (NumberFormatException e) {//If it does not work
			return false;//Return false
		}
	}

}
