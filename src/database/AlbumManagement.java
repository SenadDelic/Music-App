package database;

import model.Album;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumManagement {
    List<Album> albumList;

    public AlbumManagement() {
        albumList = new ArrayList<>();
    }

    public List<Album> queryAlbum() {
        try (Statement statement = ConnectionManagement.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(Constants.QUERY_ALBUM)) {

            while (resultSet.next()) {
                Album album = new Album();
                album.setId(resultSet.getInt(Constants.INDEX_ALBUM_ID));
                album.setName(resultSet.getString(Constants.INDEX_ALBUM_NAME));
                album.setArtistId(resultSet.getInt(Constants.INDEX_ALBUM_ARTIST));

                albumList.add(album);
            }
            return albumList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertAlbum(String albumName, int albumId) throws SQLException {
        if (!doesAlbumExist(albumName)) {
            ResultSet resultSet = null;

            try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.INSERT_ALBUMS)) {
                preparedStatement.setString(1, albumName);
                preparedStatement.setInt(2, albumId);

                if (preparedStatement.executeUpdate() != 1)
                    throw new Exception("Can't insert album");

                resultSet = preparedStatement.getGeneratedKeys();
                System.out.println(resultSet.next() ? "Id of entered album is = " + resultSet.getInt(1) : "Can't return id");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) resultSet.close();
            }
            return true;
        } else {
            System.out.println(albumName + " already exist.");
            return false;
        }
    }

    public void deleteAlbum(String album) {
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.DELETE_ALBUM)) {
            preparedStatement.setString(1, album);

            System.out.println(preparedStatement.executeUpdate() == 1 ? album + " is deleted!" : "There is no " + album + " album!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Replace oldName, let user enter..
    public void updateAlbum(String newAlbumName, String oldName, int albumId) {
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.UPDATE_ALBUM_BASED_ON_NAME)) {
            preparedStatement.setString(1, newAlbumName);
            preparedStatement.setInt(2, albumId);
            preparedStatement.setString(3, oldName);

            System.out.println(preparedStatement.executeUpdate() == 1 ? "Successful update " : "Update failed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // find better solution ?
    public boolean doesAlbumExist(String name){
        albumList = queryAlbum();
        return albumList.stream().anyMatch(album -> album.getName().equals(name));
    }
}
