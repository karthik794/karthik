package iaas;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.util.Collections;
public class ProcessThread extends Thread{
	Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	DefaultTableModel dtm;
	ArrayList<VM> vserver;
public ProcessThread(Socket soc,DefaultTableModel dtm,ArrayList<VM> vserver){
	socket=soc;
	this.dtm=dtm;
	this.vserver = vserver;
	try{
		out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
public void addRequest(String client,String video)throws Exception{
	Collections.sort(vserver,new VM());
	VM cs = vserver.get(0);
	int request = cs.getRequestSize()+1;
	if(request <= 3){
		cs.setRequestSize(request);
		cs.setClient(client);
		cs.setVideo(video);
		Object row[] = {cs.getRequestSize()+" No Of Request Virtual Server Processing On Port "+cs.getPort()};
		dtm.addRow(row);
		Socket vsocket = new Socket("localhost",cs.getPort());
		ObjectOutputStream vout = new ObjectOutputStream(vsocket.getOutputStream());
		Object req[] = {"icc",client,video};
		vout.writeObject(req);
		vout.flush();
		ObjectInputStream vin = new ObjectInputStream(vsocket.getInputStream());
		Object res[] = (Object[])vin.readObject();
		out.writeObject(res);
	}else{
		Object row[] = {"All VM Busy Request Sent to Federated Cloud Server Processing On Port 2121"};
		dtm.addRow(row);
		Socket vsocket = new Socket("localhost",2121);
		ObjectOutputStream vout = new ObjectOutputStream(vsocket.getOutputStream());
		Object req[] = {"icc",client,video};
		vout.writeObject(req);
		vout.flush();
		ObjectInputStream vin = new ObjectInputStream(vsocket.getInputStream());
		Object res[] = (Object[])vin.readObject();
		out.writeObject(res);
	}
}
@Override
public void run(){
	try{
		Object input[]=(Object[])in.readObject();
        String type=(String)input[0];
		if(type.equals("vod")){
			String client_id = (String)input[1];
			String video = (String)input[2];
			long start = System.currentTimeMillis();
			addRequest(client_id,video);
			long end = System.currentTimeMillis();
			CloudServer.setWaitingTime(end-start);
		}
		if(type.equals("time")){
			String time = (String)input[1];
			System.out.println("time "+time);
			CloudServer.setResponseTime(Long.parseLong(time));
		}
	}catch(Exception e){
        e.printStackTrace();
    }
}
}
