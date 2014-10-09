package zx.guis;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

/**
 * This is the GUI used to start off the prime search
 * 
 * @author ryan
 * 
 */
public class Primes extends JFrame {

	private static final long serialVersionUID = -4007972896640238192L;

	private JPanel contentPane;

	private JTextField txtDigits;
	private JTextField txtTimes;

	private JButton btnNewButton;

	private JLabel lblDigits;
	private JLabel lblTimes;

	private int generations;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 *            - These are the command line arguments
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
		setBounds(100, 100, 450, 189);
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

		btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			private PrimeGenerator gen;

			public void actionPerformed(ActionEvent e) {
				if (parseable(txtDigits.getText()) && parseable(txtTimes.getText())) {//If both the inputs in digits and times are numbers
					generations++;//Increment generations

					gen = new PrimeGenerator(Integer.parseInt(txtDigits.getText()), Integer.parseInt(txtTimes.getText()));
					Thread primeThread = new Thread("Prime Generator Loop #" + generations) {
						public void run() {
							gen.startPrimeLoop();//Start the loop
						};
					};
					PrimeSearcher searcher = new PrimeSearcher(primeThread, Integer.parseInt(txtDigits.getText()), Integer.parseInt(txtTimes.getText()));

					JTextComponent[] comps = searcher.getOrderedLogFields();

					gen.setFile(new File("Outputs/pg-log (" + new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss a").format(Calendar.getInstance().getTime()) + ").txt"));//Set the file
					gen.setLogOutput(comps[0], comps[1], comps[2], comps[3], comps[4]);
					//If they ticked 'Disable begin messages' Then disable the begin message
					if (chckbxNewCheckBox.isSelected()) gen.disableBeginMessage();

					searcher.openWindow();
					primeThread.start();

				}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}

	/**
	 * This method will check if a string is able to be parsed using
	 * {@link Integer#parseInt(String)}.
	 * 
	 * @param s
	 *            - The string to test
	 * @return {@link Boolean}[In primative form] - If the given string can be
	 *         parsed as an integer
	 */
	public static boolean parseable(String s) {
		try {//Try to 
			Integer.parseInt(s);//Parse the int
			return true;//And return true
		} catch (NumberFormatException e) {//If it does not work
			return false;//Return false
		}
	}

}
