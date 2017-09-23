package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.WeightUnit;

/**
 * A ProductDemo.
 */
@Entity
@Table(name = "product_demo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productdemo")
public class ProductDemo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prodcut_id")
    private String prodcutId;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "fetures")
    private String fetures;

    @Column(name = "jhi_size")
    private String size;

    @Column(name = "unit_weight")
    private Double unitWeight;

    @Column(name = "uint_in_sotck")
    private Double uintInSotck;

    @Column(name = "unit_available")
    private Double unitAvailable;

    @Column(name = "unit_blocked")
    private Double unitBlocked;

    @Column(name = "unit_shipped")
    private Double unitShipped;

    @Column(name = "reorder_level")
    private Double reorderLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "weight_uinit")
    private WeightUnit weightUinit;

    @ManyToOne
    private ProductCategory productCategory;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_demo_sales_order",
               joinColumns = @JoinColumn(name="product_demos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="sales_orders_id", referencedColumnName="id"))
    private Set<SalesOrder> salesOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdcutId() {
        return prodcutId;
    }

    public ProductDemo prodcutId(String prodcutId) {
        this.prodcutId = prodcutId;
        return this;
    }

    public void setProdcutId(String prodcutId) {
        this.prodcutId = prodcutId;
    }

    public String getName() {
        return name;
    }

    public ProductDemo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public ProductDemo details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFetures() {
        return fetures;
    }

    public ProductDemo fetures(String fetures) {
        this.fetures = fetures;
        return this;
    }

    public void setFetures(String fetures) {
        this.fetures = fetures;
    }

    public String getSize() {
        return size;
    }

    public ProductDemo size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getUnitWeight() {
        return unitWeight;
    }

    public ProductDemo unitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
        return this;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }

    public Double getUintInSotck() {
        return uintInSotck;
    }

    public ProductDemo uintInSotck(Double uintInSotck) {
        this.uintInSotck = uintInSotck;
        return this;
    }

    public void setUintInSotck(Double uintInSotck) {
        this.uintInSotck = uintInSotck;
    }

    public Double getUnitAvailable() {
        return unitAvailable;
    }

    public ProductDemo unitAvailable(Double unitAvailable) {
        this.unitAvailable = unitAvailable;
        return this;
    }

    public void setUnitAvailable(Double unitAvailable) {
        this.unitAvailable = unitAvailable;
    }

    public Double getUnitBlocked() {
        return unitBlocked;
    }

    public ProductDemo unitBlocked(Double unitBlocked) {
        this.unitBlocked = unitBlocked;
        return this;
    }

    public void setUnitBlocked(Double unitBlocked) {
        this.unitBlocked = unitBlocked;
    }

    public Double getUnitShipped() {
        return unitShipped;
    }

    public ProductDemo unitShipped(Double unitShipped) {
        this.unitShipped = unitShipped;
        return this;
    }

    public void setUnitShipped(Double unitShipped) {
        this.unitShipped = unitShipped;
    }

    public Double getReorderLevel() {
        return reorderLevel;
    }

    public ProductDemo reorderLevel(Double reorderLevel) {
        this.reorderLevel = reorderLevel;
        return this;
    }

    public void setReorderLevel(Double reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public WeightUnit getWeightUinit() {
        return weightUinit;
    }

    public ProductDemo weightUinit(WeightUnit weightUinit) {
        this.weightUinit = weightUinit;
        return this;
    }

    public void setWeightUinit(WeightUnit weightUinit) {
        this.weightUinit = weightUinit;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public ProductDemo productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Set<SalesOrder> getSalesOrders() {
        return salesOrders;
    }

    public ProductDemo salesOrders(Set<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
        return this;
    }

    public ProductDemo addSalesOrder(SalesOrder salesOrder) {
        this.salesOrders.add(salesOrder);
        return this;
    }

    public ProductDemo removeSalesOrder(SalesOrder salesOrder) {
        this.salesOrders.remove(salesOrder);
        return this;
    }

    public void setSalesOrders(Set<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
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
        ProductDemo productDemo = (ProductDemo) o;
        if (productDemo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDemo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDemo{" +
            "id=" + getId() +
            ", prodcutId='" + getProdcutId() + "'" +
            ", name='" + getName() + "'" +
            ", details='" + getDetails() + "'" +
            ", fetures='" + getFetures() + "'" +
            ", size='" + getSize() + "'" +
            ", unitWeight='" + getUnitWeight() + "'" +
            ", uintInSotck='" + getUintInSotck() + "'" +
            ", unitAvailable='" + getUnitAvailable() + "'" +
            ", unitBlocked='" + getUnitBlocked() + "'" +
            ", unitShipped='" + getUnitShipped() + "'" +
            ", reorderLevel='" + getReorderLevel() + "'" +
            ", weightUinit='" + getWeightUinit() + "'" +
            "}";
    }
}
