package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.object.BarangJualInput;
import systemapp.tbblessing.object.BarangReturInput;
import systemapp.tbblessing.object.BaseResponse;
import systemapp.tbblessing.object.PageResponse;
import systemapp.tbblessing.object.TransaksiInput;
import systemapp.tbblessing.object.UpdateTransaksiInput;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
                barangJ = new BarangJualModel();
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

            barangR = new BarangReturModel();
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

    @GetMapping(value = "/list-transaksi")
    private PageResponse viewListTransaksi(
        @RequestParam(name="page") Long page,
        @RequestParam(name="start", required = false) String start,
        @RequestParam(name="end", required = false) String end) {
            try{
                if(start == null && end == null) {
                    long id = transaksiService.getLatest().getIdTransaksi();
                    long paging = 10*(page-1);
                    if(!(paging == 0L)) {
                        id = id - paging;
                    }

                    if(transaksiService.getLatest().getIdTransaksi() < 10) {
                        List<TransaksiModel> list = transaksiService.getAllTransaksi();

                        PageResponse response = new PageResponse();
                        response.setStatus(200);
                        response.setMessage(false);
                        response.setResult(list);

                        return response;
                    } else if(id <= 0 && page > 1) {
                        List<TransaksiModel> list = transaksiService.getAllTransaksi();

                        PageResponse response = new PageResponse();
                        response.setStatus(200);
                        response.setMessage(false);
                        response.setResult(list);

                        return response;
                    } else {
                        List<TransaksiModel> list = transaksiService.getTransaksiByPage(id);

                        PageResponse response = new PageResponse();
                        response.setStatus(200);
                        Long first = transaksiService.getFirst().getIdTransaksi();
                        if(list.get(0).getIdTransaksi().equals(first)) {
                            response.setMessage(false);
                        } else {
                            response.setMessage(true);
                        }
                        Collections.reverse(list);
                        response.setResult(list);

                        return response;
                    }
                } else {
                    try {
                        List<TransaksiModel> list = transaksiService.getTransaksiByDate(start, end, page);

                        PageResponse response = new PageResponse();
                        response.setStatus(200);
                        if(list.get(0).getIdTransaksi().equals(1L)) {
                            response.setMessage(false);
                        } else {
                            response.setMessage(true);
                        }
                        Collections.reverse(list);
                        response.setResult(list);

                        return response;
                    } catch(Exception e) {
                        PageResponse response = new PageResponse();
                        response.setStatus(200);
                        response.setMessage(false);
                        response.setResult(new ArrayList<TransaksiModel>());
                        return response;
                    }
                }
            } catch(Exception e) {
                PageResponse response = new PageResponse();
                response.setStatus(200);
                response.setMessage(false);
                response.setResult(new ArrayList<TransaksiModel>());
                return response;
            }
    }

    @PutMapping(value = "/transaksi/update/{idTransaksi}")
    private BaseResponse updateTransaksi(@PathVariable(value = "idTransaksi") Long idTransaksi, @RequestBody UpdateTransaksiInput transaksi) {
        TransaksiModel newTransaksi = updateTransaksiConverter(transaksi);
        List<Long> listJual = new ArrayList<>();
        List<Long> listRetur = new ArrayList<>();
        BarangModel barangModel = new BarangModel();

        for(BarangJualModel barangJual : transaksiService.getTransaksiByIdTransaksi(idTransaksi).getListBarangJual()) {
            listJual.add(barangJual.getIdBarangJual());
            barangModel = barangJual.getBarangModel();

            barangModel.setStockBarang(barangModel.getStockBarang() + barangJual.getStockBarangJual());
            barangService.updateBarang(barangModel.getIdBarang(), barangModel);
        }

        for(BarangReturModel barangRetur : transaksiService.getTransaksiByIdTransaksi(idTransaksi).getListBarangRetur()) {
            listRetur.add(barangRetur.getIdBarangRetur());

            barangModel = barangRetur.getBarangModel();

            barangModel.setStockBarang(barangModel.getStockBarang() - barangRetur.getStockBarangRetur());
            barangService.updateBarang(barangModel.getIdBarang(), barangModel);
        }

        transaksiService.updateTransaksi(idTransaksi, newTransaksi);

        BarangModel barang = new BarangModel();
        BarangJualModel barangJ = new BarangJualModel();
        BarangReturModel barangR = new BarangReturModel();

        for(BarangJualInput barangJual : transaksi.getListBarangJual()) {
            try {
                barang = barangService.getBarangByNamaBarang(barangJual.getNamaBarang());
            } catch(NoSuchElementException e) {
                continue;
            }
            barangJ = new BarangJualModel();
                barangJ.setHargaJual(barangJual.getHarga());
                barangJ.setStockBarangJual(barangJual.getStock());
                barangJ.setBarangModel(barangService.getBarangByNamaBarang(barangJual.getNamaBarang()));
                barangJ.setTransaksiModel(newTransaksi);
                barangJService.addBarang(barangJ);
                System.out.println(barangJ.getIdBarangJual());
                newTransaksi.addListBarangJual(barangJ);

                barang.setStockBarang(barang.getStockBarang() - barangJ.getStockBarangJual());
                barangService.updateBarang(barang.getIdBarang(), barang);

        }

        for(BarangReturInput barangRetur : transaksi.getListBarangRetur()) {

            try {
                barang = barangService.getBarangByNamaBarang(barangRetur.getNamaBarang());
            } catch(NoSuchElementException e) {
                continue;
            }
            barangR = new BarangReturModel();
            barangR.setHargaRetur(barangRetur.getHarga());
            barangR.setStockBarangRetur(barangRetur.getStock());
            barangR.setBarangModel(barangService.getBarangByNamaBarang(barangRetur.getNamaBarang()));
            barangR.setTransaksiModel(newTransaksi);
            barangRService.addBarang(barangR);
            newTransaksi.addListBarangRetur(barangR);

            barang.setStockBarang(barang.getStockBarang() + barangR.getStockBarangRetur());
            barangService.updateBarang(barang.getIdBarang(), barang);
        }

        transaksiService.updateTransaksi(idTransaksi, newTransaksi);
        for(Long l : listJual) {
            barangJService.deleteBarang(l);
        }

        for(long l : listRetur) {
            barangRService.deleteBarang(l);
        }

        transaksiService.updateTransaksi(idTransaksi, newTransaksi);
        newTransaksi = transaksiService.getTransaksiByIdTransaksi(idTransaksi);

        transaksiService.updateNominalTransaksi(newTransaksi);
        transaksiService.updateHutangTransaksi(newTransaksi);

        BaseResponse result = new BaseResponse();
        result.setStatus(200);
        result.setMessage("Update Transaksi Berhasil");
        result.setResult(newTransaksi);

        return result;
    }

    @GetMapping(value = "/transaksi/view/{idTransaksi}")
    private BaseResponse viewTransaksi(@PathVariable(value = "idTransaksi") Long idTransaksi) {
        try {
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Transaksi Tersedia");
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

    private TransaksiModel updateTransaksiConverter(UpdateTransaksiInput input) {
        TransaksiModel transaksi = new TransaksiModel();
        transaksi.setIdTransaksi(input.getIdTransaksi());
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