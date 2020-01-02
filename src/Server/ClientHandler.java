package Server;

import Model.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ClientHandler implements Runnable
{
    private Socket client;
    private ObjectInputStream objectInputStream;
    public ClientHandler(Socket client) throws IOException
    {
        System.out.println("new Client accepted!!");
        this.client = client;
        objectInputStream = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run()
    {
        while (true)
        {
            System.out.println("Listening...   ");
            Msg temp;
            try
            {
                temp = (Msg) objectInputStream.readObject();
                System.out.println("new message");
                if (temp.getTo().equals("Server"))
                {
                    Server.objectOutputStreams.put(temp.getFrom(), new ObjectOutputStream(client.getOutputStream()));
                    System.out.println(temp.getFrom()+" has joined =) ");
                    newUserJoined(temp.getFrom());
                }
                else
                    Server.objectOutputStreams.get(temp.getTo()).writeObject(temp);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    private synchronized void newUserJoined(String user) throws IOException
    {
        Server.users.add(user);
        System.out.println("Online users: "+Server.users.size());
        for(ObjectOutputStream ob : Server.objectOutputStreams.values())
        {
            ob.writeObject(new Msg("Server","Client",null));
            ob.writeObject(Server.users);
        }
    }
}
