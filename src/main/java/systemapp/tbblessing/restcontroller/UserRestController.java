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
public class UserRestController {
    @Autowired
    private LoginService service;
    
    @PostMapping("/login")
    private BaseResponse handleLogin(
        @Valid @RequestBody LoginModel login
    ) {
        try {
            LoginModel log = service.getLoginByToken(login.getToken()).get();
            return new BaseResponse(200, "Token tersedia", log);
        } catch(NoSuchElementException e) {
            return new BaseResponse(404, "Token tidak tersedia di dalam Database", null);
        }
    }
}