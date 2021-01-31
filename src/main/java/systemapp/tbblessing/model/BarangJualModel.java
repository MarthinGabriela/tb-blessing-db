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
@Table(name = "barang_jual")
@JsonIgnoreProperties(value={"barang", "transaksi"}, allowSetters = true)
public class BarangJualModel implements Serializable {
    @Id
    @Column(name = "id_barang_jual")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarangJual;

    public Long getIdBarangJual() {
        return this.idBarangJual;
    }

    public void setIdBarangJual(Long idBarangJual) {
        this.idBarangJual = idBarangJual;
    }

    @NotNull
    @Column(name = "stock_barang_jual", nullable = false)
    private Long stockBarangJual;

    public Long getStockBarangJual() {
        return this.stockBarangJual;
    }

    public void setStockBarangJual(Long stockBarangJual) {
        this.stockBarangJual = stockBarangJual;
    }

    @NotNull
    @Column(name = "harga_jual_barang", nullable = false)
    private Long hargaJual;

    public Long getHargaJual() {
        return this.hargaJual;
    }

    public void setHargaJual(Long hargaJual) {
        this.hargaJual = hargaJual;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "barangjual_id", referencedColumnName = "id_barang", nullable = false)
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