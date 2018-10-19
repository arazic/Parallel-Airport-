import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MyGui extends JFrame {

	private JPanel contentPane;
	private JTextField txtDsds;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyGui frame = new MyGui();
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
	public MyGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fatma Ben Gurion Airport");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("David", Font.PLAIN, 22));
		lblNewLabel.setBounds(69, 16, 267, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNumber = new JLabel("number of technical crew");
		lblNumber.setForeground(new Color(0, 0, 0));
		lblNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumber.setFont(new Font("David", Font.PLAIN, 16));
		lblNumber.setBounds(15, 95, 182, 20);
		contentPane.add(lblNumber);
		
		JLabel lblNewLabel_1 = new JLabel("work time for security");
		lblNewLabel_1.setFont(new Font("David", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(227, 95, 201, 20);
		contentPane.add(lblNewLabel_1);
		
		txtDsds = new JTextField();
		txtDsds.setText("1");
		txtDsds.setBounds(25, 118, 159, 21);
		contentPane.add(txtDsds);
		txtDsds.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("2");
		textField_1.setBounds(225, 118, 146, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("START");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int numTechnicalCrew;
				int workSecurity;
				if(TheTxtIsInteger(txtDsds)){
					numTechnicalCrew= Integer.parseInt(txtDsds.getText());}
				else{
					numTechnicalCrew=1;
				}
				if(TheTxtIsNum(textField_1)){
					workSecurity= (int) Double.parseDouble(textField_1.getText());}
				else{
					workSecurity=2;
				}
				Airport AirPort= new Airport("FlightsData",numTechnicalCrew, workSecurity); 
				
			}
		});
		btnNewButton.setBounds(46, 184, 115, 29);
		contentPane.add(btnNewButton);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(236, 184, 115, 29);
		contentPane.add(btnExit);
	}
	 
	private boolean TheTxtIsInteger(JTextField textField_1){
		String txt= textField_1.getText();
		for(int i=0; i<txt.length(); i++){
			if(txt.charAt(i)=='.'){
				return false;
			}
		}
		return true;
	}
	
	private boolean TheTxtIsNum(JTextField txtDsds){
		String txt= txtDsds.getText();
		for(int i=0; i<txt.length(); i++){
			if(!(txt.charAt(i)>=0 && txt.charAt(i)<=9)){
				return false;
			}
		}
		return true;
	}
}
