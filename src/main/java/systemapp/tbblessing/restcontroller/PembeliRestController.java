package systemapp.tbblessing.restcontroller;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class PembeliRestController {
    @Autowired
    PembeliService PembeliService;

    @PostMapping(value = "/pembeli")
    private ResponseEntity<Map<String, Object>> createPembeli(@Valid @RequestBody PembeliModel pembeli, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("message", "Field pembeli tidak lengkap");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            int data = PembeliService.getPembeliByNamaAndAlamat(pembeli.getNamaPembeli(), pembeli.getAlamat());
            if(data == 0) {
                try {
                    PembeliService.addPembeli(pembeli);
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", 200);
                    response.put("message", "Tambah pembeli sukses");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } catch (Exception ex) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", 500);
                    response.put("message", "Tambah pembeli gagal");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("status", 200);
                response.put("message", "Data pembeli sudah ada");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @PutMapping(value = "/pembeli/update/{idPembeli}")
    private ResponseEntity<Map<String, Object>> updatePembeli(@PathVariable(value = "idPembeli") Long idPembeli, @RequestBody PembeliModel pembeli) {
        try {
            PembeliService.updatePembeli(idPembeli, pembeli);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "Update pembeli sukses");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Error Id pembeli "+ String.valueOf(idPembeli) +" tidak valid");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/list-pembeli")
	public ResponseEntity<Map<String, Object>> listPembeli(@RequestParam("draw") int draw,
        @RequestParam("start") int start,
        @RequestParam("length") int length,
        @RequestParam(name = "search[value]", required = false) String search
        ) {
		try {
			int page = start / length; //Calculate page number
            PageRequest pageable = PageRequest.of(
                    page,
                    length,
                    Sort.by(Sort.Direction.ASC, "namaPembeli")
            );

            Page<PembeliModel> responseData;
            if(search == "") {
                responseData = PembeliService.findAllBySearch("", pageable);
            } else {
                responseData = PembeliService.findAllBySearch(search, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", responseData.getNumber() + 1);
            response.put("recordsTotal", responseData.getTotalElements());
            response.put("recordsFiltered", responseData.getTotalElements());
            response.put("totalPages", responseData.getTotalPages());
            response.put("data", responseData.getContent());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @GetMapping(value = "/pembeli")
    private ResponseEntity<Map<String, Object>> getPembeli() {
        try {
            List<PembeliModel> responseData = PembeliService.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("data", responseData);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/pembeli/view/{idPembeli}")
    private PembeliModel viewPembeli(@PathVariable(value = "idPembeli") Long idPembeli) {
        try {
            return PembeliService.getPembeliById(idPembeli);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Error Id pembeli "+ String.valueOf(idPembeli) +" tidak valid"
            );
        }
    }

    @DeleteMapping(value = "/pembeli/{idPembeli}")
    private ResponseEntity<Map<String, Object>> deletePembeli(@PathVariable("idPembeli") Long idPembeli) {
        try {
            PembeliService.deletePembeli(idPembeli);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 200);
            response.put("message", "Delete pembeli sukses");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.NOT_FOUND);
            response.put("message", "Error Id pembeli "+ String.valueOf(idPembeli) +" tidak valid");

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}