package com.company;

import database.ArtistManagement;
import database.ConnectionManagement;
import model.Artist;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ConnectionManagement.getInstance().openConnection();
        testArtist();

        ConnectionManagement.getInstance().closeConnection();
    }

    // Test CRUD Artist db
    public static void testArtist() throws Exception {
        ArtistManagement artistManagement = new ArtistManagement();

        int id = artistManagement.insertArtist("Kiseljacka trojka :3");
        System.out.println("Id of artist is " + id);

        List<Artist> artists = artistManagement.queryArtist();
        artists.stream().map(artist -> "ID = " + artist.getId() + " Name = " + artist.getName()).forEach(System.out::println);
    }
}
