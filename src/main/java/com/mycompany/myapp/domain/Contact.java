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
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "whats_app_id")
    private String whatsAppId;

    @ManyToOne
    private Company company;

    @ManyToOne
    private ContactCategory contactCategory;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "contact_address",
               joinColumns = @JoinColumn(name="contacts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="addresses_id", referencedColumnName="id"))
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public Contact title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public Contact department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public Contact officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobile() {
        return mobile;
    }

    public Contact mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public Contact homePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getFax() {
        return fax;
    }

    public Contact fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWhatsAppId() {
        return whatsAppId;
    }

    public Contact whatsAppId(String whatsAppId) {
        this.whatsAppId = whatsAppId;
        return this;
    }

    public void setWhatsAppId(String whatsAppId) {
        this.whatsAppId = whatsAppId;
    }

    public Company getCompany() {
        return company;
    }

    public Contact company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ContactCategory getContactCategory() {
        return contactCategory;
    }

    public Contact contactCategory(ContactCategory contactCategory) {
        this.contactCategory = contactCategory;
        return this;
    }

    public void setContactCategory(ContactCategory contactCategory) {
        this.contactCategory = contactCategory;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Contact addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Contact addAddress(Address address) {
        this.addresses.add(address);
        return this;
    }

    public Contact removeAddress(Address address) {
        this.addresses.remove(address);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
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
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", title='" + getTitle() + "'" +
            ", department='" + getDepartment() + "'" +
            ", email='" + getEmail() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", homePhone='" + getHomePhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", whatsAppId='" + getWhatsAppId() + "'" +
            "}";
    }
}
