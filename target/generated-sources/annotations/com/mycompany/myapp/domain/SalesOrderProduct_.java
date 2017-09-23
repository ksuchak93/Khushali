package com.mycompany.myapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SalesOrderProduct.class)
public abstract class SalesOrderProduct_ {

	public static volatile SingularAttribute<SalesOrderProduct, Double> unitPrice;
	public static volatile SingularAttribute<SalesOrderProduct, Double> total;
	public static volatile SingularAttribute<SalesOrderProduct, ProductDemo> product;
	public static volatile SingularAttribute<SalesOrderProduct, String> quantity;
	public static volatile SingularAttribute<SalesOrderProduct, Boolean> fulfilled;
	public static volatile SingularAttribute<SalesOrderProduct, Double> shippedQuantity;
	public static volatile SingularAttribute<SalesOrderProduct, Double> discount;
	public static volatile SingularAttribute<SalesOrderProduct, Long> id;
	public static volatile SingularAttribute<SalesOrderProduct, ZonedDateTime> shipDate;
	public static volatile SingularAttribute<SalesOrderProduct, SalesOrder> salesOrder;

}

