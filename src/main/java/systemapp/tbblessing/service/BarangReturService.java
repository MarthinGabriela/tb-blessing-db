package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;

public interface BarangReturService {
    void addBarang(BarangReturModel barang);
    BarangReturModel updateBarang(Long idBarang, BarangReturModel barang);
    void deleteBarang(Long idBarang);
    BarangReturModel getBarangByIdBarang(Long idBarang);
}
