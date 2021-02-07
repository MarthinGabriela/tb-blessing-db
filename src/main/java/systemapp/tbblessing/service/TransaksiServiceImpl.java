package systemapp.tbblessing.service;

import systemapp.tbblessing.model.*;
import systemapp.tbblessing.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
@Transactional
public class TransaksiServiceImpl implements TransaksiService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    TransaksiDb transaksiDb;

    @Override
    public TransaksiModel addTransaksi(TransaksiModel transaksi) {
        return transaksiDb.save(transaksi);
    }

    @Override
    public TransaksiModel updateTransaksi(Long idTransaksi, TransaksiModel transaksi) {
        updateNominalTransaksi(transaksi);
        return transaksiDb.save(transaksi);
    }

    @Override
    public void deleteTransaksi(Long idTransaksi) {
        TransaksiModel transaksi = getTransaksiByIdTransaksi(idTransaksi);
        transaksiDb.delete(transaksi);
    }

    @Override
    public TransaksiModel getTransaksiByIdTransaksi(Long idTransaksi) {
        Optional<TransaksiModel> transaksi = transaksiDb.findByIdTransaksi(idTransaksi);
        if(transaksi.isPresent()) {
            return transaksi.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<TransaksiModel> getTransaksiByNamaPembeli(String namaPembeli) {
        return transaksiDb.findByNamaPembeli(namaPembeli);
    }

    @Override
    public List<TransaksiModel> getAllTransaksi() {
        return transaksiDb.findAll();
    }

    @Override
    public TransaksiModel updateNominalTransaksi(TransaksiModel input) {
        Long nominal = 0L;
        TransaksiModel transaksi = getTransaksiByIdTransaksi(input.getIdTransaksi());

        try {
            for(int i = 0; i < transaksi.getListBarangJual().size(); i++) {
                nominal += transaksi.getListBarangJual().get(i).getHargaJual() * transaksi.getListBarangJual().get(i).getStockBarangJual();
            }
        } catch (NullPointerException e) {

        }

        Long price = (long) 100 - transaksi.getDiskon();
        Long drnominal = (long) (nominal * price)/100;
        nominal = drnominal;

        try {
            for(int i = 0; i < transaksi.getListBarangRetur().size(); i++) {
                nominal -= transaksi.getListBarangRetur().get(i).getHargaRetur() * transaksi.getListBarangRetur().get(i).getStockBarangRetur();
            }
        } catch (NullPointerException e) {
            
        }

        transaksi.setNominalTransaksi(nominal);
        return transaksiDb.save(transaksi);
    }

    @Override
    public TransaksiModel updateHutangTransaksi(TransaksiModel transaksi) {
        Long nominal = transaksi.getNominalTransaksi();
        Long pembayaran = 0L;

        for(int i = 0; i < transaksi.getListPembayaran().size(); i++) {
            pembayaran += transaksi.getListPembayaran().get(i).getPembayaran();
        }

        transaksi.setHutangTransaksi(nominal - pembayaran);
        return transaksiDb.save(transaksi);
    }

    @Override
    public List<TransaksiModel> getAllTransaksiPage(Long page) {
        return transaksiDb.findTop10ByOrderByIdTransaksiDesc();
    }

    @Override
    public List<TransaksiModel> getTransaksiByPage(Long input) {
        return transaksiDb.findByIdTransaksiBetween(input - 9, input);
    }

    @Override
    public TransaksiModel getLatest(){
        List<TransaksiModel> list = transaksiDb.findTop1ByOrderByIdTransaksiDesc();

        return list.get(0);
    }

    @Override
    public TransaksiModel getFirst(){
        List<TransaksiModel> list = transaksiDb.findTop1ByOrderByIdTransaksi();

        return list.get(0);
    }

    @Override
    public List<TransaksiModel> getTransaksiByDate(String start, String end, long page) {
        Date starting = null;
        Date ending = null;
        try{
            if(start != null) {
                starting = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            }
            if(end != null) {
                ending =new SimpleDateFormat("yyyy-MM-dd").parse(end);
            }
        } catch (Exception e) {
        }

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransaksiModel> query = cb.createQuery(TransaksiModel.class);
        Root<TransaksiModel> tm = query.from(TransaksiModel.class);

        Path<Date> tanggal = tm.get("tanggalTransaksi");
        List<Predicate> predicates = new ArrayList<>();

        if(starting != null) {
            predicates.add(cb.greaterThanOrEqualTo( tanggal, starting));
        }
        if(ending != null) {
            predicates.add(cb.lessThanOrEqualTo( tanggal, ending));
        }

        query.select(tm).where(predicates.toArray(new Predicate[]{})).orderBy(cb.desc(tanggal));

        return entityManager
                    .createQuery(query)
                    .setFirstResult((int)(page-1) * 10)
                    .setMaxResults(10)
                    .getResultList();
    }
}