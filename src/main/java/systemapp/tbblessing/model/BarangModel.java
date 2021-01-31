package systemapp.tbblessing.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@Entity
@Table(name = "barang")
public class BarangModel implements Serializable {
    @Id
    @Column(name = "id_barang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarang;

    public Long getIdBarang() {
        return this.idBarang;
    }

    public void setIdBarang(Long idBarang) {
        this.idBarang = idBarang;
    }

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_barang", nullable = false)
    private String namaBarang;

    public String getNamaBarang() {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    @NotNull
    @Size(max = 20)
    @Column(name = "satuan_barang", nullable = false)
    private String satuanBarang;

    public String getSatuanBarang() {
        return this.satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
    }


    @NotNull
    @Column(name = "stock_barang", nullable = false)
    private Long stockBarang;

    public Long getStockBarang() {
        return this.stockBarang;
    }

    public void setStockBarang(Long stockBarang) {
        this.stockBarang = stockBarang;
    }

    @NotNull
    @Column(name = "harga_beli_barang", nullable = false)
    private Long hargaBeliBarang;

    public Long getHargaBeliBarang() {
        return this.hargaBeliBarang;
    }

    public void setHargaBeliBarang(Long hargaBeliBarang) {
        this.hargaBeliBarang = hargaBeliBarang;
    }

    @OneToMany(mappedBy = "barangModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BarangJualModel> listBarangJual;

    public List<BarangJualModel> getListBarangJual() {
        return this.listBarangJual;
    }

    public void setListBarangJual(List<BarangJualModel> listBarangJual) {
        this.listBarangJual = listBarangJual;
    }

    public void addListBarangJual(BarangJualModel barangJualModel) {
        this.listBarangJual.add(barangJualModel);
    }

    @OneToMany(mappedBy = "barangModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BarangReturModel> listBarangRetur;

    public List<BarangReturModel> getListBarangRetur() {
        return this.listBarangRetur;
    }

    public void setListBarangRetur(List<BarangReturModel> listBarangRetur) {
        this.listBarangRetur = listBarangRetur;
    }

    public void addListBarangRetur(BarangReturModel barangReturModel) {
        this.listBarangRetur.add(barangReturModel);
    }
}