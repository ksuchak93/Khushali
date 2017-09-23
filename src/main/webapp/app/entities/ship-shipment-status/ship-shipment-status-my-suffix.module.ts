import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhushFinalSharedModule } from '../../shared';
import {
    ShipShipmentStatusMySuffixService,
    ShipShipmentStatusMySuffixPopupService,
    ShipShipmentStatusMySuffixComponent,
    ShipShipmentStatusMySuffixDetailComponent,
    ShipShipmentStatusMySuffixDialogComponent,
    ShipShipmentStatusMySuffixPopupComponent,
    ShipShipmentStatusMySuffixDeletePopupComponent,
    ShipShipmentStatusMySuffixDeleteDialogComponent,
    shipShipmentStatusRoute,
    shipShipmentStatusPopupRoute,
    ShipShipmentStatusMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...shipShipmentStatusRoute,
    ...shipShipmentStatusPopupRoute,
];

@NgModule({
    imports: [
        KhushFinalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShipShipmentStatusMySuffixComponent,
        ShipShipmentStatusMySuffixDetailComponent,
        ShipShipmentStatusMySuffixDialogComponent,
        ShipShipmentStatusMySuffixDeleteDialogComponent,
        ShipShipmentStatusMySuffixPopupComponent,
        ShipShipmentStatusMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ShipShipmentStatusMySuffixComponent,
        ShipShipmentStatusMySuffixDialogComponent,
        ShipShipmentStatusMySuffixPopupComponent,
        ShipShipmentStatusMySuffixDeleteDialogComponent,
        ShipShipmentStatusMySuffixDeletePopupComponent,
    ],
    providers: [
        ShipShipmentStatusMySuffixService,
        ShipShipmentStatusMySuffixPopupService,
        ShipShipmentStatusMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhushFinalShipShipmentStatusMySuffixModule {}
