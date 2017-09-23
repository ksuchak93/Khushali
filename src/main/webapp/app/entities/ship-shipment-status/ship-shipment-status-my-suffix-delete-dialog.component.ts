import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShipShipmentStatusMySuffix } from './ship-shipment-status-my-suffix.model';
import { ShipShipmentStatusMySuffixPopupService } from './ship-shipment-status-my-suffix-popup.service';
import { ShipShipmentStatusMySuffixService } from './ship-shipment-status-my-suffix.service';

@Component({
    selector: 'jhi-ship-shipment-status-my-suffix-delete-dialog',
    templateUrl: './ship-shipment-status-my-suffix-delete-dialog.component.html'
})
export class ShipShipmentStatusMySuffixDeleteDialogComponent {

    shipShipmentStatus: ShipShipmentStatusMySuffix;

    constructor(
        private shipShipmentStatusService: ShipShipmentStatusMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipShipmentStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shipShipmentStatusListModification',
                content: 'Deleted an shipShipmentStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ship-shipment-status-my-suffix-delete-popup',
    template: ''
})
export class ShipShipmentStatusMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipShipmentStatusPopupService: ShipShipmentStatusMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shipShipmentStatusPopupService
                .open(ShipShipmentStatusMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
