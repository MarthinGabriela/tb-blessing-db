package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.NoSuchElementException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class BarangJualRestController {
    @Autowired
    BarangJualService barangJualService;

    @PostMapping(value = "/barang-jual")
    private ResponseEntity createBarang(@Valid @RequestBody BarangJualModel barang, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field barang tidak lengkap");
        } else {
            barangJualService.addBarang(barang);
            return ResponseEntity.ok("Add Barang sukses");
        }
    }

    @GetMapping(value = "/barang-jual/update/{idBarangJual}")
    private ResponseEntity updateBarang(@PathVariable(value = "idBarangJual") Long idBarangJual, @RequestBody BarangJualModel barang) {
        try {
            barangJualService.updateBarang(idBarangJual, barang);
            return ResponseEntity.ok("Update Barang sukses");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangJual) +" tidak valid"
            );
        }
    }

    @GetMapping(value = "/barang-jual/view/{idBarangJual}")
    private BarangJualModel viewBarang(@PathVariable(value = "idBarangJual") Long idBarangJual) {
        try {
            return barangJualService.getBarangByIdBarang(idBarangJual);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangJual) +" tidak valid"
            );
        }
    }

    @DeleteMapping(value = "/barang-jual/{idBarangJual}")
    private ResponseEntity<String> deleteBarang(@PathVariable("idBarangJual") Long idBarangJual) {
        try {
            barangJualService.deleteBarang(idBarangJual);
            return ResponseEntity.ok("Delete Barang sukses");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangJual) +" tidak valid"
            );
        }
    }
}