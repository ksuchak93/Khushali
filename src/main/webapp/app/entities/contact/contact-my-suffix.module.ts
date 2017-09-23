import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ContactMySuffixService,
    ContactMySuffixPopupService,
    ContactMySuffixComponent,
    ContactMySuffixDetailComponent,
    ContactMySuffixDialogComponent,
    ContactMySuffixPopupComponent,
    ContactMySuffixDeletePopupComponent,
    ContactMySuffixDeleteDialogComponent,
    contactRoute,
    contactPopupRoute,
    ContactMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...contactRoute,
    ...contactPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContactMySuffixComponent,
        ContactMySuffixDetailComponent,
        ContactMySuffixDialogComponent,
        ContactMySuffixDeleteDialogComponent,
        ContactMySuffixPopupComponent,
        ContactMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ContactMySuffixComponent,
        ContactMySuffixDialogComponent,
        ContactMySuffixPopupComponent,
        ContactMySuffixDeleteDialogComponent,
        ContactMySuffixDeletePopupComponent,
    ],
    providers: [
        ContactMySuffixService,
        ContactMySuffixPopupService,
        ContactMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalContactMySuffixModule {}
