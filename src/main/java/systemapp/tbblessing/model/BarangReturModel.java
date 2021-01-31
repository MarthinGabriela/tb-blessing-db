package systemapp.tbblessing.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "barang_retur")
@JsonIgnoreProperties(value={"barang", "transaksi"}, allowSetters = true)
public class BarangReturModel implements Serializable {
    @Id
    @Column(name = "id_barang_retur")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarangRetur;

    public Long getIdBarangRetur() {
        return this.idBarangRetur;
    }

    public void setIdBarangRetur(Long idBarangRetur) {
        this.idBarangRetur = idBarangRetur;
    }

    @NotNull
    @Column(name = "stock_barang_retur", nullable = false)
    private Long stockBarangRetur;

    public Long getStockBarangRetur() {
        return this.stockBarangRetur;
    }

    public void setStockBarangRetur(Long stockBarangRetur) {
        this.stockBarangRetur = stockBarangRetur;
    }

    @NotNull
    @Column(name = "harga_retur_barang", nullable = false)
    private Long hargaRetur;

    public Long getHargaRetur() {
        return this.hargaRetur;
    }

    public void setHargaRetur(Long hargaRetur) {
        this.hargaRetur = hargaRetur;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "barangRetur_id", referencedColumnName = "id_barang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BarangModel barangModel;

    public BarangModel getBarangModel() {
        return this.barangModel;
    }

    public void setBarangModel(BarangModel barangModel) {
        this.barangModel = barangModel;
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