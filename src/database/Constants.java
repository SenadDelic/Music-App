package database;

public class Constants {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTIST = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    //Album
    public static final String QUERY_ALBUM = "SELECT * FROM " + TABLE_ALBUMS;
    public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS +
            '(' + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES(?, ?)";
    public static final String DELETE_ALBUM = "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + " = ?";
    String sql = "UPDATE albums SET name = ?, artist = ? WHERE name = ?";
    public static final String UPDATE_ALBUM_BASED_ON_NAME = "UPDATE " + TABLE_ALBUMS + " SET " + COLUMN_ALBUM_NAME + " = ?, " +
            COLUMN_ALBUM_ARTIST + " = ?" + " WHERE " + COLUMN_ALBUM_NAME + " = ?";

    // ARTIST
    public static final String QUERY_ARTIST =
            "SELECT * FROM " + Constants.TABLE_ARTIST;
    public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTIST + "(" + COLUMN_ARTIST_NAME + ")" +
            "VALUES (?)";
    public static final String QUERY_ARTISTS_FOR_SONG_START =
            "SELECT " + TABLE_ARTIST + "." + COLUMN_ARTIST_NAME + ", " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
                    TABLE_SONGS + "." + COLUMN_SONG_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + TABLE_ALBUMS + " ON " +
                    TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
                    " INNER JOIN " + TABLE_ARTIST + " ON " +
                    TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTIST + "." + COLUMN_ARTIST_ID +
                    " WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + " = \"";
    public static final String DELETE_ARTIST = "DELETE FROM " + TABLE_ARTIST + " WHERE name = ?";
    public static final String UPDATE_ARTIST_NAME = "UPDATE " + TABLE_ARTIST + " SET " + COLUMN_ARTIST_NAME +
            "= ? " + " WHERE " + COLUMN_ARTIST_NAME + "= ?";

    // Song
    public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS +
            '(' + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM +
            ") VALUES(?, ?, ?)";
    public static final String DELETE_SONG = "DELETE FROM " + TABLE_SONGS + " WHERE title = ?";
    public static final String UPDATE_SONG_TITLE = "UPDATE " + TABLE_SONGS + " SET " + COLUMN_SONG_TITLE + "= ?" +
            "WHERE " + COLUMN_SONG_TITLE + "= ?";
    public static final String QUERY_SONG = "SELECT * FROM " + TABLE_SONGS;

}
