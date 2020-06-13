package database;

import model.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongManagement {
    List<Song> songs;

    public SongManagement() {
        songs = new ArrayList<>();
    }

    public List<Song> querySongs() {
        try (Statement statement = ConnectionManagement.getInstance().getConnection().createStatement();
             ResultSet resultset = statement.executeQuery(Constants.QUERY_SONG)) {

            while (resultset.next()) {
                Song song = new Song();
                song.setId(resultset.getInt(Constants.INDEX_SONG_ID));
                song.setTrack(resultset.getInt(Constants.INDEX_SONG_TRACK));
                song.setTitle(resultset.getString(Constants.INDEX_SONG_TITLE));
                song.setAlbumId(resultset.getInt(Constants.INDEX_SONG_ALBUM));

                songs.add(song);
            }
            return songs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertSong(Scanner input, Connection connection) throws SQLException {
        System.out.print("Enter title of the song: ");
        String title = input.nextLine();
        System.out.print("Enter name of the artist: ");
        String artist = input.nextLine();
        System.out.print("Enter album name: ");
        String album = input.nextLine();
        System.out.print("Enter track id: ");
        int track = input.nextInt();

        if (isSongExist(title)) {
            System.out.println("This title already exist");
        } else {
            try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.INSERT_SONGS)) {

                ConnectionManagement.getInstance().getConnection().setAutoCommit(false);
                ArtistManagement artistManagement = new ArtistManagement();
                AlbumManagement albumManagement = new AlbumManagement();

                int artistId = artistManagement.insertArtist(input, connection);
                System.out.println("Artist id = " + artistId);
                int albumId = albumManagement.insertAlbum(input, connection);
                System.out.println("Album id " + albumId);

                if (artistId != -1 && albumId != -1) {
                    preparedStatement.setInt(1, track);
                    preparedStatement.setString(2, title);
                    preparedStatement.setInt(3, albumId);
                    if (preparedStatement.executeUpdate() == 1)
                        ConnectionManagement.getInstance().getConnection().commit();
                } else
                    throw new SQLException();
            } catch (SQLException e) {
                System.out.println("Rollback");
                ConnectionManagement.getInstance().getConnection().rollback();
                e.printStackTrace();
            } finally {
                System.out.println("Auto-commit");
                ConnectionManagement.getInstance().getConnection().setAutoCommit(true);
            }
        }
    }

    public boolean isSongExist(String title) {
        songs = querySongs();
        return songs.stream().anyMatch(song -> song.getTitle().equals(title));
    }

    public void deleteSong(Scanner input) {
        System.out.print("Enter song title: ");
        String title = input.nextLine();

        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.DELETE_SONG)) {
            preparedStatement.setString(1, title);

            System.out.println(preparedStatement.executeUpdate() == 1 ? title + " is deleted" : "Can't find " + title);
        } catch (SQLException E) {
            E.printStackTrace();
        }
    }

    // add update for track !!
    public void updateSong(Scanner input) {
        System.out.print("Enter what song you want to rename = ");
        String oldTitle = input.nextLine();
        System.out.print("Enter new song name: ");
        String title = input.nextLine();

        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.UPDATE_SONG_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, oldTitle);

            System.out.println(preparedStatement.executeUpdate() == 1 ? title + " is updated" : "Something went wrong!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
