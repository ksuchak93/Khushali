package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.WeightUnit;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductDemo.class)
public abstract class ProductDemo_ {

	public static volatile SingularAttribute<ProductDemo, Double> unitWeight;
	public static volatile SingularAttribute<ProductDemo, WeightUnit> weightUinit;
	public static volatile SetAttribute<ProductDemo, SalesOrder> salesOrders;
	public static volatile SingularAttribute<ProductDemo, Double> unitShipped;
	public static volatile SingularAttribute<ProductDemo, String> fetures;
	public static volatile SingularAttribute<ProductDemo, ProductCategory> productCategory;
	public static volatile SingularAttribute<ProductDemo, Double> reorderLevel;
	public static volatile SingularAttribute<ProductDemo, String> size;
	public static volatile SingularAttribute<ProductDemo, Double> uintInSotck;
	public static volatile SingularAttribute<ProductDemo, Double> unitBlocked;
	public static volatile SingularAttribute<ProductDemo, String> name;
	public static volatile SingularAttribute<ProductDemo, String> details;
	public static volatile SingularAttribute<ProductDemo, Double> unitAvailable;
	public static volatile SingularAttribute<ProductDemo, Long> id;
	public static volatile SingularAttribute<ProductDemo, String> prodcutId;

}

