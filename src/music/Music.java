package music;

import database.AlbumManagement;
import database.ArtistManagement;
import database.ConnectionManagement;
import database.SongManagement;
import model.Album;
import model.Artist;
import model.Song;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Music {
    private final Scanner input = new Scanner(System.in);
    private final AlbumManagement albumManagement = new AlbumManagement();
    private final SongManagement songManagement = new SongManagement();
    private final ArtistManagement artistManagement = new ArtistManagement();
    private final Connection connection = ConnectionManagement.getInstance().getConnection();

    public void playMusic() throws SQLException {
        int choice;
        Menu.welcome();

        do {
            Menu.menu();
            choice = input.nextInt();
            input.nextLine(); // fixed skipping nextLine()
            switch (choice) {
                case 1:
                    albumManagement.insertAlbum(input, connection);
                    break;
                case 2:
                    artistManagement.insertArtist(input, connection);
                    break;
                case 3:
                    songManagement.insertSong(input, connection);
                    break;
                case 4:
                    albumManagement.updateAlbum(input);
                    break;
                case 5:
                    artistManagement.updateArtist(input);
                    break;
                case 6:
                    songManagement.updateSong(input);
                    break;
                case 7:
                    List<Album> albums = albumManagement.queryAlbum(connection);
                    albums.stream().map(album -> "Id = " + album.getId() + " Name = " + album.getName() + " Artist Id = " + album.getArtistId())
                            .forEach(System.out::println);
                    break;
                case 8:
                    List<Artist> artists = artistManagement.queryArtist(connection);
                    artists.stream().map(artist -> "Id = " + artist.getId() + " Name = " + artist.getName()).forEach(System.out::println);
                    break;
                case 9:
                    List<Song> songs = songManagement.querySongs();
                    songs.stream().map(song -> "Id = " + song.getId() + " Track = " + song.getTrack() + " Title = " + song.getTitle()
                            + " Album = " + song.getAlbumId()).forEach(System.out::println);
                    break;
                case 10:
                    albumManagement.deleteAlbum(input);
                    break;
                case 11:
                    artistManagement.deleteArtist(input);
                    break;
                case 12:
                    songManagement.deleteSong(input);
                    break;
                default:
                    if (choice != 0)
                        System.out.println("Wrong input!!");
            }
        } while (choice != 0);
        ConnectionManagement.getInstance().closeConnection();
        input.close();
        Menu.exit();
    }
}
