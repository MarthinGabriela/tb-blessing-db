package systemapp.tbblessing.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "pembeli")
public class PembeliModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembeli;

    public Long getIdPembeli() {
        return this.idPembeli;
    }

    public void setIdPembeli(Long idPembeli) {
        this.idPembeli = idPembeli;
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
}
