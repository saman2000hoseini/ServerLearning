package Client;

import Model.Msg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class Client implements Runnable
{
    private Socket socket;
    private String userName;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private static Vector<String> onlineUsers = new Vector<>();
    private final int port = 6500;

    public Client(String userName) throws IOException
    {
        this.userName = userName;
        //onlineUsers.add(userName);
        try
        {
            socket = new Socket("localhost", port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        SendMessage(new Msg(userName, "Server", null));
    }


    @Override
    public void run()
    {
        try
        {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        while (true)
        {
            Msg temp;
            try
            {
                temp = (Msg) objectInputStream.readObject();
                if (temp.getFrom().equals("Server") && temp.getTo().equals("Client"))
                {
                    onlineUsers = new Vector<>();
                    onlineUsers.addAll((Vector) objectInputStream.readObject());
                    System.out.println(onlineUsers.size());
                    System.out.println("----------------------------------------------------");
                    System.out.println("Online Users: ");
                    for (String usr : onlineUsers)
                        System.out.println(usr);
                    System.out.println("----------------------------------------------------");
                }
                else
                {
                    System.out.println("----------------------------------------------------");
                    System.out.println(temp.getFrom() + ": " + temp.getMessage());
                    System.out.println("----------------------------------------------------");
                }
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

    public void SendMessage(Msg msg) throws IOException
    {
        objectOutputStream.writeObject(msg);
    }

    public String getUserName()
    {
        return userName;
    }
}
