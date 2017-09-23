package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ShipProduct.
 */
@Entity
@Table(name = "ship_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope = ShipProduct.class)
@Document(indexName = "shipproduct")
public class ShipProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "gross_weight")
    private Double grossWeight;

    @Column(name = "packege")
    private String packege;

    @ManyToOne
    private Shipment shipment;

    @ManyToOne
    private SalesOrderProduct salesOrderProduct;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public ShipProduct quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public ShipProduct netWeight(Double netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public ShipProduct grossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
        return this;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getPackege() {
        return packege;
    }

    public ShipProduct packege(String packege) {
        this.packege = packege;
        return this;
    }

    public void setPackege(String packege) {
        this.packege = packege;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public ShipProduct shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public SalesOrderProduct getSalesOrderProduct() {
        return salesOrderProduct;
    }

    public ShipProduct salesOrderProduct(SalesOrderProduct salesOrderProduct) {
        this.salesOrderProduct = salesOrderProduct;
        return this;
    }

    public void setSalesOrderProduct(SalesOrderProduct salesOrderProduct) {
        this.salesOrderProduct = salesOrderProduct;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipProduct shipProduct = (ShipProduct) o;
        if (shipProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShipProduct{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            ", netWeight='" + getNetWeight() + "'" +
            ", grossWeight='" + getGrossWeight() + "'" +
            ", packege='" + getPackege() + "'" +
            "}";
    }
}
