import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    CompanyCategoryMySuffixService,
    CompanyCategoryMySuffixPopupService,
    CompanyCategoryMySuffixComponent,
    CompanyCategoryMySuffixDetailComponent,
    CompanyCategoryMySuffixDialogComponent,
    CompanyCategoryMySuffixPopupComponent,
    CompanyCategoryMySuffixDeletePopupComponent,
    CompanyCategoryMySuffixDeleteDialogComponent,
    companyCategoryRoute,
    companyCategoryPopupRoute,
    CompanyCategoryMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...companyCategoryRoute,
    ...companyCategoryPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CompanyCategoryMySuffixComponent,
        CompanyCategoryMySuffixDetailComponent,
        CompanyCategoryMySuffixDialogComponent,
        CompanyCategoryMySuffixDeleteDialogComponent,
        CompanyCategoryMySuffixPopupComponent,
        CompanyCategoryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CompanyCategoryMySuffixComponent,
        CompanyCategoryMySuffixDialogComponent,
        CompanyCategoryMySuffixPopupComponent,
        CompanyCategoryMySuffixDeleteDialogComponent,
        CompanyCategoryMySuffixDeletePopupComponent,
    ],
    providers: [
        CompanyCategoryMySuffixService,
        CompanyCategoryMySuffixPopupService,
        CompanyCategoryMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalCompanyCategoryMySuffixModule {}
