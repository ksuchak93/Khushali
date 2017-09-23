package com.mycompany.myapp.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shipment.class)
public abstract class Shipment_ {

	public static volatile SingularAttribute<Shipment, String> bookingNo;
	public static volatile SingularAttribute<Shipment, Company> shipperCompany;
	public static volatile SingularAttribute<Shipment, ZonedDateTime> etd;
	public static volatile SetAttribute<Shipment, SalesOrder> salesOrders;
	public static volatile SingularAttribute<Shipment, String> shipmentNo;
	public static volatile SingularAttribute<Shipment, Long> id;
	public static volatile SetAttribute<Shipment, ShipProduct> shipProducts;
	public static volatile SingularAttribute<Shipment, ShipShipmentStatus> shipShipmentStatus;

}

