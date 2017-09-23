package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, Country> country;
	public static volatile SingularAttribute<Address, String> addressLineTwo;
	public static volatile SingularAttribute<Address, String> code;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> addressLineOne;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> state;

}

