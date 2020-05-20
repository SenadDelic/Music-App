package database;

import model.Artist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistManagement {

    public List<Artist> queryArtist() {
        try (Statement statement = ConnectionManagement.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(Constants.QUERY_ARTIST)) {

            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(Constants.INDEX_ARTIST_ID));
                artist.setName(resultSet.getString(Constants.INDEX_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insertArtist(String name) throws Exception {
        try (PreparedStatement preparedStatement = ConnectionManagement.getInstance().getConnection().
                prepareStatement(Constants.INSERT_ARTIST)) {
            preparedStatement.setString(1, name);

            int affected = preparedStatement.executeUpdate();
            if (affected != 1)
                throw new Exception("Couldn't insert artist!");

            ResultSet generateKey = preparedStatement.getGeneratedKeys();
            return generateKey.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
