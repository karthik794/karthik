package iaas;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.jfree.ui.RefineryUtilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JLabel;
public class CloudServer extends JFrame
{
	JButton b1,b2;
	JPanel p2,p3;
	CustomPanel p1;
	Font f1;
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	ArrayList<VM> vserver = new ArrayList<VM>();
	ServerSocket server;
	ProcessThread thread;
	VM cser;
	static long waiting_time;
	static long response_time;
	JLabel l1;
public static void setWaitingTime(long time){
	waiting_time = waiting_time+time;
}
public static void setResponseTime(long tme){
	response_time = response_time + tme;
}
public void startVirtualServer(){
	VM cs = new VM();
	cs.setRequestSize(0);
	cs.setPort(5000);
	vserver.add(cs);
	Object row[] = {"New Virtual Machine Created On Port "+cs.getPort()};
	dtm.addRow(row);

	VM cs1 = new VM();
	cs1.setRequestSize(0);
	cs1.setPort(5001);
	vserver.add(cs1);
	Object row1[] = {"New Virtual Machine Created On Port "+cs1.getPort()};
	dtm.addRow(row1);

	VM cs2 = new VM();
	cs2.setRequestSize(0);
	cs2.setPort(5002);
	vserver.add(cs2);
	Object row2[] = {"New Virtual Machine Created On Port "+cs2.getPort()};
	dtm.addRow(row2);
	
	for(int i=0;i<vserver.size();i++){
		cs = vserver.get(i);
		startVirtual(cs.getPort());
	}
}
public void startVirtual(final int port){
	Runnable r = new Runnable(){
		public void run(){
			try{
				System.out.println(port+" port");
				ServerSocket vserver = new ServerSocket(port);
				while(true){
					Socket soc = vserver.accept();
					VirtualProcess vp = new VirtualProcess(soc);
					vp.start();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	};
	new Thread(r).start();
}
public void start(){
	try{
		server = new ServerSocket(1111);
		Object res[] = {"Cloud Server Started"};
		dtm.addRow(res);
		while(true){
			Socket socket = server.accept();
			socket.setKeepAlive(true);
			thread=new ProcessThread(socket,dtm,vserver);
			thread.start();
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public CloudServer(){
	super("Cloud Server Screen");
	JPanel panel = new JPanel();
	f1 = new Font("Monospaced",Font.BOLD,22);
	l1 = new JLabel("<html><body><center> A MODEL FOR INVESTIGATING CLOUD COMPUTING(IAAS) IN DATA CENTER <BR/>PERFORMANCE AND QOS</center></body></html>");
	l1.setForeground(new Color(125,54,2));
	l1.setFont(f1);
	panel.add(l1);
	panel.setBackground(new Color(180, 196, 136, 136));

	p1 = new CustomPanel();
	p1.setTitle("    Cloud Server Screen");
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
	dtm.addColumn("Cloud Data Center Connection Request Details");

	p3 = new JPanel();
	b2 = new JButton("Clear");
	b2.setFont(f1);
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			for(int i=dtm.getRowCount()-1;i>=4;i--){
				dtm.removeRow(i);
			}
			for(int i=0;i<vserver.size();i++){
				VM cs = vserver.get(i);
				cs.setRequestSize(0);
				cs.getClient().clear();
			}
		}
	});
	b1 = new JButton("Response & waiting Time Chart");
	b1.setFont(f1);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Response & Waiting Time Chart",response_time,waiting_time);
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);

		}
	});
	p2.add(p3,BorderLayout.SOUTH);
	p2.setBackground(Color.white);
	jsp.getViewport().setBackground(Color.white);
	p2.setBounds(0,45,800,400);
	p1.add(p2);
	getContentPane().add(p1,BorderLayout.CENTER);
	getContentPane().add(panel,BorderLayout.NORTH);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	CloudServer cs = new CloudServer();
	cs.setVisible(true);
	cs.setSize(800,550);
	new ServerThread(cs);
	new VirtualThread(cs);
}
}