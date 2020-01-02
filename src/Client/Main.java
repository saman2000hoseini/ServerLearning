package Client;

import Model.Msg;

import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Please Enter your username:");
        Scanner input = new Scanner(System.in);
        String user = input.next();
        Client client = new Client(user);
        new Thread(client).start();
        while (true)
        {
            Msg msg;
            System.out.println("To :");
            String to = input.next();
            System.out.println(to);
            System.out.println("Please Enter your message:");
            String message = input.next();
            msg = new Msg(client.getUserName(),to,message);
            client.SendMessage(msg);
        }
    }
}
