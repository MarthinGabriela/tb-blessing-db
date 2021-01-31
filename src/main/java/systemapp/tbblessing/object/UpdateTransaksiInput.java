package systemapp.tbblessing.object;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTransaksiInput {
    @JsonProperty("idTransaksi")
    private Long idTransaksi;

    public Long getIdTransaksi() {
        return this.idTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

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
}