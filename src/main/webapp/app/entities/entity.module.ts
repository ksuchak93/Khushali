import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KhushFinalCountryMySuffixModule } from './country/country-my-suffix.module';
import { KhushFinalAddressMySuffixModule } from './address/address-my-suffix.module';
import { KhushFinalProductCategoryMySuffixModule } from './product-category/product-category-my-suffix.module';
import { KhushFinalProductDemoMySuffixModule } from './product-demo/product-demo-my-suffix.module';
import { KhushFinalContactCategoryMySuffixModule } from './contact-category/contact-category-my-suffix.module';
import { KhushFinalCompanyCategoryMySuffixModule } from './company-category/company-category-my-suffix.module';
import { KhushFinalCompanyMySuffixModule } from './company/company-my-suffix.module';
import { KhushFinalContactMySuffixModule } from './contact/contact-my-suffix.module';
import { KhushFinalSalesOrderMySuffixModule } from './sales-order/sales-order-my-suffix.module';
import { KhushFinalSalesOrderProductMySuffixModule } from './sales-order-product/sales-order-product-my-suffix.module';
import { KhushFinalShipmentMySuffixModule } from './shipment/shipment-my-suffix.module';
import { KhushFinalShipShipmentStatusMySuffixModule } from './ship-shipment-status/ship-shipment-status-my-suffix.module';
import { KhushFinalShipProductMySuffixModule } from './ship-product/ship-product-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KhushFinalCountryMySuffixModule,
        KhushFinalAddressMySuffixModule,
        KhushFinalProductCategoryMySuffixModule,
        KhushFinalProductDemoMySuffixModule,
        KhushFinalContactCategoryMySuffixModule,
        KhushFinalCompanyCategoryMySuffixModule,
        KhushFinalCompanyMySuffixModule,
        KhushFinalContactMySuffixModule,
        KhushFinalSalesOrderMySuffixModule,
        KhushFinalSalesOrderProductMySuffixModule,
        KhushFinalShipmentMySuffixModule,
        KhushFinalShipShipmentStatusMySuffixModule,
        KhushFinalShipProductMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalEntityModule {}
