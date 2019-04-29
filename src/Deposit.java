import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class Deposit implements ActionListener {

	ResultSet Rs;
	JFrame J;
	JButton b1, bcheck;
	JTextField T1, T2, T3, T4;
	JLabel L1, L2, L3, L4;
	String account;
	int bal=0;

	public Deposit(JMenuBar MenuBar) {
		J = new JFrame();
		J.setJMenuBar(MenuBar);

		b1 = new JButton("Done");
		bcheck = new JButton("Check");
		T1 = new JTextField(10);
		T2 = new JTextField(10);
		L1 = new JLabel("Account");
		L2 = new JLabel("Amount");
		// T3=new JTextField(10);
		// L3=new JLabel("Date");
		T4 = new JTextField(10);
		L4 = new JLabel("Current Balance");
		// J.setLayout(new GridLayout(5,2));
		J.setLayout(new GridLayout(4, 2));
		J.add(L1);
		J.add(T1);
		J.add(L2);
		J.add(T2);
		// J.add(L3);
		// J.add(T3);
		J.add(L4);
		J.add(T4);
		J.add(bcheck);
		J.add(b1);
		J.setSize(400, 350);
		J.setLocationRelativeTo(null);
		J.setVisible(true);
		b1.addActionListener(this);
		bcheck.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent x) {
		JButton jbtn = (JButton) x.getSource();
		String balance = "";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank", "root", "");
			Statement st = con.createStatement();
			account = T1.getText();
		
			Rs = st.executeQuery("SELECT * FROM `accounts` WHERE accno = '" + account + "'");
			if(jbtn.equals(bcheck)) {
				if (!Rs.next()) {
					JOptionPane.showMessageDialog(J, "please enter a valid account number");
				} else {
					Rs = st.executeQuery("SELECT ifnull(sum(amount),0) FROM deposit WHERE accno = '" + account + "'");
					Rs.next();
					bal = Rs.getInt(1);
					Rs = st.executeQuery("SELECT ifnull(sum(amount),0) FROM withdraw WHERE accno = '" + account + "'");
					Rs.next();
					bal -= Rs.getInt(1);
					balance = Integer.toString(bal);
					T4.setText(balance);
				}
			} else if(jbtn.equals(b1)){
				int amount = Integer.parseInt(T2.getText());
				st.executeUpdate("insert into deposit values('"+ account + "', " + amount +", current_date)");
				JOptionPane.showMessageDialog(J, "your new balance is "+(bal+amount));
				balance = Integer.toString(bal+amount);
				T4.setText(balance);
			}
		} catch (Exception E) {

		}
	}

}
