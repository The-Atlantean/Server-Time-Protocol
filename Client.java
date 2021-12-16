/**CCCS 431 - Socket Programming Assignment
 * @author Joseph Moubarak 261060924
 * @author Jessica Gignac 260335082
 * 
 */
 package assignmentscccs300;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/**Client class for creating and running client application
 * Program creates new client sockets and guides user for input
 */
public class Client {

    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    /**Function to create new client socket and start the server connection
     * 
     * @param ip of the server
     * @param port to connect to the server on
     * @throws Exception
     */
    public void startConnection(String ip, int port) throws Exception {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**Function to send message to the server and receive response
     * 
     * @param msg to send sever
     * @return response from server
     * @throws Exception
     */
    public String sendMessage(String msg) throws Exception {
        out.println(msg);
        String response = in.readLine();
        return response;
    }

    /**Function to close the client socket
     * 
     * @throws Exception
     */
    public void stopConnection() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    /**Main function to run program
     * 
     * @param args
     */
    public static void main(String[] args)  {
       try {
    	   Client myClient = new Client(); //create new instance of a client
    	   Scanner input = new Scanner(System.in); //create new scanner object
    	   for (int i=0; i<40;i++){
    		   System.out.print("--");
    	   }
    	   //menu to display to client 
           System.out.println("\nWELCOME TO SDTP CLIENT");
           System.out.println("\nConnect* to the server using the IP address and then greet the server with hello");
           System.out.println("Once greeted you can ask the server for time, day of the week or date");
           System.out.println("Enter help or about for more information");
           System.out.println("Use bye to exit the session and disconnect" );
           for (int i=0; i<40;i++){
        	   System.out.print("--");
           }
           String hostName;
           if (args.length != 0) { //if host name was entered as a command line argument
        	   hostName = args[0];
           }
           else { //prompt user to enter host name
        	   System.out.println("\n>Connect*: Enter SDTP server IP Address below to initiate connection: ");
        	   System.out.print(">");
               hostName = input.nextLine(); 
           }
            myClient.startConnection(hostName, 431); //connect to server may cause an exception
            System.out.println("\n>Connect*: Connected to SDTP server via IP " + hostName);
            System.out.println(myClient.in.readLine()); //print the server connection confirmation
            System.out.print("\n> ");
            String command;
            while(!(command = input.nextLine().toLowerCase()).isEmpty()) { //while loop to ask for user input as long as enter is not pressed
            	String response;
            	if (command.contains("help") || command.contains("about")) { //print this help display if user inputs help or about
            		System.out.println("The SDTP server returns date, time or day of the week but you must greet it first");
            		System.out.println("You can use the following commands:");
                    System.out.println("HELLO: Greet the SDTP Server ");
                    System.out.println("TIME : Use it to return current time" );
                    System.out.println("DATE : Use it to return current date");
                    System.out.println("DOW : Use it to return day of the week");
                    System.out.println("BYE : Use it to end session and disconnect from SDTP Server");
                    System.out.println("Pressing ENTER without a command will terminate the session");
            		System.out.print("\n> ");
            	}
            	else if (command.contains("hello")) { //send hello command to server if user inputs hello
            		response = myClient.sendMessage("hello");
            		System.out.println(response);
            		System.out.print("\n> ");
            	}
            	else if (command.contains("date")) { //send date command if user inputs date
            		response = myClient.sendMessage("date");
            		System.out.println(response);
            		System.out.print("\n> ");
            	}
            	else if (command.contains("day") || command.contains("dow") || command.contains("week")) { //send dow command if user inputs day of the week or dow
            		response = myClient.sendMessage("dow");
            		System.out.println(response);
            		System.out.print("\n> ");
            	}
            	else if (command.contains("time")) { //send time command if user inputs time
            		response = myClient.sendMessage("time");
            		System.out.println(response);
            		System.out.print("\n> ");
            	}
            	else if (command.contains("bye") || command.contains("by")) { //send bye command if user inputs bye or by and break out of loop
            		response = myClient.sendMessage("bye");
            		System.out.println(response);
            		break;
            	}
            	else { //if anything else is entered besides valid commands
            		System.out.println("Invalid command");
            		System.out.print("\n> ");
            	}
            }
            System.out.println(">Terminating the connection");
            input.close(); //close scanner object
            myClient.stopConnection(); //close client socket
            System.out.println(">Exit*: Server connection closed");
        } 
       catch (NullPointerException b) {  //Server does not return anything 
    	   System.err.println("Server Error null return, connection closed");
    	   System.exit(1);
       }
       catch (IOException a) { //Server connection problem
           System.err.println("There was a problem connecting to the server");
           System.exit(1);
       }
       catch (Exception c) { //Server ended without proper shutdown
    	   System.err.println("Error server terminated, connection closed");
    	   System.exit(1);
       }
    }
}
