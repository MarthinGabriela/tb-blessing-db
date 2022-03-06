package systemapp.tbblessing.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import systemapp.tbblessing.model.*;

public interface PembeliService {
    void addPembeli(PembeliModel pembeli);
    PembeliModel updatePembeli(Long idPembeli, PembeliModel pembeli);
    void deletePembeli(Long idPembeli);
    PembeliModel getPembeliById(Long idPembeli);
    PembeliModel getPembeliByNamaPembeli(String namaPembeli);
    List<PembeliModel> getAll();
    Page<PembeliModel> findAllBySearch(String search, PageRequest pageable);
    int getPembeliByNamaAndAlamat(String nama, String alamat);
}
