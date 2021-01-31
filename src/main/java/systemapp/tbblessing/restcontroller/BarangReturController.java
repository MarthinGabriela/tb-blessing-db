package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
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
public class BarangReturController {
    @Autowired
    BarangReturService barangReturService;

    @PostMapping(value = "/barang-retur")
    private ResponseEntity createBarang(@Valid @RequestBody BarangReturModel barang, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field barang tidak lengkap");
        } else {
            barangReturService.addBarang(barang);
            return ResponseEntity.ok("Add Barang sukses");
        }
    }

    @GetMapping(value = "/barang-retur/update/{idBarangRetur}")
    private ResponseEntity updateBarang(@PathVariable(value = "idBarangRetur") Long idBarangRetur, @RequestBody BarangReturModel barang) {
        try {
            barangReturService.updateBarang(idBarangRetur, barang);
            return ResponseEntity.ok("Update Barang sukses");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangRetur) +" tidak valid"
            );
        }
    }

    @GetMapping(value = "/barang-retur/view/{idBarangRetur}")
    private BarangReturModel viewBarang(@PathVariable(value = "idBarangRetur") Long idBarangRetur) {
        try {
            return barangReturService.getBarangByIdBarang(idBarangRetur);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangRetur) +" tidak valid"
            );
        }
    }

    @DeleteMapping(value = "/barang-retur/{idBarangRetur}")
    private ResponseEntity<String> deleteBarang(@PathVariable("idBarangRetur") Long idBarangRetur) {
        try {
            barangReturService.deleteBarang(idBarangRetur);
            return ResponseEntity.ok("Delete Barang sukses");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id Barang "+ String.valueOf(idBarangRetur) +" tidak valid"
            );
        }
    }
}
