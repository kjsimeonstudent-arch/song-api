package com.simeon.songs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/velasco/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    // ✅ GET all songs

    @GetMapping("/")
    public String home() {
        return "API is running!";
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        return song.orElse(null);
    }

    // ✅ POST create
    @PostMapping
    public Song createSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    // ✅ PUT update
    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Optional<Song> optionalSong = songRepository.findById(id);

        if (optionalSong.isPresent()) {
            Song song = optionalSong.get();

            song.setTitle(updatedSong.getTitle());
            song.setArtist(updatedSong.getArtist());
            song.setAlbum(updatedSong.getAlbum());
            song.setGenre(updatedSong.getGenre());
            song.setUrl(updatedSong.getUrl());

            return songRepository.save(song);
        }

        return null;
    }

    // ✅ DELETE (must return STRING)
    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return "Song with ID " + id + " deleted.";
        }
        return "Song not found.";
    }

    // ✅ SEARCH (path variable, NOT query param)
    @GetMapping("/search/{value}")
    public List<Song> searchSongs(@PathVariable String value) {
        return songRepository
                .findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCaseOrGenreContainingIgnoreCase(
                        value, value, value, value);
    }
}
