import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
class Account {

	public static void main(String az[]) {
		firstInterface fi = new firstInterface();

	}
}

class firstInterface implements ActionListener{
	ResultSet Rs;
	JFrame J;
	JButton b1;
	JTextField T1, T2;
	JLabel L1, L2;
	JRadioButton RBcurrent, RBsavings, RBmale, RBfemale;
	String acctype, gender, AccNo, Name, Address;
	JMenuItem M1, M2, M3, M4;
	JMenuBar MenuBar;

	public firstInterface() {
		J = new JFrame();
		MenuBar= new JMenuBar();
		JMenu Menu1=new JMenu("Details");

		M1=new JMenuItem("Open Account");
		M2=new JMenuItem("Deposit");
		M3=new JMenuItem("Withdraw");
		M4=new JMenuItem("Check Balance");

		J.setJMenuBar(MenuBar);
		Menu1.add(M1);
		Menu1.add(M2);
		Menu1.add(M3);
		Menu1.add(M4);
		MenuBar.add(Menu1);

		M1.addActionListener(this);
		M2.addActionListener(this);
		M3.addActionListener(this);

		b1= new JButton("Create");
		T1=new JTextField(10);
		T2=new JTextField(10);
		L1=new JLabel("Name");
		L2=new JLabel("Address");
		RBcurrent= new JRadioButton("Current");
		RBsavings= new JRadioButton("Savings");
		RBmale = new JRadioButton("Male");
		RBfemale = new JRadioButton("Female");
		RBcurrent.addActionListener(this);
		RBsavings.addActionListener(this);
		RBmale.addActionListener(this);
		RBfemale.addActionListener(this);
		b1.addActionListener(this);
		J.setLayout(new GridLayout(5,2));
		J.add(L1);
		J.add(T1);
		J.add(L2);
		J.add(T2); 
		J.add(RBcurrent);

		J.add(RBmale);
		J.add(RBsavings);
		J.add(RBfemale);
		J.add(new JLabel());
		J.add(b1);


		J.setSize(400, 350);
		J.setLocationRelativeTo(null);
		J.setVisible(true);

	}

	public void actionPerformed(ActionEvent x) {
		try {
			JRadioButton jrb = (JRadioButton) x.getSource();

			if(RBcurrent == jrb) {
				acctype = "C";
				System.out.println("current");
				RBsavings.setSelected(false);
			}
			if(RBsavings == jrb) {
				acctype = "S";
				System.out.println("Saving");
				RBcurrent.setSelected(false);
			}
			if (RBmale == jrb) {
				gender = "M";
				System.out.println("Male");
				RBfemale.setSelected(false);
			}
			if (RBfemale == jrb) {
				gender = "F";
				System.out.println("Female");
				RBmale.setSelected(false);
			}
		} catch(Exception e) {

		}


		try {
			JButton jbtn = (JButton) x.getSource();


			if(jbtn == b1) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost/bank","root","");
					Statement st = con.createStatement();

					if(acctype.equals("C")) {
						Rs = st.executeQuery("select count(*) from accounts where AccNo like '%C%'"); //How many in a table
					}
					else {
						Rs= st.executeQuery("select count(*) from accounts where AccNo like '%S%'");
					}
					Rs.next();
					int NewAccNo=Rs.getInt(1)+1; //2
					AccNo = acctype + gender; //"CM"

					if(NewAccNo < 10){
						AccNo += "00";
					}
					else if(NewAccNo <100 && NewAccNo >= 10) {
						AccNo += "0";
					}

					//AccNo = CM00
					AccNo += Integer.toString(NewAccNo); //AccNo= CM00, NewAccNo = 2, CM002

					Name = T1.getText();
					Address = T2.getText();

					st.executeUpdate("insert into accounts values('"+AccNo+"', '"+Name+"','"  +Address+"')");

				} 
				catch (Exception e) {}
			}

		} catch(Exception e) {
		}		

		try {
			JMenuItem option = (JMenuItem) x.getSource();
			if(option == M1) {
				firstInterface fi = new firstInterface();
			}
			if(option==M2) {
				Deposit t = new Deposit(MenuBar);
			}
		} catch(Exception e){
			
		}
	}

}

