import java.util.ArrayList;

public class ConcertManager {
    private ArrayList<Concert> concerts;

    public ConcertManager() {
        concerts = new ArrayList<Concert>();
    }

    public void addConcert(Concert concert) {
        concerts.add(concert);
    }

    public void removeConcert(Concert concert) {
        concerts.remove(concert);
    }

    public ArrayList<Concert> getConcerts() {
        return concerts;
    }

    public void sortConcertsByPrice() {
        concerts.sort((Concert c1, Concert c2) -> c1.getDate().compareTo(c2.getDate()));
    }

    public void sortConcertsByDate() {
        concerts.sort((Concert c1, Concert c2) -> c1.getDate().compareTo(c2.getDate()));
    }

    public ArrayList<Concert> getConcertsByDate(String date)
    {
        ArrayList<Concert> concertsByDate = new ArrayList<Concert>();
        for (Concert concert : concerts)
        {
            if (concert.getDate().equals(date))
            {
                concertsByDate.add(concert);
            }
        }
        return concertsByDate;
    }

    public ArrayList<Concert> getConcertsByGenre(String genre)
    {
        ArrayList<Concert> concertsByGenre = new ArrayList<Concert>();
        for (Concert concert : concerts)
        {
            if (concert.getGenre().equals(genre))
            {
                concertsByGenre.add(concert);
            }
        }
        return concertsByGenre;
    }

    public ArrayList<Concert> getConcertsByArtist(String artist)
    {
        ArrayList<Concert> concertsByArtist = new ArrayList<Concert>();
        for (Concert concert : concerts)
        {
            if (concert.getArtists().contains(artist))
            {
                concertsByArtist.add(concert);
            }
        }
        return concertsByArtist;
    }

    public ArrayList<Concert> getConcertsByLocation(String location)
    {
        ArrayList<Concert> concertsByLocation = new ArrayList<Concert>();
        for (Concert concert : concerts)
        {
            if (concert.getLocation().equals(location))
            {
                concertsByLocation.add(concert);
            }
        }
        return concertsByLocation;
    }
    public ArrayList<Concert> getConcertsByPrice(int price)
    {
        ArrayList<Concert> concertsByPrice = new ArrayList<Concert>();
        for (Concert concert : concerts)
        {
            if (concert.getPrice() == price)
            {
                concertsByPrice.add(concert);
            }
        }
        return concertsByPrice;
    }




}
