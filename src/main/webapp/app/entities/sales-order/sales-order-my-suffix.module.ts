import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    SalesOrderMySuffixService,
    SalesOrderMySuffixPopupService,
    SalesOrderMySuffixComponent,
    SalesOrderMySuffixDetailComponent,
    SalesOrderMySuffixDialogComponent,
    SalesOrderMySuffixPopupComponent,
    SalesOrderMySuffixDeletePopupComponent,
    SalesOrderMySuffixDeleteDialogComponent,
    salesOrderRoute,
    salesOrderPopupRoute,
    SalesOrderMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...salesOrderRoute,
    ...salesOrderPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SalesOrderMySuffixComponent,
        SalesOrderMySuffixDetailComponent,
        SalesOrderMySuffixDialogComponent,
        SalesOrderMySuffixDeleteDialogComponent,
        SalesOrderMySuffixPopupComponent,
        SalesOrderMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        SalesOrderMySuffixComponent,
        SalesOrderMySuffixDialogComponent,
        SalesOrderMySuffixPopupComponent,
        SalesOrderMySuffixDeleteDialogComponent,
        SalesOrderMySuffixDeletePopupComponent,
    ],
    providers: [
        SalesOrderMySuffixService,
        SalesOrderMySuffixPopupService,
        SalesOrderMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalSalesOrderMySuffixModule {}
