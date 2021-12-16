/**CCCS 431 - Socket Programming Assignment
 * @author Joseph Moubarak 261060924
 * @author Jessica Gignac 260335082
 * 
 */
package assignmentscccs300;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**Server class creating a new server listener on port 431
 * Program creates new threads when a client connects
 */

public class Server {
	
	private static ExecutorService group = Executors.newFixedThreadPool(4);
	private static final int SERVER_PORT = 431;

	/**Main function to run a new server listener
	 * 
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
    	ServerSocket listener = new ServerSocket(SERVER_PORT);
    	System.out.println("CCCS 431 SDTP Server written by Joseph & Jessica READY");
    	try{
    		while (true) { //continue to create new client socket threads
    			Socket client = listener.accept();
    			System.out.println("[Client] " + client.getRemoteSocketAddress() + " connected");
    			ClientHandler clientThread = new ClientHandler(client);
    			System.out.println("[Client] new thread created for " + client.getRemoteSocketAddress());
    			group.execute(clientThread);
    		}
    	}
    	catch (Exception e) { //problem with the server close program
    		listener.close();
    		System.err.println("Error server stopped listening");
    		System.exit(1);
    	}
    	finally { //close server 
    		listener.close();
    	}
    }
}
