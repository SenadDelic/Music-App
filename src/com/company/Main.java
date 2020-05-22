package com.company;

import database.ArtistManagement;
import database.ConnectionManagement;
import model.Artist;
import model.SongArtist;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ConnectionManagement.getInstance().openConnection();
        testArtist();

        ConnectionManagement.getInstance().closeConnection();
    }

    /* Test CRUD Artist db, more than CRUD :3 */
    public static void testArtist() throws Exception {
        ArtistManagement artistManagement = new ArtistManagement();

        System.out.println("\nInsert artist");
        int id = artistManagement.insertArtist("Kiseljacka trojka :3");
        System.out.println("Id of artist is " + id);

        List<Artist> artists = artistManagement.queryArtist();
        artists.stream().map(artist -> "ID = " + artist.getId() + " Name = " + artist.getName()).forEach(System.out::println);
/*
        List<SongArtist> songArtists = artistManagement.queryArtistForSong("Dressed to kill");
        if (songArtists != null)
            songArtists.stream().map(artist -> "Artist name = " + artist.getArtistName() +
                    " Artist album = " + artist.getAlbumName() +
                    " Track = " + artist.getTrack()).forEach(System.out::println);
        else
            System.out.println("There is no artist for song!");*/


        // artistManagement.deleteArtist("Kiseljacka trojka :3");

        System.out.println("\nUpdate");
        artistManagement.updateArtist("Kiseljacka trojka", "Kiseljacka trojka :3");

        System.out.println("\nAfter Update");
        artists = artistManagement.queryArtist();
        artists.stream().map(artist -> "ID = " + artist.getId() + " Name = " + artist.getName()).forEach(System.out::println);

    }
}
