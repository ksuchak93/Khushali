import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    SalesOrderProductMySuffixService,
    SalesOrderProductMySuffixPopupService,
    SalesOrderProductMySuffixComponent,
    SalesOrderProductMySuffixDetailComponent,
    SalesOrderProductMySuffixDialogComponent,
    SalesOrderProductMySuffixPopupComponent,
    SalesOrderProductMySuffixDeletePopupComponent,
    SalesOrderProductMySuffixDeleteDialogComponent,
    salesOrderProductRoute,
    salesOrderProductPopupRoute,
    SalesOrderProductMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...salesOrderProductRoute,
    ...salesOrderProductPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SalesOrderProductMySuffixComponent,
        SalesOrderProductMySuffixDetailComponent,
        SalesOrderProductMySuffixDialogComponent,
        SalesOrderProductMySuffixDeleteDialogComponent,
        SalesOrderProductMySuffixPopupComponent,
        SalesOrderProductMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        SalesOrderProductMySuffixComponent,
        SalesOrderProductMySuffixDialogComponent,
        SalesOrderProductMySuffixPopupComponent,
        SalesOrderProductMySuffixDeleteDialogComponent,
        SalesOrderProductMySuffixDeletePopupComponent,
    ],
    providers: [
        SalesOrderProductMySuffixService,
        SalesOrderProductMySuffixPopupService,
        SalesOrderProductMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalSalesOrderProductMySuffixModule {}
