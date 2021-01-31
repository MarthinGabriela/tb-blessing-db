package systemapp.tbblessing.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BarangJualInput {
    @JsonProperty("stock")
    private Long stock;

    public Long getStock() {
        return this.stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @JsonProperty("harga")
    private Long harga;

    public Long getHarga() {
        return this.harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    @JsonProperty("namaBarang")
    private String namaBarang;

    public String getNamaBarang() {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

}