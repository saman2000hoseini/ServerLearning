package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Server implements Runnable
{
    private final int port = 6500;
    private ArrayList<Socket> clients;
    private ServerSocket serverSocket;
    static HashMap<String, ObjectOutputStream> objectOutputStreams;
    static Vector<String> users = new Vector<>();

    public Server() throws IOException
    {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        objectOutputStreams= new HashMap<>();
    }

    public static void main(String[] args)
    {
        try
        {
            Thread t = new Thread(new Server());
            t.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Socket temp = serverSocket.accept();
                clients.add(temp);
                ClientHandler clientHandler = new ClientHandler(temp);
                new Thread(clientHandler).start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
