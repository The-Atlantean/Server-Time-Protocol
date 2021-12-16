/**CCCS 431 - Socket Programming Assignment
 * @author Joseph Moubarak 261060924
 * @author Jessica Gignac 260335082
 * 
 */
package assignmentscccs300;
import java.io.*;
import java.net.*;
import java.time.*;

/**ClientHandler class implements the Runnable class
 * Program runs the client interactions
 */
public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private static boolean running;

    /**Create new client objects and marks the program as running
     * @param clientSocket
     * @throws Exception
     */
    public ClientHandler(Socket clientSocket) throws Exception {
    	this.client=clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(),true);
        running = true;
    }

    /**Override the run function for Executor class
     */
    @Override
    public void run() {
       try {
           out.println("[Server] CCCS431 Server written by Joseph & Jessica... READY"); //let client know the server is ready to receive commands
           boolean greeting = false; //if client hasn't greeted the server boolean remains false
           while (running) {
               String command = in.readLine(); //read client commands
               LocalDate localDate = LocalDate.now(); //create LocalDate object
               switch (command) { //switch statement for client commands 

                   case "bye": 
                       if (greeting) {
                    	   System.out.println("[Client] " + client.getRemoteSocketAddress() + " said BYE");
                           out.println("[Server] BYE");
                           System.out.println("[Client] " + client.getRemoteSocketAddress() + " disconnected");
                           running = false;
                           break;
                       } else {
                    	   out.println("[Server] Polite people say HELLO first");
                           continue;
                       }

                   case "hello":
                       if (!greeting) {
                    	   System.out.println("[Client] " + client.getRemoteSocketAddress() + " said HELLO");
                           out.println("[Server] ALOHA " + client.getRemoteSocketAddress());
                           greeting = true;
                           break;
                       } else {
                           out.println("[Server] ERROR Already said HELLO");
                           break;
                       }

                   case "dow":
                       if (greeting) {
                    	   System.out.println("[Client] " + client.getRemoteSocketAddress() + " said DOW");
                           DayOfWeek dow = DayOfWeek.from(localDate);
                           out.println("[Server] " + dow);
                           break;
                       } else {
                    	   out.println("[Server] Polite people say HELLO first");
                           continue;
                       }

                   case "time":
                       if (greeting) {
                    	   System.out.println("[Client] " + client.getRemoteSocketAddress() + " said TIME");
                           String time = String.format("%tR", LocalTime.now());
                           out.println("[Server] " +time);
                           break;
                       } else {
                    	   out.println("[Server] Polite people say HELLO first");
                           continue;
                       }
                   case "date":
                       if (greeting) {
                    	   System.out.println("[Client] " + client.getRemoteSocketAddress() + " said DATE");
                           out.println("[Server] "+java.time.LocalDate.now());
                           break;
                       } else {
                           out.println("[Server] Polite people say HELLO first");
                           continue;
                       }
                   default:
                       out.println("[Server] Unrecognized command");
                       break;
               }
           }
           running = false; 
       }
       catch (Exception e){ //if client terminates connection improperly
           System.err.println("[Client] " + client.getRemoteSocketAddress() + " terminated connection against protocol");
       } 
       finally { //close client sockets
    	   try {
               in.close();
               out.close();
               client.close();
           } catch (IOException a) {
               System.out.println("Error, server stopped");
               System.exit(1);
           }
       }
    }
}