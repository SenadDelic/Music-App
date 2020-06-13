package database;

import model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlbumManagement {
    private List<Album> albumList;

    public AlbumManagement() {
        albumList = new ArrayList<>();
    }

    public List<Album> queryAlbum(Connection connection) {
        try (Statement statement = connection.createStatement();
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

    public int insertAlbum(Scanner input, Connection connection) throws SQLException {
        System.out.print("Enter album name: ");
        String albumName = input.next();
        System.out.print("Enter album id: ");
        int albumId = input.nextInt();

        if (!doesAlbumExist(albumName, connection)) {
            ResultSet resultSet = null;

            try (PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_ALBUMS)) {
                preparedStatement.setString(1, albumName);
                preparedStatement.setInt(2, albumId);

                if (preparedStatement.executeUpdate() != 1)
                    throw new Exception("Can't insert album");

                resultSet = preparedStatement.getGeneratedKeys();
                return resultSet.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) resultSet.close();
            }
        } else
            System.out.println(albumName + " already exist.");
        return -1;
    }

    public void deleteAlbum(Scanner input) {
        System.out.print("Enter album name: ");
        String album = input.nextLine();

        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().prepareStatement(Constants.DELETE_ALBUM)) {
            preparedStatement.setString(1, album);

            System.out.println(preparedStatement.executeUpdate() == 1 ? album + " is deleted!" : "There is no " + album + " album!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Replace oldName, let user enter..
    // String newAlbumName, String oldName, int albumId
    public void updateAlbum(Scanner input) {
        String newAlbumName, oldName;
        int albumId;
        System.out.print("Which album you want to rename: ");
        oldName = input.next();
        System.out.print("Enter new album name: ");
        newAlbumName = input.next();
        System.out.print("Enter albumId: ");
        albumId = input.nextInt();

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
    public boolean doesAlbumExist(String name, Connection connection) {
        albumList = queryAlbum(connection);
        return albumList.stream().anyMatch(album -> album.getName().equals(name));
    }
}
