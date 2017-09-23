package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SetAttribute<Company, Address> addresses;
	public static volatile SingularAttribute<Company, String> companyName;
	public static volatile SetAttribute<Company, CompanyCategory> companyCategories;
	public static volatile SingularAttribute<Company, Long> id;

}

