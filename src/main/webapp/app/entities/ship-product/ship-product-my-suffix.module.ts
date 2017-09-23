import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ShipProductMySuffixService,
    ShipProductMySuffixPopupService,
    ShipProductMySuffixComponent,
    ShipProductMySuffixDetailComponent,
    ShipProductMySuffixDialogComponent,
    ShipProductMySuffixPopupComponent,
    ShipProductMySuffixDeletePopupComponent,
    ShipProductMySuffixDeleteDialogComponent,
    shipProductRoute,
    shipProductPopupRoute,
    ShipProductMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shipProductRoute,
    ...shipProductPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShipProductMySuffixComponent,
        ShipProductMySuffixDetailComponent,
        ShipProductMySuffixDialogComponent,
        ShipProductMySuffixDeleteDialogComponent,
        ShipProductMySuffixPopupComponent,
        ShipProductMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ShipProductMySuffixComponent,
        ShipProductMySuffixDialogComponent,
        ShipProductMySuffixPopupComponent,
        ShipProductMySuffixDeleteDialogComponent,
        ShipProductMySuffixDeletePopupComponent,
    ],
    providers: [
        ShipProductMySuffixService,
        ShipProductMySuffixPopupService,
        ShipProductMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalShipProductMySuffixModule {}
