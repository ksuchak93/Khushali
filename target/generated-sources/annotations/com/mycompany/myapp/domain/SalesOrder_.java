package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.OrderStatus;
import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SalesOrder.class)
public abstract class SalesOrder_ {

	public static volatile SingularAttribute<SalesOrder, String> orderNumber;
	public static volatile SingularAttribute<SalesOrder, Shipment> shipment;
	public static volatile SingularAttribute<SalesOrder, Long> id;
	public static volatile SingularAttribute<SalesOrder, ZonedDateTime> shipDate;
	public static volatile SingularAttribute<SalesOrder, ZonedDateTime> orderDate;
	public static volatile SetAttribute<SalesOrder, SalesOrderProduct> salesOrderProducts;
	public static volatile SingularAttribute<SalesOrder, OrderStatus> status;
	public static volatile SingularAttribute<SalesOrder, Company> customer;

}

