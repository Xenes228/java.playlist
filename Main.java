import java.io.*;
import java.util.*;

public class MusicPlayer {

    private static final String PLAYLIST_FILE = "playlists.txt";
    private static final String SONGS_FILE = "songs.txt";

    private List<Song> songs;
    private List<Playlist> playlists;
    private Playlist currentPlaylist;
    private int currentSongIndex;

    public MusicPlayer() {
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        currentPlaylist = null;
        currentSongIndex = -1;

        // Загружаем песни и плейлисты из файлов
        loadSongs();
        loadPlaylists();
    }

    // Загружает песни из файла
    private void loadSongs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SONGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                songs.add(new Song(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке песен: " + e.getMessage());
        }
    }

    // Загружает плейлисты из файла
    private void loadPlaylists() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PLAYLIST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Playlist playlist = new Playlist(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    playlist.addSong(getSongByName(parts[i]));
                }
                playlists.add(playlist);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке плейлистов: " + e.getMessage());
        }
    }

    // Сохраняет плейлисты в файл
    private void savePlaylists() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYLIST_FILE))) {
            for (Playlist playlist : playlists) {
                writer.write(playlist.getName() + ",");
                for (Song song : playlist.getSongs()) {
                    writer.write(song.getName() + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении плейлистов: " + e.getMessage());
        }
    }

    // Получает песню по названию
    private Song getSongByName(String name) {
        for (Song song : songs) {
            if (song.getName().equals(name)) {
                return song;
            }
        }
        return null;
    }

    // Получает плейлист по номеру
    private Playlist getPlaylistByIndex(int index) {
        if (index < 0 || index >= playlists.size()) {
            return null;
        }
        return playlists.get(index);
    }

    // Получает плейлист по названию
    private Playlist getPlaylistByName(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)) {
                return playlist;
            }
        }
        return null;
    }

    // Выводит список песен
    public void listSongs() {
        System.out.println("Список песен:");
        for (Song song : songs) {
            System.out.println(song);
        }
    }

    // Создает плейлист
    public void createPlaylist(String name) {
        if (getPlaylistByName(name) != null) {
            System.out.println("Плейлист с таким именем уже существует.");
            return;
        }
        Playlist playlist = new Playlist(name);
        playlists.add(playlist);
        System.out.println("Плейлист создан.");
    }

    // Включает плейлист
    public void playPlaylist(int index) {
        Playlist playlist = getPlaylistByIndex(index);
        if (playlist == null) {
            System.out.println("Плейлист с таким номером не существует.");
            return;
        }
        playPlaylist(playlist);
    }

    // Включает плейлист
    public void playPlaylist(String name) {
        Playlist playlist = getPlaylistByName(name);
        if (playlist == null) {
            System.out.println("Плейлист с таким именем не существует.");
            return;
        }
        playPlaylist(playlist);
    }

    // Включает плейлист
    private void playPlaylist(Playlist playlist) {
        currentPlaylist = playlist;
        currentSongIndex = 0;
        playCurrentSong();
    }

    // Сохраняет плейлист
    public void savePlaylist(int index) {
        Playlist playlist = getPlaylistByIndex(index);
        if (playlist == null) {
            System.out.println("Плейлист с таким номером не существует.");
            return;
        }
        savePlaylist(playlist);
    }

    // Сохраняет плейлист
    public void savePlaylist(String name) {
        Playlist playlist = getPlaylistByName(name);
        if (playlist == null) {
            System.out.println("Плейлист с таким именем не существует.");
            return;
        }
        savePlaylist(playlist);
    }

    // Сохраняет плейлист
    private void savePlaylist(Playlist playlist) {
        savePlaylists();
        System.out.println("Плейлист сохранен.");
    }

    // Удаляет плейлист
    public void deletePlaylist(int index) {
        Playlist playlist = getPlaylistByIndex(index);
        if (playlist == null) {
            System.out.println("Плейлист с таким номером не существует.");
            return;
        }
        deletePlaylist(playlist);
    }

    // Удаляет плейлист
    public void deletePlaylist(String name) {
        Playlist playlist = getPlaylistByName(name);
        if (playlist == null) {
            System.out.println("Плейлист с таким именем не существует.");
            return;
        }
        deletePlaylist(playlist);
    }

    // Удаляет плейлист
    private void deletePlaylist(Playlist playlist) {
        playlists.remove(playlist);
        savePlaylists();
        System.out.println("Плейлист удален.");
    }

    // Добавляет песню в плейлист
    public void addSongToPlaylist(int playlistIndex, int songIndex) {
        Playlist playlist = getPlaylistByIndex(playlistIndex);
        if (playlist == null) {
            System.out.println("Плейлист с таким номером не существует.");
            return;
        }
        Song song = getSongByIndex(songIndex);
        if (song == null) {
            System.out.println("Песня с таким номером не существует.");
            return;
        }
        addSongToPlaylist(playlist, song);
    }

    // Добавляет песню в плейлист
    public void addSongToPlaylist(int playlistIndex, String songName) {
        Playlist playlist = getPlaylistByIndex(playlistIndex);
        if (playlist == null) {
            System.out.println("Плейлист с таким номером не существует.");
            return;
        }
        Song song = getSongByName(songName);
        if (song == null) {
            System.out.println("Песня с таким именем не существует.");
            return;
        }
        addSongToPlaylist(playlist, song);
    }

    // Добавляет песню в плейлист
    public void addSongToPlaylist(String playlistName, int songIndex) {
        Playlist playlist = getPlaylistByName(playlistName);
        if (playlist == null) {
            System.out.println("Плейлист с таким именем не существует.");
            return;
        }
        Song song = getSongByIndex(songIndex);
        if (song == null) {
            System.out.println("Песня с таким номером не существует.");
            return;
        }
        addSongToPlaylist(playlist, song);
    }

    // Добавляет песню в плейлист
    public void addSongToPlaylist(String playlistName, String songName) {
        Playlist playlist = getPlaylistByName(playlistName);
        if (playlist == null) {
            System.out.println("Плейлист с таким именем не существует.");
            return;
        }
        Song song = getSongByName(songName);
        if (song == null) {
            System.out.println("Песня с таким именем не существует.");
            return;
        }
        addSongToPlaylist(playlist, song);
    }