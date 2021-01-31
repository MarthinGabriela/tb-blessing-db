package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;

public interface BarangJualService {
    void addBarang(BarangJualModel barangJualModel);
    BarangJualModel updateBarang(Long idBarangJual, BarangJualModel barangJualModel);
    void deleteBarang(Long idBarang);
    BarangJualModel getBarangByIdBarang(Long idBarang);
}
