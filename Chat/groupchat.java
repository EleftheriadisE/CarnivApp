package CarnivAPP.Chat;

import java.net.*;
import java.io.*;
import java.util.*;
import CarnivAPP.GUI.*;

public class GroupChat 
{ 
    private static final String TERMINATE = "Έξοδος";
    static String name;
    static volatile boolean finished = false;
                InetAddress group = InetAddress.getByName(args[0]);
                int port = Integer.parseInt(args[1]);
                Scanner sc = new Scanner(System.in);
                System.out.print("Εισάγετε το όνομά σας: ");
                name = sc.nextLine();
                MulticastSocket socket = new MulticastSocket(port);
                socket.setTimeToLive(0);
                socket.joinGroup(group);
                Thread t = new Thread(new ReadThread(socket,group,port));
                t.start();
                System.out.println("Ξεκινήστε τη δημιουργία μηνυμάτων...\n");
                while(true) 
                { 
                    String message; 
                    message = sc.nextLine(); 
                    if(message.equalsIgnoreCase(GroupChat.TERMINATE)) 
                    { 
                        finished = true; 
                        socket.leaveGroup(group); 
                        socket.close(); 
                        break; 
                    } 
                    message = name + ": " + message; 
                    byte[] buffer = message.getBytes(); 
                    DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port); 
                    socket.send(datagram); 
                } 
            } 
            catch(SocketException se) 
            { 
                System.out.println("Σφάλμα δημιουργίας socket"); 
                se.printStackTrace(); 
            } 
            catch(IOException ie) 
            { 
                System.out.println("Σφάλμα ανάγνωσης ή εγγραφής από ή προς το socket"); 
                ie.printStackTrace(); 
            } 
        } 
    } 
} 
class ReadThread implements Runnable 
{ 
    private MulticastSocket socket; 
    private InetAddress group; 
    private int port; 
    private static final int MAX_LEN = 1000; 
    ReadThread(MulticastSocket socket,InetAddress group,int port) 
    { 
        this.socket = socket; 
        this.group = group; 
        this.port = port; 
    } 
      
    @Override
    public void run() 
    { 
        while(!GroupChat.finished) 
        { 
                byte[] buffer = new byte[ReadThread.MAX_LEN]; 
                DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port); 
                String message; 
            try
            { 
                socket.receive(datagram); 
                message = new
                String(buffer,0,datagram.getLength(),"UTF-8"); 
                if(!message.startsWith(GroupChat.name)) 
                    System.out.println(message); 
            } 
            catch(IOException e) 
            { 
                System.out.println("Το socket έκλεισε."); 
            } 
        } 
    } 
}