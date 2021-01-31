package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PembayaranDb extends JpaRepository<PembayaranModel, Long>{
    Optional<PembayaranModel> findByIdPembayaran(Long idPembayaran);
}