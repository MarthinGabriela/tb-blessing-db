package systemapp.tbblessing.repository;

import systemapp.tbblessing.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Date;
import java.util.List;

@Repository
public interface TransaksiDb extends JpaRepository<TransaksiModel, Long>{
    Optional<TransaksiModel> findByIdTransaksi(Long id);
    List<TransaksiModel> findByNamaPembeli(String namaPembeli);
	List<TransaksiModel> findAllByTanggalTransaksiBetween(
      Date start,
      Date end);
    List<TransaksiModel> findTop10ByOrderByIdTransaksiDesc();
	List<TransaksiModel> findByIdTransaksiBetween(Long input, long l);
	List<TransaksiModel> findTop1ByOrderByIdTransaksiDesc();
}