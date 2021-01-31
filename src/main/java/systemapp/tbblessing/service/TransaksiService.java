package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import java.util.List;

public interface TransaksiService {
    TransaksiModel addTransaksi(TransaksiModel transaksi);
    TransaksiModel updateTransaksi(Long idTransaksi, TransaksiModel transaksi);
    void deleteTransaksi(Long idTransaksi);
    TransaksiModel getTransaksiByIdTransaksi(Long idTransaksi);
    List<TransaksiModel> getTransaksiByNamaPembeli(String namaPembeli);
    List<TransaksiModel> getAllTransaksi();
    TransaksiModel updateNominalTransaksi(TransaksiModel transaksi);
    TransaksiModel updateHutangTransaksi(TransaksiModel transaksi);
    List<TransaksiModel> getAllTransaksiPage(Long page);
    List<TransaksiModel> getTransaksiByPage(Long input);
    TransaksiModel getLatest();
}
