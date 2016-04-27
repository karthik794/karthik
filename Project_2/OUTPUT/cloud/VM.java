package iaas;
import java.util.ArrayList;
import java.util.Comparator;
public class VM implements Comparator{
	int port;
	int request;
	ArrayList<String> clientid = new ArrayList<String>();
	String video;
public void setClient(String client){
	clientid.add(client);
}
public ArrayList<String> getClient(){
	return clientid;
}
public void setVideo(String video){
	this.video = video;
}
public String getVideo(){
	return video;
}
public void setPort(int port){
	this.port = port;
}
public int getPort(){
	return port;
}
public void setRequestSize(int request){
	this.request = request;
}
public int getRequestSize(){
	return request;
}
public int compare(Object sr1, Object sr2){
	VM p1 =(VM)sr1;
	VM p2 =(VM)sr2;
	int s1 = p1.getRequestSize();
    int s2 = p2.getRequestSize();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}