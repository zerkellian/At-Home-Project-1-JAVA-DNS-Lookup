/**
 * Investigates a website and displays information about the network services involved in accessing it.
 * 
 * @author Ian Lindquist
 */
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletableFuture;
import javax.net.ssl.SSLSession;
import java.util.Optional;


public class NetworkInspector{

	public NetworkInspector(String hostname){ //constructor
		this.hostname = hostname;
	}

	public static void main (String[] args)  throws UnknownHostException{ //main function throws UnknownHostException in case host can't be reached
	if (args.length == 0){ // Check for no arguments passed
			System.out.println("Usage: java NetworkInspector <remotehost name>\n");
			System.exit(0);
		}
	NetworkInspector remoteHost = new NetworkInspector(args[0]); //Create a new NetworkInspector object
	System.out.println ("Hostname: "+ remoteHost.hostname+"\n");
	try{
			InetAddress[] inetAddresses = InetAddress.getAllByName(remoteHost.hostname); //Use InetAddress.getAllByName to retrieve the IP address list
			System.out.println("Resolved Addresses:");
			for (InetAddress address : inetAddresses){
				System.out.println(address.getHostAddress());
			}
			HttpClient http_client = HttpClient.newBuilder() //New HttpClient builder object for making the HTTPS request
				.followRedirects(HttpClient.Redirect.NORMAL) // Tell it to follow any redirects
				.build();
			HttpRequest a_request = HttpRequest.newBuilder() // Build an HTTPS request
				.uri(URI.create("https://"+ remoteHost.hostname))
				.build();
			int status_code = http_client.sendAsync(a_request, HttpResponse.BodyHandlers.ofString()) //Make the request asyncronously and capture the status code
				.thenApply(HttpResponse::statusCode)
				.join();
				if (status_code >= 200 && status_code <= 299){ // Check to see if the status code falls within the success range of 200 and 299. If so, print connection success and the code.
					System.out.println("\nHTTPS Connection Successful: ");
					System.out.println("\nStatus Code: ");
					System.out.println(status_code);
				} else{
					System.out.println("\nReceived status "+status_code + " from server"); //If not a success code, report on the other code it returned
				}
			System.out.println("\nTLS Version:");
			CompletableFuture<Optional<String>> tlsVersion = http_client.sendAsync(a_request, HttpResponse.BodyHandlers.discarding()) //return type CompletableFuture. Also, strip out the response body
				.thenApply(response -> response.sslSession().map(SSLSession::getProtocol)); // grab the protocl using SSLSession::getProtocol method
			tlsVersion.thenAccept(opt -> opt.ifPresent(System.out::println)) // Print out the TLS version number
				.join();
				
		} catch (UnknownHostException e){
		System.out.println("\n That host was unreachable"); //Catch for when the user-specified site wasn't reachable
		}
	}
	







private String hostname;

}