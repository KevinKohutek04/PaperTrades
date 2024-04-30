package PaperWeight.Biscuit_corpo.repository;

import PaperWeight.Biscuit_corpo.entity.ERole;
import PaperWeight.Biscuit_corpo.entity.Pos;
import PaperWeight.Biscuit_corpo.entity.Roles;
import PaperWeight.Biscuit_corpo.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PosRepository extends JpaRepository<Pos, Integer> {
    @Override
    Optional<Pos> findById(Integer id);
    List<Pos> findByUser(Long user);
    List<Pos> findByType(int type);
}