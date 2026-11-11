/**
 * Investigates a website and displays information about the network services involved in accessing it.
 * 
 * @author Ian Lindquist
 */
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.net.UnknownHostException;
public class NetworkInspector{

	public NetworkInspector(String hostname){
		this.hostname = hostname;
	}

	public static void main (String[] args)  throws UnknownHostException{
		if (args.length == 0){
			System.out.println("Usage: java NetworkInspector <remotehost name>\n");
			System.exit(0);
		}
		NetworkInspector remoteHost = new NetworkInspector(args[0]);
		System.out.println ("Hostname: "+ remoteHost.hostname+"\n");
		try{
			InetAddress[] inetAddresses = InetAddress.getAllByName(remoteHost.hostname);
			System.out.println("Resolved Addresses:\n");
			for (InetAddress address : inetAddresses){
				System.out.println(address.getHostAddress());
			}
			
		} catch (UnknownHostException e){
		e.printStackTrace();
		}
	}
	






















private String hostname;

}