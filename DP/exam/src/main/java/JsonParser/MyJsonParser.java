package JsonParser;

import java.util.List;

import com.example.Entity.Concert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyJsonParser {

    public static List<Concert> parseConcerts(String json) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Concert>> ref = new TypeReference<List<Concert>>() {
        };
        try {
            return mapper.readValue(json, ref);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Concert parseConcert(String json) {
        if (json == "") {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Concert> ref = new TypeReference<Concert>() {
        };
        try {
            return mapper.readValue(json, ref);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<String> parseIds(String json) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<String>> ref = new TypeReference<List<String>>() {
        };
        try {
            return mapper.readValue(json, ref);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String toJsonConcert(Concert pub) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pub);
        } catch (JsonProcessingException e) {
            System.out.println("ERROR : " + e.getMessage());
            return "";
        }
    }


    public static String toJsonConcerts(List<Concert> pubs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pubs);
        } catch (JsonProcessingException e) {
            System.out.println("ERROR : " + e.getMessage());
            return "";
        }
    }

    public static String toJsonIDs(List<String> IDs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(IDs);
        } catch (JsonProcessingException e) {
            System.out.println("ERROR : " + e.getMessage());
            return "";
        }
    }
}