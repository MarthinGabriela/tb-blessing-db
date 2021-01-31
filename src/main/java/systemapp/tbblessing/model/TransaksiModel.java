package systemapp.tbblessing.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "transaksi")
public class TransaksiModel implements Serializable {
    @Id
    @Column(name = "id_transaksi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaksi;

    public Long getIdTransaksi() {
        return this.idTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    @NotNull
    @Size(max = 30)
    @Column(name = "nama_pembeli", nullable = false)
    private String namaPembeli;

    public String getNamaPembeli() {
        return this.namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    @NotNull
    @Size(max = 60)
    @Column(name = "alamat", nullable = false)
    private String alamat;

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @NotNull
    @Column(name = "diskon", nullable = false)
    private int diskon;

    public int getDiskon() {
        return this.diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_transaksi")
    private Date tanggalTransaksi;

    public Date getTanggalTransaksi() {
        return this.tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    @OneToMany(mappedBy = "transaksiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "transaksiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "transaksiModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembayaranModel> listPembayaran;

    public List<PembayaranModel> getListPembayaran() {
        return this.listPembayaran;
    }

    public void setListPembayaran(List<PembayaranModel> listPembayaran) {
        this.listPembayaran = listPembayaran;
    }

    public void addListPembayaran(PembayaranModel pembayaranModel) {
        this.listPembayaran.add(pembayaranModel);
    }

    @NotNull
    @Column(name = "nominal_transaksi", nullable = false)
    private Long nominalTransaksi;

    public Long getNominalTransaksi() {
        return this.nominalTransaksi;
    }

    public void setNominalTransaksi(Long nominalTransaksi) {
        this.nominalTransaksi = nominalTransaksi;
    }

    @NotNull
    @Column(name = "hutang_transaksi", nullable = false)
    private Long hutangTransaksi;

    public Long getHutangTransaksi() {
        return this.hutangTransaksi;
    }

    public void setHutangTransaksi(Long hutangTransaksi) {
        this.hutangTransaksi = hutangTransaksi;
    }

}