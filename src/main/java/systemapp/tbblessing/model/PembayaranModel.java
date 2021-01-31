package systemapp.tbblessing.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pembayaran")
@JsonIgnoreProperties(value={"transaksi"}, allowSetters = true)
public class PembayaranModel implements Serializable {
    @Id
    @Column(name = "id_pembayaran")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPembayaran;

    public Long getIdPembayaran() {
        return this.idPembayaran;
    }

    public void setIdPembayaran(Long idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    @NotNull
    @Column(name = "pembayaran", nullable = false)
    private Long pembayaran;

    public Long getPembayaran() {
        return this.pembayaran;
    }

    public void setPembayaran(Long pembayaran) {
        this.pembayaran = pembayaran;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_pembayaran")
    private Date tanggalPembayaran;

    public Date getTanggalPembayaran() {
        return this.tanggalPembayaran;
    }

    public void setTanggalPembayaran(Date tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transaksi_id", referencedColumnName = "id_transaksi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TransaksiModel transaksiModel;

    public TransaksiModel getTransaksiModel() {
        return this.transaksiModel;
    }

    public void setTransaksiModel(TransaksiModel transaksiModel) {
        this.transaksiModel = transaksiModel;
    }
}
