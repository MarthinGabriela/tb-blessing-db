package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.object.*;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class PembayaranRestController {
    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiService transaksiService;

    @PostMapping(value = "/pembayaran")
    private BaseResponse createPembayaran(@Valid @RequestBody PembayaranInput pembayaran, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            return new BaseResponse(500, "Input Field for Pembayaran is missing", null);
        } else {
            PembayaranModel bayar = new PembayaranModel();
            bayar.setPembayaran(pembayaran.getNominal());
            bayar.setTanggalPembayaran(pembayaran.getTanggal());
            bayar.setTransaksiModel(transaksiService.getTransaksiByIdTransaksi(pembayaran.getIdTransaksi()));

            pembayaranService.addPembayaran(bayar);
            transaksiService.updateHutangTransaksi(bayar.getTransaksiModel());
            return new BaseResponse(200, "Pembayaran berhasil ditambahkan ke dalam database", bayar);
        }
    }

    @GetMapping(value = "/list-pembayaran")
    private BaseResponse viewListPembayaran() {
        return new BaseResponse(200, "List Pembayaran Get", pembayaranService.getAllPembayaran());
    }

    @GetMapping(value = "/pembayaran/update/{idPembayaran}")
    private BaseResponse updatePembayaran(@PathVariable(value = "idPembayaran") Long idPembayaran, @RequestBody PembayaranModel pembayaran) {
        try {
            pembayaranService.updatePembayaran(idPembayaran, pembayaran);
            transaksiService.updateHutangTransaksi(pembayaran.getTransaksiModel());
            return new BaseResponse(200, "Pembayaran berhasil diedit", pembayaran);
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Id Pembayaran tidak tersedia", null);
        }
    }

    @GetMapping(value = "/pembayaran/view/{idPembayaran}")
    private BaseResponse viewPembayaran(@PathVariable(value = "idPembayaran") Long idPembayaran) {
        try {
            pembayaranService.getPembayaranByIdPembayaran(idPembayaran);
            return new BaseResponse(200, "Pembayaran berhasil diedit", pembayaranService.getPembayaranByIdPembayaran(idPembayaran));
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Id Pembayaran tidak tersedia", null);
        }
    }

    @DeleteMapping(value = "/pembayaran/{idPembayaran}")
    private BaseResponse deletePembayaran(@PathVariable("idPembayaran") Long idPembayaran) {
        try {
            TransaksiModel transaksi = pembayaranService.getPembayaranByIdPembayaran(idPembayaran).getTransaksiModel();
            pembayaranService.deletePembayaran(idPembayaran);
            transaksiService.updateHutangTransaksi(transaksi);
            return new BaseResponse(200, "Pembayaran berhasil dihapus", null);
        } catch (NoSuchElementException e) {
            return new BaseResponse(200, "Id Pembayaran tidak tersedia", null);
        }
    }
}