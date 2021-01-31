package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import java.util.List;

public interface BarangService {
    void addBarang(BarangModel barangModel);
    BarangModel updateBarang(Long idBarang, BarangModel barangModel);
    void deleteBarang(Long idBarang);
    BarangModel getBarangByIdBarang(Long idBarang);
    BarangModel getBarangByNamaBarang(String namaBarang);
    List<BarangModel> getAllBarang();
    List<BarangModel> getByStockBarangLessThanEqual(Long kuantitas);
	List<BarangModel> getByStockBarangGreaterThanEqual(Long kuantitas);
}