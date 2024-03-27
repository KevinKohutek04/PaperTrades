package PaperWeight.Biscuit_corpo.repository;

import java.util.Optional;

import PaperWeight.Biscuit_corpo.entity.ERole;
import PaperWeight.Biscuit_corpo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByName(ERole name);
}