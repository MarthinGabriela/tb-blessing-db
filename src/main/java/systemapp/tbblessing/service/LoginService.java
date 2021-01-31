package systemapp.tbblessing.service;

import java.util.Optional;
import systemapp.tbblessing.model.*;

public interface LoginService {
    Optional<LoginModel> getLoginByToken(String token);
}
