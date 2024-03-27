package PaperWeight.Biscuit_corpo.controller;

import PaperWeight.Biscuit_corpo.entity.Song;
import PaperWeight.Biscuit_corpo.repository.SongRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Sample {
    @Autowired
    private SongRepoitory repo;
    @GetMapping("/api/test/items")
    public List<Song> test () {
        return repo.findAll();
    }
}
//TEST CLASS