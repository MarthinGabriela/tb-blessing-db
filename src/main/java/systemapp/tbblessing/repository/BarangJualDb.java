package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BarangJualDb extends JpaRepository<BarangJualModel, Long>{
    Optional<BarangJualModel> findByIdBarangJual(Long idBarangJual);
}
