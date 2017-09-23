package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_address",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="addresses_id", referencedColumnName="id"))
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_company_category",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="company_categories_id", referencedColumnName="id"))
    private Set<CompanyCategory> companyCategories = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Company addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Company addAddress(Address address) {
        this.addresses.add(address);
        return this;
    }

    public Company removeAddress(Address address) {
        this.addresses.remove(address);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<CompanyCategory> getCompanyCategories() {
        return companyCategories;
    }

    public Company companyCategories(Set<CompanyCategory> companyCategories) {
        this.companyCategories = companyCategories;
        return this;
    }

    public Company addCompanyCategory(CompanyCategory companyCategory) {
        this.companyCategories.add(companyCategory);
        return this;
    }

    public Company removeCompanyCategory(CompanyCategory companyCategory) {
        this.companyCategories.remove(companyCategory);
        return this;
    }

    public void setCompanyCategories(Set<CompanyCategory> companyCategories) {
        this.companyCategories = companyCategories;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            "}";
    }
}
