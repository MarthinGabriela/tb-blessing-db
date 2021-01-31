package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PembayaranServiceImpl implements PembayaranService {
    @Autowired
    PembayaranDb pembayaranDb;

    @Override
    public void addPembayaran(PembayaranModel pembayaran) {
        pembayaranDb.save(pembayaran);
    }

    @Override
    public PembayaranModel updatePembayaran(Long idPembayaran, PembayaranModel pembayaran) {
        PembayaranModel updated = getPembayaranByIdPembayaran(idPembayaran);
        return pembayaranDb.save(updated);
    }

    @Override
    public void deletePembayaran(Long idPembayaran) {
        PembayaranModel pembayaran = getPembayaranByIdPembayaran(idPembayaran);
        pembayaranDb.delete(pembayaran);
    }

    @Override
    public PembayaranModel getPembayaranByIdPembayaran(Long idPembayaran) {
        Optional<PembayaranModel> pembayaran = pembayaranDb.findByIdPembayaran(idPembayaran);
        if(pembayaran.isPresent()) {
            return pembayaran.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<PembayaranModel> getAllPembayaran() {
        return pembayaranDb.findAll();
    }
}
