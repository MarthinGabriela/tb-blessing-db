package systemapp.tbblessing.object;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PembayaranInput {
    @JsonProperty("nominal")
    private Long nominal;

    @JsonProperty("idTransaksi")
    private Long idTransaksi;

    @JsonProperty("tanggal")
    private Date tanggal;

    public Long getNominal() {
        return this.nominal;
    }

    public void setNominal(Long nominal) {
        this.nominal = nominal;
    }

    public Long getIdTransaksi() {
        return this.idTransaksi;
    }

    public void setIdTransaksi(Long idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

}
