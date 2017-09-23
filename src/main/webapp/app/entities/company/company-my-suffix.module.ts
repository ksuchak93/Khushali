import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    CompanyMySuffixService,
    CompanyMySuffixPopupService,
    CompanyMySuffixComponent,
    CompanyMySuffixDetailComponent,
    CompanyMySuffixDialogComponent,
    CompanyMySuffixPopupComponent,
    CompanyMySuffixDeletePopupComponent,
    CompanyMySuffixDeleteDialogComponent,
    companyRoute,
    companyPopupRoute,
    CompanyMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyRoute,
    ...companyPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyMySuffixComponent,
        CompanyMySuffixDetailComponent,
        CompanyMySuffixDialogComponent,
        CompanyMySuffixDeleteDialogComponent,
        CompanyMySuffixPopupComponent,
        CompanyMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CompanyMySuffixComponent,
        CompanyMySuffixDialogComponent,
        CompanyMySuffixPopupComponent,
        CompanyMySuffixDeleteDialogComponent,
        CompanyMySuffixDeletePopupComponent,
    ],
    providers: [
        CompanyMySuffixService,
        CompanyMySuffixPopupService,
        CompanyMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalCompanyMySuffixModule {}
