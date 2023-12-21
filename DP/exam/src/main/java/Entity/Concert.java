package Entity;

package com.example.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Concert implements Serializable{
    protected String name;
    protected String ID;
    protected String location;
    protected String date;
    protected String time;
    protected int price, seats;
    protected Set<String> artists;

    protected String genre;

    protected ArrayList<Boolean> bookedSeats;

    public Concert() {
    }

    public Concert(String ID) {
        this.ID = ID;
    }

    public Concert(String ID, String name, String location, String date, String time, int price, Set<String> artists, String genre, int seats) {
        this.ID = ID;
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.price = price;
        this.artists = artists;
        this.genre = genre;
        this.seats = seats;
        bookedSeats = new ArrayList<>();
        for(int i = 0; i < seats; i++){
            bookedSeats.add(false);
        }


    }

    public void bookSeat(int seatNumber){
        if(seatNumber >= bookedSeats.size() || seatNumber < 0){
            return;
        }
        bookedSeats.set(seatNumber, true);
    }

    public void setID(String val) {
        ID = val;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public void setDate(String date)
    {
        this.date = date;
    }

    public void setName(String val) {
        name = val;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setArtists(Set<String> artists) {
        this.artists = artists;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setSeats(int seats) {
        this.seats = seats;
        if(bookedSeats == null){
            bookedSeats = new ArrayList<>();
            for(int i = 0; i < seats; i++){
                bookedSeats.add(false);
            }
        }
    }


    public String getID() {
        return ID;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDate()
    {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public Set<String> getArtists() {
        return artists;
    }

    public String getGenre() {
        return genre;
    }

    public int getSeats() {
        return seats;
    }

    public ArrayList<Boolean> getBookedSeats() {
        return bookedSeats;
    }

    public boolean isBooked(int seatNumber){
        if(seatNumber >= bookedSeats.size() || seatNumber < 0){
            return false;
        }
        return bookedSeats.get(seatNumber);
    }

    public void addArtist(String artist){
        if(artists == null){
            artists = new HashSet<>();
        }
        artists.add(artist);
    }

    public String toString(){
        StringBuilder result = new StringBuilder(ID + "%" + name + "%" + location + "%" + date + "%" + time + "%" + price + "%" + genre + "%" + seats + "%");
        for(String artist : artists){
            result.append(artist).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }
}