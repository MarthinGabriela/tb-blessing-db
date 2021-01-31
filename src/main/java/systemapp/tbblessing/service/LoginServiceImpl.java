package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginDb db;

    @Override
    public Optional<LoginModel> getLoginByToken(String token) {
        return db.findByToken(token);
    }
}
