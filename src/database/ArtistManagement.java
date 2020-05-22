package database;

import model.Artist;
import model.SongArtist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistManagement {
    List<Artist> artistList;

    public ArtistManagement() {
        artistList = new ArrayList<>();
    }

    public List<Artist> queryArtist() {
        try (Statement statement = ConnectionManagement.getInstance().getConnection().createStatement();
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
            return null;
        }
    }

    public boolean insertArtist(String name) throws Exception {
        if (!doesArtistExist(name)) {
            ResultSet generateKey = null;

            try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().
                    prepareStatement(Constants.INSERT_ARTIST)) {
                preparedStatement.setString(1, name);

                if (preparedStatement.executeUpdate() != 1)
                    throw new Exception("Couldn't insert artist!");

                generateKey = preparedStatement.getGeneratedKeys();
                System.out.println(generateKey.next() ? "Id of artist" + name + " is " + generateKey : "Can't return id");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (generateKey != null) generateKey.close();
            }
            return true;
        } else {
            System.out.println("Something is wrong");
            return false;
        }
    }

    public boolean doesArtistExist(String name) {
        artistList = queryArtist();
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

    public void deleteArtist(String artistName) {
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.DELETE_ARTIST)) {
            preparedStatement.setString(1, artistName);

            System.out.println(preparedStatement.executeUpdate() == 1 ? artistName + " is deleted" : "There is no " + artistName + " artist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Setting update with id?
    // Replace artistOldName, let user enter ..
    public void updateArtist(String artistNewName, String artistOldName) {
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.UPDATE_ARTIST_NAME)) {
            preparedStatement.setString(1, artistNewName);
            preparedStatement.setString(2, artistOldName);

            System.out.println(preparedStatement.executeUpdate() == 1 ? artistOldName + " is updated to " + artistNewName : "Something went wrong");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
