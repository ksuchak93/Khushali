import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ShipmentMySuffixService,
    ShipmentMySuffixPopupService,
    ShipmentMySuffixComponent,
    ShipmentMySuffixDetailComponent,
    ShipmentMySuffixDialogComponent,
    ShipmentMySuffixPopupComponent,
    ShipmentMySuffixDeletePopupComponent,
    ShipmentMySuffixDeleteDialogComponent,
    shipmentRoute,
    shipmentPopupRoute,
    ShipmentMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shipmentRoute,
    ...shipmentPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShipmentMySuffixComponent,
        ShipmentMySuffixDetailComponent,
        ShipmentMySuffixDialogComponent,
        ShipmentMySuffixDeleteDialogComponent,
        ShipmentMySuffixPopupComponent,
        ShipmentMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ShipmentMySuffixComponent,
        ShipmentMySuffixDialogComponent,
        ShipmentMySuffixPopupComponent,
        ShipmentMySuffixDeleteDialogComponent,
        ShipmentMySuffixDeletePopupComponent,
    ],
    providers: [
        ShipmentMySuffixService,
        ShipmentMySuffixPopupService,
        ShipmentMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalShipmentMySuffixModule {}
