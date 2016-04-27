package iaas;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.table.DefaultTableModel;
public class ProcessThread extends Thread{
	Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	DefaultTableModel dtm;
public ProcessThread(Socket soc,DefaultTableModel dtm){
	socket=soc;
	this.dtm = dtm;
	try{
		out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
	try{
		Object input[]=(Object[])in.readObject();
        String type=(String)input[0];
		if(type.equals("icc")){
			String client = (String)input[1];
			String video = (String)input[2];
			FileInputStream fin = new FileInputStream("video/"+video);
			byte b[] = new byte[fin.available()];
			fin.read(b,0,b.length);
			fin.close();
			Object req[] = {"chunk",b};
			out.writeObject(req);
			Object row[] = {"Request processed via federated server"};
			dtm.addRow(row);
		}
	}catch(Exception e){
        e.printStackTrace();
    }
}
}
