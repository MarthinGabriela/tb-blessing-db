package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PembeliDb extends JpaRepository<PembeliModel, Long>{
    List<PembeliModel> findAll();
    Optional<PembeliModel> findByIdPembeli(Long idPembeli);
    Optional<PembeliModel> findByNamaPembeli(String namaPembeli);
    List<PembeliModel> findAllByOrderByNamaPembeliAsc();
}