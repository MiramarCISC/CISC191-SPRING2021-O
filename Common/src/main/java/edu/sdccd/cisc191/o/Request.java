package edu.sdccd.cisc191.o;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to create an object Request and the static methods are used to JSON serialize the Request object that is to be outputted
 * Also contains a method to deserialize a JSON serialized object
 */
public class Request {
    private String userName;

    @JsonIgnore
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJSON(Request user) throws Exception {
        return objectMapper.writeValueAsString(user);
    }
    public static Request fromJSON(String input) throws Exception{
        return objectMapper.readValue(input, Request.class);
    }
    protected Request(){

    }
    public Request(String username) {
        this.userName = username;
    }

    public String getLogEntryDay(){
        return userName;
    }

    @Override
    public String toString() {
        return String.format("User -  ", userName);
    }



}
