package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SalesOrderProduct.
 */
@Entity
@Table(name = "sales_order_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope = SalesOrderProduct.class)
@Document(indexName = "salesorderproduct")
public class SalesOrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "total")
    private Double total;

    @Column(name = "fulfilled")
    private Boolean fulfilled;

    @Column(name = "shipped_quantity")
    private Double shippedQuantity;

    @Column(name = "ship_date")
    private ZonedDateTime shipDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private SalesOrder salesOrder;

    @ManyToOne
    private ProductDemo product;

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

    public SalesOrderProduct quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public SalesOrderProduct unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public SalesOrderProduct discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotal() {
        return total;
    }

    public SalesOrderProduct total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean isFulfilled() {
        return fulfilled;
    }

    public SalesOrderProduct fulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
        return this;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Double getShippedQuantity() {
        return shippedQuantity;
    }

    public SalesOrderProduct shippedQuantity(Double shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
        return this;
    }

    public void setShippedQuantity(Double shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public ZonedDateTime getShipDate() {
        return shipDate;
    }

    public SalesOrderProduct shipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    public void setShipDate(ZonedDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public SalesOrderProduct salesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
        return this;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public ProductDemo getProduct() {
        return product;
    }

    public SalesOrderProduct product(ProductDemo productDemo) {
        this.product = productDemo;
        return this;
    }

    public void setProduct(ProductDemo productDemo) {
        this.product = productDemo;
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
        SalesOrderProduct salesOrderProduct = (SalesOrderProduct) o;
        if (salesOrderProduct.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salesOrderProduct.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalesOrderProduct{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            ", unitPrice='" + getUnitPrice() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", total='" + getTotal() + "'" +
            ", fulfilled='" + isFulfilled() + "'" +
            ", shippedQuantity='" + getShippedQuantity() + "'" +
            ", shipDate='" + getShipDate() + "'" +
            "}";
    }
}
