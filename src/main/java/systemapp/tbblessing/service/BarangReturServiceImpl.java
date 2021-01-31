package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BarangReturServiceImpl implements BarangReturService {
    @Autowired
    BarangReturDb barangReturDb;

    @Override
    public void addBarang(BarangReturModel barang) {
        barangReturDb.save(barang);
    }

    @Override
    public BarangReturModel updateBarang(Long idBarang, BarangReturModel barang) {
        BarangReturModel barangRetur = getBarangByIdBarang(idBarang);
        return barangReturDb.save(barangRetur);
    }

    @Override
    public void deleteBarang(Long idBarang) {
        BarangReturModel barang = getBarangByIdBarang(idBarang);
        barangReturDb.delete(barang);
    }

    @Override
    public BarangReturModel getBarangByIdBarang(Long idBarang) {
        Optional<BarangReturModel> barang = barangReturDb.findByIdBarangRetur(idBarang);
        if(barang.isPresent()) {
            return barang.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
