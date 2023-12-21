package DataManager;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entity.Concert;

public class DataManager {

    private List<Concert> concerts;
    private IDGenerator idGenerator;

    public DataManager(){
        concerts = new ArrayList<>();
        idGenerator = new IDGenerator("S");
    }

    public DataManager(String IdSample){
        concerts = new ArrayList<>();
        idGenerator = new IDGenerator(IdSample);
    }

    public DataManager(List<Concert> dummyData, String IdSample){
        this.concerts = new ArrayList<>();
        idGenerator = new IDGenerator(IdSample);
        for(Concert student : dummyData){
            addConcert(student);
        }
    }

    public IDGenerator getIdGenerator(){
        return this.idGenerator;
    }

    public List<Concert> getConcerts(){
        return concerts;
    }

    public List<Concert> getConcertsByDate(LocalDate date){
        List<Concert> result = new ArrayList<>();
        for(Concert student : concerts){
            if(LocalDate.parse(student.getDate()).isAfter(date)){
                result.add(student);
            }
        }
        return result;
    }

    public List<Concert> getConcertsByName(String str){
        List<Concert> result = new ArrayList<>();
        for(Concert student : concerts){
            if(student.getName().contains(str)){
                result.add(student);
            }
        }
        return result;
    }

    public List<Concert> getConcertByGenre(String genre){
        List<Concert> result = new ArrayList<>();
        for(Concert student : concerts){
            if(student.getGenre().contains(genre)){
                result.add(student);
            }
        }
        return result;
    }

    public List<Concert> getConcertByArtist(String artist){
        List<Concert> result = new ArrayList<>();
        for(Concert student : concerts){
            if(student.getArtists().contains(artist)){
                result.add(student);
            }
        }
        return result;
    }
    public List<Concert> getConcertByPrice(int price){
        List<Concert> result = new ArrayList<>();
        for(Concert student : concerts){
            if(student.getPrice() == price){
                result.add(student);
            }
        }
        return result;
    }


    public Concert getConcert(String Id){
        for(Concert publication : concerts){
            if(publication.getID().equals(Id)){
                return publication;
            }
        }
        return null;
    }

    public void addConcert(Concert publication){
        concerts.add(publication);
        idGenerator.addId(publication.getID());
    }

    public void deleteConcert(String Id){
        int deleteInd = 0;
        for(; deleteInd < concerts.size(); deleteInd++){
            if(concerts.get(deleteInd).getID().equals(Id)){
                break;
            }
        }
        if(deleteInd != concerts.size()){
            concerts.remove(deleteInd);
            idGenerator.removeId(Id);
        }
    }

    public void updateConcert(Concert upd){
        int ind = 0;
        for(;ind < concerts.size(); ind++){
            if(concerts.get(ind).getID().equals(upd.getID())){
                break;
            }
        }
        concerts.set(ind, upd);
    }

}
