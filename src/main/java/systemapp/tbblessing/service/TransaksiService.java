package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import java.time.ZonedDateTime;
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
    TransaksiModel getFirst();
	List<TransaksiModel> getTransaksiByDate(String start, String end, long page);
}
