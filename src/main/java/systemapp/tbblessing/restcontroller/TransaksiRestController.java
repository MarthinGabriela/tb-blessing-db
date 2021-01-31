package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.object.BarangJualInput;
import systemapp.tbblessing.object.BarangReturInput;
import systemapp.tbblessing.object.BaseResponse;
import systemapp.tbblessing.object.TransaksiInput;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class TransaksiRestController {
    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private BarangJualService barangJService;

    @Autowired
    private BarangReturService barangRService;

    @Autowired
    private PembayaranService pembayaranService;

    @PostMapping(value = "/transaksi")
    private BaseResponse createTransaksi(@Valid @RequestBody TransaksiInput input) {
        TransaksiModel transaksi = transaksiConverter(input);
        transaksiService.addTransaksi(transaksi);
        BarangModel barang = new BarangModel();
        BarangJualModel barangJ = new BarangJualModel();
        BarangReturModel barangR = new BarangReturModel();

        for(BarangJualInput barangJual : input.getListBarangJual()) {

            try {
                barang = barangService.getBarangByNamaBarang(barangJual.getNamaBarang());
            } catch(NoSuchElementException e) {
                continue;
            }

                barangJ.setHargaJual(barangJual.getHarga());
                barangJ.setStockBarangJual(barangJual.getStock());
                barangJ.setBarangModel(barangService.getBarangByNamaBarang(barangJual.getNamaBarang()));
                barangJ.setTransaksiModel(transaksi);
                barangJService.addBarang(barangJ);
                transaksi.addListBarangJual(barangJ);

                barang.setStockBarang(barang.getStockBarang() - barangJ.getStockBarangJual());
                barangService.updateBarang(barang.getIdBarang(), barang);

        }

        for(BarangReturInput barangRetur : input.getListBarangRetur()) {

            try {
                barang = barangService.getBarangByNamaBarang(barangRetur.getNamaBarang());
            } catch(NoSuchElementException e) {
                continue;
            }

            barangR.setHargaRetur(barangRetur.getHarga());
            barangR.setStockBarangRetur(barangRetur.getStock());
            barangR.setBarangModel(barangService.getBarangByNamaBarang(barangRetur.getNamaBarang()));
            barangR.setTransaksiModel(transaksi);
            barangRService.addBarang(barangR);
            transaksi.addListBarangRetur(barangR);

            barang.setStockBarang(barang.getStockBarang() + barangR.getStockBarangRetur());
            barangService.updateBarang(barang.getIdBarang(), barang);
        }

        if(input.getDP() != 0) {
            PembayaranModel firstPayment = new PembayaranModel();

            firstPayment.setPembayaran(input.getDP());
            firstPayment.setTanggalPembayaran(new Date());
            firstPayment.setTransaksiModel(transaksi);
            pembayaranService.addPembayaran(firstPayment);
            transaksi.addListPembayaran(firstPayment);
        }

        TransaksiModel updatedTransaksi = transaksiService.updateNominalTransaksi(transaksi);
        updatedTransaksi = transaksiService.updateHutangTransaksi(transaksi);
        
        BaseResponse response = new BaseResponse();
        response.setStatus(200);
        response.setMessage("Add Transaksi Sukses");
        response.setResult(updatedTransaksi);

        return response;
    }

    @GetMapping(value = "/list-transaksi/{page}")
    private BaseResponse viewListTransaksi(@PathVariable Long page) {
        long id = transaksiService.getLatest().getIdTransaksi();
        long paging = 10*(page-1);
        if(!(paging == 0L)) {
            id = id - paging;
        }

        if(id <= 0 && page > 1) {
            List<TransaksiModel> list = transaksiService.getAllTransaksi();

            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Last Transaksi");
            response.setResult(list);

            return response;
        } else {
            List<TransaksiModel> list = transaksiService.getTransaksiByPage(id);

            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            if(list.get(0).getIdTransaksi().equals(1L)) {
                response.setMessage("Last Page");
            } else {
                response.setMessage("Next Page");
            }
            Collections.reverse(list);
            response.setResult(list);

            return response;
        }
    }

    /*
    @GetMapping(value = "list-transaksi/date")
    private BaseResponse viewListTransaksiByDate(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end) {
        Date starting = new SimpleDateFormat("dd-MMM-yyyy").parse(start);
        Date ending = new SimpleDateFormat("dd-MM-yyyy").parse(end);

        List<TransaksiModel> list = transaksiService.get
    } */

    @GetMapping(value = "/transaksi/update/{idTransaksi}")
    private BaseResponse updateTransaksi(@PathVariable(value = "idTransaksi") Long idTransaksi, @RequestBody TransaksiInput input) {
        try {
            TransaksiModel transaksiUpdate = new TransaksiModel();
            transaksiUpdate.setNamaPembeli(input.getNamaPembeli());
            transaksiUpdate.setAlamat(input.getAlamat());
            transaksiUpdate.setDiskon(input.getDiskon());
            Date updatedTanggal = new Date();

            try {
                updatedTanggal = new SimpleDateFormat("yyyy-MM-dd").parse(input.getTanggal());
            } catch(Exception e) {
            }
            transaksiUpdate.setTanggalTransaksi(updatedTanggal);

            transaksiUpdate.setHutangTransaksi(0L);
            transaksiUpdate.setNominalTransaksi(0L);
            transaksiUpdate.setListBarangJual(new ArrayList<BarangJualModel>());
            transaksiUpdate.setListBarangRetur(new ArrayList<BarangReturModel>());
            TransaksiModel transaksi = transaksiService.updateTransaksi(idTransaksi, transaksiUpdate);

            BarangModel barang = new BarangModel();
            BarangJualModel barangJ = new BarangJualModel();
            BarangReturModel barangR = new BarangReturModel();

            for(BarangJualInput barangJual : input.getListBarangJual()) {

                try {
                    barang = barangService.getBarangByNamaBarang(barangJual.getNamaBarang());
                } catch(NoSuchElementException e) {
                    continue;
                }

                    barangJ.setHargaJual(barangJual.getHarga());
                    barangJ.setStockBarangJual(barangJual.getStock());
                    barangJ.setBarangModel(barangService.getBarangByNamaBarang(barangJual.getNamaBarang()));
                    barangJ.setTransaksiModel(transaksi);
                    barangJService.addBarang(barangJ);
                    transaksi.addListBarangJual(barangJ);

                    barang.setStockBarang(barang.getStockBarang() - barangJ.getStockBarangJual());
                    barangService.updateBarang(barang.getIdBarang(), barang);
            }

            for(BarangReturInput barangRetur : input.getListBarangRetur()) {

                try {
                    barang = barangService.getBarangByNamaBarang(barangRetur.getNamaBarang());
                } catch(NoSuchElementException e) {
                    continue;
                }

                barangR.setHargaRetur(barangRetur.getHarga());
                barangR.setStockBarangRetur(barangRetur.getStock());
                barangR.setBarangModel(barangService.getBarangByNamaBarang(barangRetur.getNamaBarang()));
                barangR.setTransaksiModel(transaksi);
                barangRService.addBarang(barangR);
                transaksi.addListBarangRetur(barangR);

                barang.setStockBarang(barang.getStockBarang() + barangR.getStockBarangRetur());
                barangService.updateBarang(barang.getIdBarang(), barang);
            }

            TransaksiModel updatedTransaksi = transaksiService.updateNominalTransaksi(transaksi);
            updatedTransaksi = transaksiService.updateHutangTransaksi(transaksi);
            
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Add Transaksi Sukses");
            response.setResult(updatedTransaksi);

            return response;
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Id Transaksi tidak tersedia", null);
        }
    }

    @GetMapping(value = "/transaksi/view/{idTransaksi}")
    private BaseResponse viewTransaksi(@PathVariable(value = "idTransaksi") Long idTransaksi) {
        try {
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Update Transaksi Sukses");
            response.setResult(transaksiService.getTransaksiByIdTransaksi(idTransaksi));
            return response;
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Id Transaksi tidak tersedia", null);
        }
    }

    @GetMapping(value = "/transaksi/search/{namaPembeli}")
    private BaseResponse viewNamaTransaksi(@PathVariable(value = "namaPembeli") String namaPembeli) {
        try {
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Transaksi tersedia");
            response.setResult(transaksiService.getTransaksiByNamaPembeli(namaPembeli));
            return response;
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Transaksi tidak tersedia", null);
        }
    }

    @DeleteMapping(value = "/transaksi/{idTransaksi}")
    private BaseResponse deleteTransaksi(@PathVariable("idTransaksi") Long idTransaksi) {
        try {
            BarangModel barang = new BarangModel();
            TransaksiModel transaksi = transaksiService.getTransaksiByIdTransaksi(idTransaksi);

            for(BarangJualModel barangJual : transaksi.getListBarangJual()) {

                try {
                    barang = barangJual.getBarangModel();
                } catch(NoSuchElementException e) {
                    continue;
                }
            
                barang.setStockBarang(barang.getStockBarang() + barangJual.getStockBarangJual());
                barangService.updateBarang(barang.getIdBarang(), barang);
            }
    
            for(BarangReturModel barangRetur : transaksi.getListBarangRetur()) {
    
                try {
                    barang = barangRetur.getBarangModel();
                } catch(NoSuchElementException e) {
                    continue;
                }
    
                barang.setStockBarang(barang.getStockBarang() - barangRetur.getStockBarangRetur());
                barangService.updateBarang(barang.getIdBarang(), barang);
            }

            transaksiService.deleteTransaksi(idTransaksi);
            return new BaseResponse(200, "Barang berhasil dihapus", null);
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Id Transaksi tidak tersedia", null);
        }
    }

    private TransaksiModel transaksiConverter(TransaksiInput input) {
        TransaksiModel transaksi = new TransaksiModel();
        transaksi.setAlamat(input.getAlamat());
        transaksi.setDiskon(input.getDiskon());
        transaksi.setNamaPembeli(input.getNamaPembeli());
        transaksi.setTanggalTransaksi(new Date());
        transaksi.setHutangTransaksi(0L);
        transaksi.setNominalTransaksi(0L);
        transaksi.setListBarangJual(new ArrayList<BarangJualModel>());
        transaksi.setListBarangRetur(new ArrayList<BarangReturModel>());
        transaksi.setListPembayaran(new ArrayList<PembayaranModel>());
        return transaksi;
    }
}