package PaperWeight.Biscuit_corpo.repository;

import java.util.Optional;


import PaperWeight.Biscuit_corpo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //boolean exists(Id primaryKey);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Optional<User> findById(Long User);
}
//boolean exists(ID primaryKey);