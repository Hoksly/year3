package RMI.Server;


import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;



import Entity.Concert;
import DataManager.DataManager;
import DataManager.ConcertFactory;

public class RemoteDataManager implements RemoteDataManagerInterface{

    private DataManager manager;

    protected RemoteDataManager() throws RemoteException {
        manager = new DataManager(ConcertFactory.initSampleData(), "S");
    }

    @Override
    public List<Concert> getConcerts() throws RemoteException {
        return manager.getConcerts();
    }

    @Override
    public List<Concert> getConcertsByDate(LocalDate date) throws RemoteException {
        return manager.getConcertsByDate(date);
    }

    @Override
    public List<Concert> getConcertsByName(String group) throws RemoteException {
        return manager.getConcertsByName(group);
    }

    @Override
    public Concert getConcert(String Id) throws RemoteException {
        return manager.getConcert(Id);
    }

    @Override
    public void addConcert(Concert concert) throws RemoteException {
        manager.addConcert(concert);
    }

    @Override
    public void updateConcert(Concert updConcert) throws RemoteException {
        manager.updateConcert(updConcert);
        manager.getIdGenerator().releaseId(updConcert.getID());
    }

    @Override
    public void deleteConcert(String Id) throws RemoteException {
        manager.deleteConcert(Id);
    }

    @Override
    public List<String> getIds() throws RemoteException {
        return manager.getIdGenerator().getIDs();
    }

    @Override
    public String generateId() throws RemoteException {
        return manager.getIdGenerator().generateId();
    }

    @Override
    public boolean reserveId(String Id) throws RemoteException {
        return manager.getIdGenerator().reserveId(Id);
    }

}