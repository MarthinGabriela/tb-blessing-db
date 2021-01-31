package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import java.util.List;

public interface PembayaranService {
    void addPembayaran(PembayaranModel pembayaran);
    PembayaranModel updatePembayaran(Long idPembayaran, PembayaranModel pembayaran);
    void deletePembayaran(Long idPembayaran);
    PembayaranModel getPembayaranByIdPembayaran(Long idPembayaran);
    List<PembayaranModel> getAllPembayaran();
}
