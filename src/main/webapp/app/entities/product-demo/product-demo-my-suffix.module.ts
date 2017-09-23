import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ProductDemoMySuffixService,
    ProductDemoMySuffixPopupService,
    ProductDemoMySuffixComponent,
    ProductDemoMySuffixDetailComponent,
    ProductDemoMySuffixDialogComponent,
    ProductDemoMySuffixPopupComponent,
    ProductDemoMySuffixDeletePopupComponent,
    ProductDemoMySuffixDeleteDialogComponent,
    productDemoRoute,
    productDemoPopupRoute,
    ProductDemoMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productDemoRoute,
    ...productDemoPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductDemoMySuffixComponent,
        ProductDemoMySuffixDetailComponent,
        ProductDemoMySuffixDialogComponent,
        ProductDemoMySuffixDeleteDialogComponent,
        ProductDemoMySuffixPopupComponent,
        ProductDemoMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ProductDemoMySuffixComponent,
        ProductDemoMySuffixDialogComponent,
        ProductDemoMySuffixPopupComponent,
        ProductDemoMySuffixDeleteDialogComponent,
        ProductDemoMySuffixDeletePopupComponent,
    ],
    providers: [
        ProductDemoMySuffixService,
        ProductDemoMySuffixPopupService,
        ProductDemoMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalProductDemoMySuffixModule {}
