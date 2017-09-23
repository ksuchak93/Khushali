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

import com.mycompany.myapp.domain.enumeration.OrderStatus;

/**
 * A SalesOrder.
 */
@Entity
@Table(name = "sales_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope = SalesOrder.class)
@Document(indexName = "salesorder")
public class SalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_date")
    private ZonedDateTime orderDate;

    @Column(name = "ship_date")
    private ZonedDateTime shipDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(mappedBy = "salesOrder",fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalesOrderProduct> salesOrderProducts = new HashSet<>();

    @ManyToOne
    private Company customer;

    @ManyToOne
    private Shipment shipment;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public SalesOrder orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public SalesOrder orderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public ZonedDateTime getShipDate() {
        return shipDate;
    }

    public SalesOrder shipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    public void setShipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public SalesOrder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<SalesOrderProduct> getSalesOrderProducts() {
        return salesOrderProducts;
    }

    public SalesOrder salesOrderProducts(Set<SalesOrderProduct> salesOrderProducts) {
        this.salesOrderProducts = salesOrderProducts;
        return this;
    }

    public SalesOrder addSalesOrderProducts(SalesOrderProduct salesOrderProduct) {
        this.salesOrderProducts.add(salesOrderProduct);
        salesOrderProduct.setSalesOrder(this);
        return this;
    }

    public SalesOrder removeSalesOrderProducts(SalesOrderProduct salesOrderProduct) {
        this.salesOrderProducts.remove(salesOrderProduct);
        salesOrderProduct.setSalesOrder(null);
        return this;
    }

    public void setSalesOrderProducts(Set<SalesOrderProduct> salesOrderProducts) {
        this.salesOrderProducts = salesOrderProducts;
    }

    public Company getCustomer() {
        return customer;
    }

    public SalesOrder customer(Company company) {
        this.customer = company;
        return this;
    }

    public void setCustomer(Company company) {
        this.customer = company;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public SalesOrder shipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
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
        SalesOrder salesOrder = (SalesOrder) o;
        if (salesOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesOrder{" +
            "id=" + getId() +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
