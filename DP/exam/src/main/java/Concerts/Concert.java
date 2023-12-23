import java.util.ArrayList;

public class Concert {
    private String name;
    private String location;
    private String date;
    private String time;
    private int price;
    ArrayList<String> artists;
    String genre;


    public String getName()
    {
        return name;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    public int getPrice()
    {
        return price;
    }

    public ArrayList<String> getArtists()
    {
        return artists;
    }

    public String getGenre()
    {
        return genre;
    }


    public Concert(String name, String location, String date, String time, int price, ArrayList<String> artists)
    {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.price = price;
        this.artists = artists;
    }




}
