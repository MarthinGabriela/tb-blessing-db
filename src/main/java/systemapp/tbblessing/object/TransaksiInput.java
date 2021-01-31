package systemapp.tbblessing.object;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransaksiInput {
    @JsonProperty("namaPembeli")
    private String namaPembeli;

    public String getNamaPembeli() {
        return this.namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    @JsonProperty("alamat")
    private String alamat;

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @JsonProperty("diskon")
    private int diskon;

    public int getDiskon() {
        return this.diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    @JsonProperty("listBarangJual")
    private List<BarangJualInput> listBarangJual;

    public List<BarangJualInput> getListBarangJual() {
        return this.listBarangJual;
    }

    public void setListBarangJual(List<BarangJualInput> listBarangJual) {
        this.listBarangJual = listBarangJual;
    }

    @JsonProperty("listBarangRetur")
    private List<BarangReturInput> listBarangRetur;

    public List<BarangReturInput> getListBarangRetur() {
        return this.listBarangRetur;
    }

    public void setListBarangRetur(List<BarangReturInput> listBarangRetur) {
        this.listBarangRetur = listBarangRetur;
    }

    @JsonProperty("tanggalTransaksi")
    private String tanggal;

    public String getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @JsonProperty("DP")
    private Long DP;

    public Long getDP() {
        return this.DP;
    }

    public void setDP(Long DP) {
        this.DP = DP;
    }
}
