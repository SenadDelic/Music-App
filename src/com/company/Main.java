package com.company;

import database.AlbumManagement;
import database.ArtistManagement;
import database.ConnectionManagement;
import model.Album;
import model.Artist;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ConnectionManagement.getInstance().openConnection();
        //testArtist();
        testAlbum();

        ConnectionManagement.getInstance().closeConnection();
    }

    // only for testing
    public static void testAlbum() {
        AlbumManagement albumManagement = new AlbumManagement();

        albumManagement.updateAlbum("Tales of the Crown", "Tailes", 16);

        List<Album> albumList = albumManagement.queryAlbum();
        albumList.stream().map(album -> "id = " + album.getId() + "; name = " + album.getName() + "; Artist Id = " + album.getArtistId()).
                forEach(System.out::println);
    }

    /* Test CRUD Artist db, more than CRUD :3 */
    public static void testArtist() {
        ArtistManagement artistManagement = new ArtistManagement();

        List<Artist> artists = artistManagement.queryArtist();
        artists.stream().map(artist -> "ID = " + artist.getId() + " Name = " + artist.getName()).forEach(System.out::println);

    }
}
