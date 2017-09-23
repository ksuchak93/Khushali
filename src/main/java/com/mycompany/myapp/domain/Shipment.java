package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Shipment.
 */
@Entity
@Table(name = "shipment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope = Shipment.class)
@Document(indexName = "shipment")
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipment_no")
    private String shipmentNo;

    @Column(name = "booking_no")
    private String bookingNo;

    @Column(name = "etd")
    private ZonedDateTime etd;

    @OneToOne
    @JoinColumn(unique = true)
    private Company shipperCompany;

    @OneToMany(mappedBy = "shipment",fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ShipProduct> shipProducts = new HashSet<>();

    @OneToMany(mappedBy = "shipment",fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalesOrder> salesOrders = new HashSet<>();

    @ManyToOne
    private ShipShipmentStatus shipShipmentStatus;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShipmentNo() {
        return shipmentNo;
    }

    public Shipment shipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
        return this;
    }

    public void setShipmentNo(String shipmentNo) {
        this.shipmentNo = shipmentNo;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public Shipment bookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public ZonedDateTime getEtd() {
        return etd;
    }

    public Shipment etd(ZonedDateTime etd) {
        this.etd = etd;
        return this;
    }

    public void setEtd(ZonedDateTime etd) {
        this.etd = etd;
    }

    public Company getShipperCompany() {
        return shipperCompany;
    }

    public Shipment shipperCompany(Company company) {
        this.shipperCompany = company;
        return this;
    }

    public void setShipperCompany(Company company) {
        this.shipperCompany = company;
    }

    public Set<ShipProduct> getShipProducts() {
        return shipProducts;
    }

    public Shipment shipProducts(Set<ShipProduct> shipProducts) {
        this.shipProducts = shipProducts;
        return this;
    }

    public Shipment addShipProducts(ShipProduct shipProduct) {
        this.shipProducts.add(shipProduct);
        shipProduct.setShipment(this);
        return this;
    }

    public Shipment removeShipProducts(ShipProduct shipProduct) {
        this.shipProducts.remove(shipProduct);
        shipProduct.setShipment(null);
        return this;
    }

    public void setShipProducts(Set<ShipProduct> shipProducts) {
        this.shipProducts = shipProducts;
    }

    public Set<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    public Shipment salesOrders(Set<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
        return this;
    }

    public Shipment addSalesOrders(SalesOrder salesOrder) {
        this.salesOrders.add(salesOrder);
        salesOrder.setShipment(this);
        return this;
    }

    public Shipment removeSalesOrders(SalesOrder salesOrder) {
        this.salesOrders.remove(salesOrder);
        salesOrder.setShipment(null);
        return this;
    }

    public void setSalesOrders(Set<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }

    public ShipShipmentStatus getShipShipmentStatus() {
        return shipShipmentStatus;
    }

    public Shipment shipShipmentStatus(ShipShipmentStatus shipShipmentStatus) {
        this.shipShipmentStatus = shipShipmentStatus;
        return this;
    }

    public void setShipShipmentStatus(ShipShipmentStatus shipShipmentStatus) {
        this.shipShipmentStatus = shipShipmentStatus;
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
        Shipment shipment = (Shipment) o;
        if (shipment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Shipment{" +
            "id=" + getId() +
            ", shipmentNo='" + getShipmentNo() + "'" +
            ", bookingNo='" + getBookingNo() + "'" +
            ", etd='" + getEtd() + "'" +
            "}";
    }
}
