package iaas;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.FileOutputStream;
public class UserRequest extends Thread{
	String folder,video;
	String type;
public UserRequest(String folder,String video,String type){
	this.folder = folder;
	this.video = video;
	this.type = type;
}
public void run(){
	try{
		Socket socket = new Socket("localhost",1111);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		if(!type.equals("time")){
			Object req[] = {type,folder,video};
			out.writeObject(req);
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object res[] = (Object[])in.readObject();
			String chunk = (String)res[0];
			if(chunk.equals("chunk")){
				byte b[] = (byte[])res[1];
				FileOutputStream fout = new FileOutputStream("download/"+folder+"/"+video);
				fout.write(b,0,b.length);
				fout.close();
			}
		}else{
			Object req[] = {type,video};
			out.writeObject(req);
			out.flush();
		}
	}catch(Exception e){
		e.printStackTrace();
	}

}
}