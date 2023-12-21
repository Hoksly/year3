package Socket.Client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;



import Socket.Server.Server;
import Entity.Concert;
import JsonParser.MyJsonParser;


public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Server serverHandler;

    public ClientHandler(Socket socket, Server serverHandler) {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.serverHandler = serverHandler;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            closeClient();
        }
    }

    private void closeClient() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void genId() {
        writer.println(serverHandler.dbManager.getIdGenerator().generateId());
    }

    private void showConcerts() {
        List<Concert> conc;
        conc = serverHandler.dbManager.getConcerts();
        writer.println(MyJsonParser.toJsonConcerts(conc));
    }

    private void sendIds() {
        List<String> IDs;
        IDs = serverHandler.dbManager.getIdGenerator().getIDs();
        writer.println(MyJsonParser.toJsonIDs(IDs));
    }

    private void getConcert() throws IOException {
        sendIds();
        String temp = reader.readLine();
        if (serverHandler.dbManager.getIdGenerator().exists(temp)) {
            Concert p = serverHandler.dbManager.getConcert(temp);
            writer.println(MyJsonParser.toJsonConcert(p));
        } else {
            writer.println("[]");
        }
    }

    private void addConcert() throws IOException {
        genId();
        String temp = reader.readLine();
        serverHandler.dbManager.addConcert(MyJsonParser.parseConcert(temp));

    }

    private void deleteConcert() throws IOException {
        sendIds();
        String temp = reader.readLine();
        boolean checker = serverHandler.dbManager.getIdGenerator().reserveId(temp);
        if (checker) {
            serverHandler.dbManager.deleteConcert(temp);
        }
    }

    private void updateConcert() throws IOException {
        sendIds();
        String temp = reader.readLine();
        boolean checker = serverHandler.dbManager.getIdGenerator().reserveId(temp);
        if (checker) {
            Concert p = serverHandler.dbManager.getConcert(temp);
            writer.println(MyJsonParser.toJsonConcert(p));
            p = MyJsonParser.parseConcert(reader.readLine());
            serverHandler.dbManager.updateConcert(p);
            serverHandler.dbManager.getIdGenerator().releaseId(p.getID());
        } else {
            writer.println("");
        }
    }

    private void getConcertsByParams() throws IOException {
        List<Concert> conc;
        switch (reader.readLine()) {
            case "gg":
                String temp = reader.readLine();
                conc = serverHandler.dbManager.getConcertsByName(temp);
                writer.println(MyJsonParser.toJsonConcerts(conc));
                break;
            case "gd":
                LocalDate localDate = LocalDate.parse(reader.readLine());
                conc = serverHandler.dbManager.getConcertsByDate(localDate);
                writer.println(MyJsonParser.toJsonConcerts(conc));
                break;
            default:
                writer.println("");
                return;
        }
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected() && !socket.isClosed()) {
                String input = reader.readLine();
                writer.flush();
                switch (input) {
                    case "sa":
                        showConcerts();
                        break;
                    case "gap":
                        getConcertsByParams();
                        break;
                    case "ga":
                        getConcert();
                        break;
                    case "aa":
                        addConcert();
                        break;
                    case "da":
                        deleteConcert();
                        break;
                    case "ua":
                        updateConcert();
                        break;
                    case "e":
                        closeClient();
                        return;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            closeClient();
        }
    }

}