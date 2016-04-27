package iaas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JComboBox;
public class ClientScreen extends JFrame{
	LineBorder border;
	TitledBorder title;
	JLabel l1,l2,l3;
	JPanel p1,p2,p3;
	JButton b1,b2,b3;
	JTextField tf1,tf2;
	Font f1;
	JComboBox c1;
public ClientScreen(){
	super("User Request Screen");
	
	f1 = new Font("Monospaced",Font.BOLD,16);
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	p1.setBackground(Color.white);

	p2 = new JPanel();
	p2.setPreferredSize(new Dimension(600,220));
	p2.setLayout(new MigLayout("wrap 1")); 
	p2.setBackground(Color.white);
	border = new LineBorder(new Color(42,140,241),1,true);
	title = new TitledBorder (border,"User Request Form",TitledBorder.CENTER,TitledBorder.DEFAULT_POSITION, new Font("Tahoma",Font.BOLD,16),Color.darkGray);
	p2.setBorder(title);
	
	l1 = new JLabel("No Of User");
	l1.setFont(f1);
	p2.add(l1,"split 2");

	tf1 = new JTextField(15);
	tf1.setFont(f1);
	p2.add(tf1);

	l3 = new JLabel("Videos");
	l3.setFont(f1);
	p2.add(l3,"split 2");

	c1 = new JComboBox();
	c1.addItem("video1.MP4");
	c1.addItem("video2.MP4");
	c1.addItem("video3.MP4");
	c1.addItem("video4.MP4");
	p2.add(c1);
	c1.setFont(f1);

	b1 = new JButton("Send Request");
	b1.setFont(f1);
	p2.add(b1,"split 2");
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			sendRequest();
		}
	});

	

	p1.add(p2,BorderLayout.CENTER);
	
	getContentPane().add(p1,BorderLayout.CENTER);
	
}
public void sendRequest(){
	try{
		File file = new File("download");
		File list[] = file.listFiles();
		for(int i=0;i<list.length;i++){
			if(list[i].isDirectory()){
				File dir[] = list[i].listFiles();
				for(File del : dir)
					del.delete();
				list[i].delete();
			}
		}
		String vid = c1.getSelectedItem().toString().trim();
		String user = tf1.getText();
		if(user == null || user.trim().length() <= 0){
			JOptionPane.showMessageDialog(this,"No of Users must be enter");
			tf1.requestFocus();
			return;
		}
		int users = 0;
		try{
			users = Integer.parseInt(user.trim());
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(this,"No of Users must be numeric only");
			tf1.requestFocus();
			return;
		}
		long start = System.currentTimeMillis();
		UserRequest[] arr = new UserRequest[users];
		for(int i=0;i<users;i++){
			file = new File("download/"+Integer.toString(i));
			if(!file.exists())
				file.mkdir();
			UserRequest ur = new UserRequest(Integer.toString(i),vid,"vod");
			ur.start();
			arr[i] = ur;
		}
		for(UserRequest ur : arr){
			ur.join();
		}
		long end = System.currentTimeMillis();
		long remain = end-start;
		UserRequest ur = new UserRequest("time",Long.toString(remain),"time");
		ur.start();
		ur.join();
		JOptionPane.showMessageDialog(this,"All request successfully process");
	}catch(Exception e){
		e.printStackTrace();
	}
}

public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	ClientScreen cs = new ClientScreen();
	cs.setVisible(true);
	cs.setSize(600,260);
} 
}