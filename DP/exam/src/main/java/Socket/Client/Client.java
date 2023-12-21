package Socket.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


import ConsoleManagement.InputManager;
import DataManager.ConcertFactory;
import Entity.Concert;
import JsonParser.MyJsonParser;

public class Client {
    private InputManager inputManager = new InputManager();
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean working = true;

    public Client(String host, int portId) {
        try {
            clientSocket = new Socket(host, portId);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeClient() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            helpActions();
            while (clientSocket.isConnected() && !clientSocket.isClosed() && working) {
                mainLoop(out, in);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        closeClient();
    }

    private void helpActions() {
        System.out.println(
                " a - add Concert,\n d - delete Concert,\n u - update Concert,\n g - get Concert by id,\n ga - get all Concerts,\n gap - get Concert by params,\n h - help,\n e - exit\n ");
    }


    private void sendConcertsRequest() {
        System.out.println("\n You are in Concert loading menu \n");
        String input;
        System.out
                .println(
                        "\n gg - find by name;\n gd - find by date;\n");
        input = inputManager.getString("Enter Command");
        switch (input) {
            case "gg":
                out.println("gg");
                out.println(inputManager.getString("Enter group name : "));
                return;
            case "gd":
                out.println("gd");
                out.println(inputManager.getDate("Enter date : "));
                return;
            default:
                System.out.println("Invalid command!");
                return;
        }
    }


    private void showConcerts() throws IOException {
        out.println("sa");
        List<Concert> pubs = MyJsonParser.parseConcerts(in.readLine());
        if (pubs == null) {
            return;
        }
        ConcertFactory.sort(pubs);
        for (Concert student : pubs) {
            System.out.println(student.toString());
        }
    }

    private void addConcert() throws IOException {
        out.println("aa");
        Concert p = ConcertFactory.makeConcert(inputManager, in.readLine(), null);
        out.println(MyJsonParser.toJsonConcert(p));
    }

    private void deleteConcert() throws IOException {
        out.println("da");
        String ID = inputManager.getOption(MyJsonParser.parseIds(in.readLine()), " Enter Concert ID : ");
        out.println(ID);
    }

    private void getConcert() throws IOException {
        out.println("ga");
        String ID = inputManager.getOption(MyJsonParser.parseIds(in.readLine()), "Enter Concert ID : ");
        out.println(ID);
        Concert p = MyJsonParser.parseConcert(in.readLine());
        if (p != null) {
            System.out.println(p.toString());
        }
    }


    private void updateConcert() throws IOException {
        out.println("ua");
        String ID = inputManager.getOption(MyJsonParser.parseIds(in.readLine()), "Enter Concert Id : ");
        out.println(ID);
        String temp = in.readLine();
        if (temp != "") {
            Concert p = MyJsonParser.parseConcert(temp);
            out.println(MyJsonParser.toJsonConcert(ConcertFactory.updateStudent(inputManager, p)));
        } else {
            System.out.println("This id is already reserved by other client!");
        }
    }

    private void getConcertByParams() throws IOException {
        out.println("gap");
        sendConcertsRequest();
        String tmp = in.readLine();
        if (tmp.equals("")) {
            return;
        }
        List<Concert> pubs = MyJsonParser.parseConcerts(tmp);
        ConcertFactory.sort(pubs);
        for (Concert student : pubs) {
            System.out.println(student.toString());
        }
    }


    private void mainLoop(PrintWriter out, BufferedReader in) throws IOException {
        String input;
        input = inputManager.getString("Enter command : ");
        switch (input) {
            case "ga":
                showConcerts();
                break;
            case "a":
                addConcert();
                break;
            case "u":
                updateConcert();
                break;
            case "gap":
                getConcertByParams();
                break;
            case "d":
                deleteConcert();
                break;
            case "g":
                getConcert();
                break;
            case "h":
                helpActions();
                break;
            case "e":
                System.out.println("\nYou stopped working with DB\n");
                out.println("e");
                working = false;
                return;
            default:
                System.out.println("Invalid command!");
                break;
        }

    }
}