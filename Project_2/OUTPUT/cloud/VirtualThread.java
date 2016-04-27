package iaas;
public class VirtualThread extends Thread
{
	CloudServer server;
public VirtualThread(CloudServer server){
	this.server=server;
	start();
}
public void run(){
	server.startVirtualServer();
}
}