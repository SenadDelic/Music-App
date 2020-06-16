package database;

import model.Artist;
import model.SongArtist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtistManagement {
    private List<Artist> artistList;

    public ArtistManagement() {
        artistList = new ArrayList<>();
    }

    public List<Artist> queryArtist(Connection connection) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Constants.QUERY_ARTIST)) {

            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(Constants.INDEX_ARTIST_ID));
                artist.setName(resultSet.getString(Constants.INDEX_ARTIST_NAME));
                artistList.add(artist);
            }
            return artistList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertArtist(Scanner input, Connection connection) throws SQLException {
        System.out.print("Enter artist name: ");
        String name = input.next();

        ResultSet generateKey = null;
        if (!doesArtistExist(name, connection)) {
            try (PreparedStatement preparedStatement = connection.
                    prepareStatement(Constants.INSERT_ARTIST)) {
                preparedStatement.setString(1, name);

                if (preparedStatement.executeUpdate() != 1)
                    throw new SQLException("Couldn't insert artist!");

                generateKey = preparedStatement.getGeneratedKeys();
                return generateKey.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (generateKey != null) generateKey.close();
            }
        } else
            System.out.println("There is already " + name);
        return -1;
    }

    public boolean doesArtistExist(String name, Connection connection) {
        artistList = queryArtist(connection);
        return artistList.stream().anyMatch(artist -> artist.getName().equals(name));
    }

    public List<SongArtist> queryArtistForSong(String songName) {
        String sb = Constants.QUERY_ARTISTS_FOR_SONG_START + songName + "\"";

        try (Statement statement = ConnectionManagement.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sb)) {
            List<SongArtist> songArtists = new ArrayList<>();

            while (resultSet.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));

                songArtists.add(songArtist);
            }
            return songArtists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteArtist(Scanner input) {
        System.out.print("Enter artist name: ");
        String artistName = input.nextLine();
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.DELETE_ARTIST)) {
            preparedStatement.setString(1, artistName);

            System.out.println(preparedStatement.executeUpdate() == 1 ? artistName + " is deleted" : "There is no " + artistName + " artist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateArtist(Scanner input) {
        System.out.print("Enter what artist you want to rename: ");
        String artistOldName = input.nextLine();
        System.out.println("Enter new artist name: ");
        String artistNewName = input.nextLine();

        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.UPDATE_ARTIST_NAME)) {
            preparedStatement.setString(1, artistNewName);
            preparedStatement.setString(2, artistOldName);

            System.out.println(preparedStatement.executeUpdate() == 1 ? artistOldName + " is updated to " + artistNewName : "Something went wrong");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
