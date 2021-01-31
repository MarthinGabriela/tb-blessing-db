package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.object.BaseResponse;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class BarangRestController {
    @Autowired
    private BarangService barangService;

    @PostMapping(value = "/barang")
    private BaseResponse createBarang(@Valid @RequestBody BarangModel input, BindingResult result) {
        
        if(result.hasFieldErrors()) {
            return new BaseResponse(500, "Field Barang Error", null);
        }
        barangService.addBarang(input);
        BaseResponse response = new BaseResponse(200, "Barang telah ditambahkan ke database", input);
        return response;
    }

    @GetMapping(value = "/list-barang")
    private BaseResponse viewListBarang() {
        List<BarangModel> list = barangService.getAllBarang();
        BaseResponse response = new BaseResponse();
        response.setStatus(200);
        response.setMessage("List Barang Get");
        response.setResult(list);

        return response;
    }

    @GetMapping(value = "/list-barang/less/{kuantitas}")
    private BaseResponse viewListbarang(@PathVariable(value = "kuantitas") Long kuantitas) {
        List<BarangModel> list = barangService.getByStockBarangLessThanEqual(kuantitas);
        BaseResponse response = new BaseResponse();
        response.setStatus(200);
        response.setMessage("List Barang Less Get " + kuantitas);
        response.setResult(list);

        return response;
    }

    @PutMapping(value = "/barang/update/{idBarang}")
    private BaseResponse updateBarang(@PathVariable(value = "idBarang") Long idBarang, @RequestBody BarangModel barang) {
        try {
            BarangModel update = barangService.updateBarang(idBarang, barang);
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Update barang Id " + idBarang + " sukses");
            response.setResult(update);

            return response;
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "ID Barang Tidak tersedia", null);
        }
    }

    @GetMapping(value = "/barang/view/{idBarang}")
    private BaseResponse viewBarang(@PathVariable(value = "idBarang") Long idBarang) {
        try {
            BarangModel barang = barangService.getBarangByIdBarang(idBarang);
            BaseResponse response = new BaseResponse();
            response.setStatus(200);
            response.setMessage("Get Barang");
            response.setResult(barang);

            return response;
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "ID Barang tidak tersedia", null);
        }
    }

    @GetMapping(value = "/barang/search/{nama}")
    private BaseResponse viewNamaBarang(@PathVariable(value = "nama") String nama) {
        try {
            return new BaseResponse(200, "Barang tersedia", barangService.getBarangByNamaBarang(nama));
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "Barang tidak tersedia", null);
        }
    }

    // Jika perlu Hapus barang, kemungkinan sih enggak kepake tapi jaga-jaga aja
    @DeleteMapping(value = "/barang/{idBarang}")
    private BaseResponse deleteBarang(@PathVariable("idBarang") Long idBarang) {
        try {
            barangService.deleteBarang(idBarang);
            return new BaseResponse(200, "Delete Barang sukses", idBarang);
        } catch (NoSuchElementException e) {
            return new BaseResponse(404, "ID Barang tidak tersedia", null);
        }
    }
}
