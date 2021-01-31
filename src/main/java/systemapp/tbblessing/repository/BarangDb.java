package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BarangDb extends JpaRepository<BarangModel, Long>{
    Optional<BarangModel> findByIdBarang(Long idBarang);
    Optional<BarangModel> findByNamaBarang(String namaBarang);
    List<BarangModel> findByStockBarangLessThanEqual(Long input);
	List<BarangModel> findAllByOrderByNamaBarangAsc();
	List<BarangModel> findByStockBarangGreaterThanEqual(Long input);
}