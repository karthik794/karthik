package iaas;
public class ServerThread extends Thread
{
	FedCloudServer server;
public ServerThread(FedCloudServer server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}