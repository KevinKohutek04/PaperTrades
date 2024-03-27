package PaperWeight.Biscuit_corpo.repository;


import PaperWeight.Biscuit_corpo.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepoitory extends JpaRepository<Song, Integer> {
    @Override
    Optional<Song> findById(Integer Song);

    @Override
    List<Song> findAll();
    List<Song> findBySong (String Song);
}