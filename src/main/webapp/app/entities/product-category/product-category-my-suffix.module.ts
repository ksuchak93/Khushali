import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ProductCategoryMySuffixService,
    ProductCategoryMySuffixPopupService,
    ProductCategoryMySuffixComponent,
    ProductCategoryMySuffixDetailComponent,
    ProductCategoryMySuffixDialogComponent,
    ProductCategoryMySuffixPopupComponent,
    ProductCategoryMySuffixDeletePopupComponent,
    ProductCategoryMySuffixDeleteDialogComponent,
    productCategoryRoute,
    productCategoryPopupRoute,
    ProductCategoryMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...productCategoryRoute,
    ...productCategoryPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProductCategoryMySuffixComponent,
        ProductCategoryMySuffixDetailComponent,
        ProductCategoryMySuffixDialogComponent,
        ProductCategoryMySuffixDeleteDialogComponent,
        ProductCategoryMySuffixPopupComponent,
        ProductCategoryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ProductCategoryMySuffixComponent,
        ProductCategoryMySuffixDialogComponent,
        ProductCategoryMySuffixPopupComponent,
        ProductCategoryMySuffixDeleteDialogComponent,
        ProductCategoryMySuffixDeletePopupComponent,
    ],
    providers: [
        ProductCategoryMySuffixService,
        ProductCategoryMySuffixPopupService,
        ProductCategoryMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalProductCategoryMySuffixModule {}
