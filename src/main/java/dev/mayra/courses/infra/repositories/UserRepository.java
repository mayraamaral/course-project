package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.entities.user.UserMinifiedDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  public Optional<User> findByUsername(String username);

  @Query("select new dev.mayra.courses.entities.user.UserMinifiedDTO(" +
      "u.name, u.email, r.name) " +
      "from User u inner join Role r on u.role.idRole = r.idRole " +
      "where u.username = :username"
  )
  public Optional<UserMinifiedDTO> findByUsernameMinified(@Param("username") String username);

  public Optional<User> findByEmail(String email);
}
