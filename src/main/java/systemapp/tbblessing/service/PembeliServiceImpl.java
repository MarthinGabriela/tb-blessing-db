package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional
public class PembeliServiceImpl implements PembeliService {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    PembeliDb PembeliDb;

    @Override
    public void addPembeli(PembeliModel pembeli){
        PembeliDb.save(pembeli);
    }

    @Override
    public PembeliModel updatePembeli(Long idPembeli, PembeliModel pembeli){
        getPembeliById(idPembeli);
        pembeli.setIdPembeli(idPembeli);
        return PembeliDb.save(pembeli);
    }

    @Override
    public PembeliModel getPembeliById(Long idPembeli){
        Optional<PembeliModel> Pembeli = PembeliDb.findByIdPembeli(idPembeli);
        if(Pembeli.isPresent()) {
            return Pembeli.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public PembeliModel getPembeliByNamaPembeli(String namaPembeli){
        Optional<PembeliModel> Pembeli = PembeliDb.findByNamaPembeli(namaPembeli);
        if(Pembeli.isPresent()) {
            return Pembeli.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<PembeliModel> getAll() {
        return PembeliDb.findAll();
    }

    @Override
    public void deletePembeli(Long idPembeli) {
        PembeliModel Pembeli = getPembeliById(idPembeli);
        PembeliDb.delete(Pembeli);
    }
    
    @Override
    public int getPembeliByNamaAndAlamat(String nama, String alamat) {
        String search_name = "";

        if(!(nama == "")) {
            search_name += " AND (LOWER(nama_pembeli) = '"+nama.toLowerCase()+"')";
        }
        if(!(alamat == "")) {
            search_name += " AND (LOWER(alamat) = '"+alamat.toLowerCase()+"')";
        }

        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM pembeli WHERE 1=1 "+search_name);
        int count = ((Number) query.getSingleResult()).intValue();
        return count;
    }

    @Override
    public Page<PembeliModel> findAllBySearch(String search, PageRequest pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        String search_name = "";

        if(!(search == "")) {
            search_name += " AND (LOWER(nama_pembeli) LIKE '%"+search.toLowerCase()+"%' OR LOWER(alamat) LIKE '%"+search.toLowerCase()+"%')";
        }

        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM pembeli WHERE 1=1 "+search_name);
        int count = ((Number) query.getSingleResult()).intValue();
        
        Query query2 = entityManager.createNativeQuery("SELECT * FROM pembeli WHERE 1=1 "+search_name+" ORDER BY nama_pembeli ASC", PembeliModel.class);
        query2.setFirstResult((pageNumber) * pageSize);
        query2.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
        List<PembeliModel> res = query2.getResultList();
        Page<PembeliModel> result = new PageImpl<>(res, pageable, count);
        return result;
    }
}
