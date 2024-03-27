package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.APIReturn;
import PaperWeight.Biscuit_corpo.entity.Song;
import PaperWeight.Biscuit_corpo.repository.SongRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SongController {
    @Autowired
    private SongRepoitory repo;
    @GetMapping("/AllSong")
    public List<Song> getSong () {
        return repo.findAll();
    }

    @GetMapping("/ModifySong")
    public ResponseEntity<APIReturn> ModifySong (@RequestBody Song song) {
        try {
            Optional<Song> array = repo.findById(song.getID());
            Song repoSong = array.get();
            repoSong.setSong(song.getSong());
            repoSong.setType(song.getType());
            repoSong.setgood(song.getgood());
            repo.save(repoSong);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SONG SAVED WIT ID:" +song.getID()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new APIReturn("556","ERROR","COULDNT FIND:" +song.getID()));
        }
    }
    @DeleteMapping("/deletSong")
    public String deletSong (@RequestParam String name) {

            List<Song> array = repo.findBySong(name);
            if(array.size() > 0 && array != null) {
                Song temp = array.get(0);
                repo.delete(temp);
                return "REMOVED: " + name;
            }

        return "CAN NOT FIND: " + name;
    }
    @PostMapping(value = "/AddSong", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIReturn> AddSong (@RequestBody Song song) {
        List<Song> array = repo.findBySong(song.getSong());
        if(array != null && array.size() > 0) {
            return ResponseEntity.unprocessableEntity().body(new APIReturn("55","NOT_SAVED","SONG ALREADY ESTIST"));
        } else {
            Song newSong = new Song();
            newSong.setSong(song.getSong());
            newSong.setgood(song.getgood());
            newSong.setType(song.getType());
            repo.save(newSong);
            return ResponseEntity.status(201).body(new APIReturn("0","SAVED","SONG SAVED WIT ID:" +song.getSong()));
        }
    }
}