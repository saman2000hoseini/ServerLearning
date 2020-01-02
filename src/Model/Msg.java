package Model;

import java.io.Serializable;

public class Msg implements Serializable
{
    private String from;
    private String to;
    private String message;

    public Msg(String from,String to, String message)
    {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public String getMessage()
    {
        return message;
    }
}
