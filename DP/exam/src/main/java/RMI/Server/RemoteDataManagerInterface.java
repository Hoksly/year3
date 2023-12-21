package RMI.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import Entity.Concert;

public interface RemoteDataManagerInterface extends Remote{
    public List<Concert> getConcerts() throws RemoteException;
    public List<Concert> getConcertsByDate(LocalDate date) throws RemoteException;
    public List<Concert> getConcertsByName(String group) throws RemoteException;
    public Concert getConcert(String Id) throws RemoteException;

    public void addConcert(Concert student) throws RemoteException;
    public void updateConcert(Concert updStudent) throws RemoteException;
    public void deleteConcert(Strring Id) throws RemoteException;

    public List<String> getIds() throws RemoteException;
    public String generateId() throws RemoteException;
    public boolean reserveId(String Id) throws RemoteException;
}