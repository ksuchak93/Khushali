package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, ContactCategory> contactCategory;
	public static volatile SingularAttribute<Contact, String> lastName;
	public static volatile SetAttribute<Contact, Address> addresses;
	public static volatile SingularAttribute<Contact, String> homePhone;
	public static volatile SingularAttribute<Contact, String> mobile;
	public static volatile SingularAttribute<Contact, String> title;
	public static volatile SingularAttribute<Contact, String> firstName;
	public static volatile SingularAttribute<Contact, String> officePhone;
	public static volatile SingularAttribute<Contact, String> whatsAppId;
	public static volatile SingularAttribute<Contact, Company> company;
	public static volatile SingularAttribute<Contact, Long> id;
	public static volatile SingularAttribute<Contact, String> department;
	public static volatile SingularAttribute<Contact, String> fax;
	public static volatile SingularAttribute<Contact, String> email;

}

