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
public class BarangJualServiceImpl implements BarangJualService {
    @Autowired
    BarangJualDb barangJualDb;

    @Override
    public void addBarang(BarangJualModel barangJualModel) {
        barangJualDb.save(barangJualModel);
    }

    @Override
    public BarangJualModel updateBarang(Long idBarangJual, BarangJualModel barangJualModel) {
        BarangJualModel barangJual = getBarangByIdBarang(idBarangJual);
        return barangJualDb.save(barangJual);
    }

    @Override
    public void deleteBarang(Long idBarang) {
        BarangJualModel barangJual = getBarangByIdBarang(idBarang);
        barangJualDb.delete(barangJual);
    }

    @Override
    public BarangJualModel getBarangByIdBarang(Long idBarang) {
        Optional<BarangJualModel> barang = barangJualDb.findByIdBarangJual(idBarang);
        if(barang.isPresent()) {
            return barang.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteByTransaksi(TransaksiModel transaksi){
        barangJualDb.deleteByTransaksiModel(transaksi);
    }
}
