package PaperWeight.Biscuit_corpo.repository;

import PaperWeight.Biscuit_corpo.entity.ClosedPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClosedPosRepository extends JpaRepository<ClosedPos, Integer> {
    @Override
    Optional<ClosedPos> findById(Integer id);
    List<ClosedPos> findByUser(Long user);

}