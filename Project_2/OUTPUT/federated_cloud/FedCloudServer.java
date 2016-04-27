package iaas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import javax.swing.JLabel;
public class FedCloudServer extends JFrame{
	JPanel p2,p3;
	CustomPanel p1;
	Font f1;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	ServerSocket server;
	ProcessThread thread;
	JLabel l1;
public void start(){
	try{
		server = new ServerSocket(2121);
		Object res[] = {"Federated Cloud Server Started"};
		dtm.addRow(res);
		while(true){
			Socket socket = server.accept();
			socket.setKeepAlive(true);
			thread=new ProcessThread(socket,dtm);
			thread.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public FedCloudServer(){
	super("Federated Cloud Server Screen");
	JPanel panel = new JPanel();
	f1 = new Font("Monospaced",Font.BOLD,22);
	l1 = new JLabel("<html><body><center>A MODEL FOR INVESTIGATING CLOUD COMPUTING(IAAS) IN DATA CENTER <BR/>PERFORMANCE AND QOS</center></body></html>");
	l1.setForeground(new Color(125,54,2));
	l1.setFont(f1);
	panel.add(l1);
	panel.setBackground(new Color(180, 196, 136, 136));

	p1 = new CustomPanel();
	p1.setTitle("    Federated Cloud Server Screen");
	p1.setLayout(null);
	
	p2 = new JPanel();
	p2.setLayout(new BorderLayout());

	f1 = new Font("Courier New",Font.BOLD,13);
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.setRowHeight(40);
	jsp = new JScrollPane(table);
	p2.add(jsp,BorderLayout.CENTER);
	dtm.addColumn("Federated Cloud Data Center Connection Request Details");

	
	p2.setBackground(Color.white);
	jsp.getViewport().setBackground(Color.white);
	p2.setBounds(0,45,600,400);
	p1.add(p2);
	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(panel,BorderLayout.NORTH);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	FedCloudServer cs = new FedCloudServer();
	cs.setVisible(true);
	cs.setSize(800,550);
	new ServerThread(cs);
}
}