package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoginDb extends JpaRepository<LoginModel, Long>{
    Optional<LoginModel> findById(Long id);
    Optional<LoginModel> findByToken(String token);
}
