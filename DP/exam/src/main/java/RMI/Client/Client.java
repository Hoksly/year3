package RMI.Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;


import ConsoleManagement.InputManager;
import DataManager.ConcertFactory;
import Entity.Concert;
import RMI.Server.RemoteDataManagerInterface;

public class Client {
    private RemoteDataManagerInterface dbRemote;
    private InputManager inputManager = new InputManager();

    public Client() {
        try {
            dbRemote = (RemoteDataManagerInterface) Naming.lookup("rmi://localhost:1234/DB");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showConcerts(List<Concert> publications) {
        if (publications == null) {
            return;
        }
        ConcertFactory.sort(publications);
        for (Concert publication : publications) {
            System.out.println(publication.toString());
        }
    }

    private void getConcerts() throws RemoteException {
        showConcerts(dbRemote.getConcerts());
    }

    private void getConcert() throws RemoteException {
        Concert p = dbRemote.getConcert(inputManager.getOption(dbRemote.getIds(), "Enter Concert Id : "));
        if (p != null) {
            System.out.println(p.toString());
            return;
        }
        System.out.println("Concert with this Id no longer exists!");
    }

    private void getConcertsByParams() throws RemoteException {
        System.out.println("\n You are in Concert loading menu \n");
        String input;
        System.out
                .println(
                        "\n gg - find by name;\n gd - find by date;\n");
        input = inputManager.getString("Enter Command");
        switch (input) {
            case "gg":
                showConcerts(dbRemote.getConcertsByName(inputManager.getString("Enter string to contain : ")));
                return;
            case "gd":
                showConcerts(dbRemote.getConcertsByDate(inputManager.getDate("Enter date : ")));
                return;
            default:
                System.out.println("Invalid command!");
                return;
        }
    }

    private void updateConcert() throws RemoteException {
        String Id = inputManager.getOption(dbRemote.getIds(), "Enter student Id : ");
        boolean reserved = dbRemote.reserveId(Id);
        if (reserved) {
            Concert p = ConcertFactory.updateStudent(inputManager, dbRemote.getConcert(Id));
            if(p != null){
                dbRemote.updateConcert(p);
            }
            return;
        }
        System.out.println("Unable to acquire lock on Concert with Id " + Id);
    }

    private void addConcert() throws RemoteException {
        dbRemote.addConcert(ConcertFactory.makeConcert(inputManager, dbRemote.generateId(), dbRemote.getIds()));
    }

    private void deleteConcert() throws RemoteException {
        String Id = inputManager.getOption(dbRemote.getIds(), "Enter Concert Id : ");
        boolean reserved = dbRemote.reserveId(Id);
        if (reserved) {
            dbRemote.deleteConcert(Id);
            return;
        }
        System.out.println("Unable to acquire lock on Concert with id " + Id);
    }

    private void helpActions() {
        System.out.println(
                " a - add Concert,\n d - delete Concert,\n u - update Concert,\n g - get Concert by id,\n ga - get all Concerts,\n gap - get Concert by params,\n h - help,\n e - exit\n ");
    }

    public void mainLoop() throws RemoteException {
        String input;
        helpActions();
        while (true) {
            input = inputManager.getString(" Enter command : ");
            switch (input) {
                case "ga":
                    getConcerts();
                    break;
                case "gap":
                    getConcertsByParams();
                    break;
                case "g":
                    getConcert();
                    break;
                case "a":
                    addConcert();
                    break;
                case "u":
                    updateConcert();
                    break;
                case "d":
                    deleteConcert();
                    break;
                case "h":
                    helpActions();
                    break;
                case "e":
                    System.out.println("You stopped working with current environment!");
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
}