package com.mycompany.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ShipProduct.class)
public abstract class ShipProduct_ {

	public static volatile SingularAttribute<ShipProduct, Double> netWeight;
	public static volatile SingularAttribute<ShipProduct, Double> grossWeight;
	public static volatile SingularAttribute<ShipProduct, String> quantity;
	public static volatile SingularAttribute<ShipProduct, Shipment> shipment;
	public static volatile SingularAttribute<ShipProduct, SalesOrderProduct> salesOrderProduct;
	public static volatile SingularAttribute<ShipProduct, Long> id;
	public static volatile SingularAttribute<ShipProduct, String> packege;

}

