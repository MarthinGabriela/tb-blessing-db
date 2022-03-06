package systemapp.tbblessing.object;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PembeliInput {
    @JsonProperty("idPembeli")
    private String idPembeli;

    public String getIdPembeli() {
        return this.idPembeli;
    }

    public void setIdPembeli(String idPembeli) {
        this.idPembeli = idPembeli;
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
        this.alamat = namaPembeli;
    }

}