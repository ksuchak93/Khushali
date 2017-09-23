import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ContactCategoryMySuffixService,
    ContactCategoryMySuffixPopupService,
    ContactCategoryMySuffixComponent,
    ContactCategoryMySuffixDetailComponent,
    ContactCategoryMySuffixDialogComponent,
    ContactCategoryMySuffixPopupComponent,
    ContactCategoryMySuffixDeletePopupComponent,
    ContactCategoryMySuffixDeleteDialogComponent,
    contactCategoryRoute,
    contactCategoryPopupRoute,
    ContactCategoryMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactCategoryRoute,
    ...contactCategoryPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContactCategoryMySuffixComponent,
        ContactCategoryMySuffixDetailComponent,
        ContactCategoryMySuffixDialogComponent,
        ContactCategoryMySuffixDeleteDialogComponent,
        ContactCategoryMySuffixPopupComponent,
        ContactCategoryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ContactCategoryMySuffixComponent,
        ContactCategoryMySuffixDialogComponent,
        ContactCategoryMySuffixPopupComponent,
        ContactCategoryMySuffixDeleteDialogComponent,
        ContactCategoryMySuffixDeletePopupComponent,
    ],
    providers: [
        ContactCategoryMySuffixService,
        ContactCategoryMySuffixPopupService,
        ContactCategoryMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalContactCategoryMySuffixModule {}
