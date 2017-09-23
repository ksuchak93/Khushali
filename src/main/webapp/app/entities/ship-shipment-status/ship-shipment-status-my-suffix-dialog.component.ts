import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShipShipmentStatusMySuffix } from './ship-shipment-status-my-suffix.model';
import { ShipShipmentStatusMySuffixPopupService } from './ship-shipment-status-my-suffix-popup.service';
import { ShipShipmentStatusMySuffixService } from './ship-shipment-status-my-suffix.service';

@Component({
    selector: 'jhi-ship-shipment-status-my-suffix-dialog',
    templateUrl: './ship-shipment-status-my-suffix-dialog.component.html'
})
export class ShipShipmentStatusMySuffixDialogComponent implements OnInit {

    shipShipmentStatus: ShipShipmentStatusMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shipShipmentStatusService: ShipShipmentStatusMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shipShipmentStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shipShipmentStatusService.update(this.shipShipmentStatus));
        } else {
            this.subscribeToSaveResponse(
                this.shipShipmentStatusService.create(this.shipShipmentStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShipShipmentStatusMySuffix>) {
        result.subscribe((res: ShipShipmentStatusMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ShipShipmentStatusMySuffix) {
        this.eventManager.broadcast({ name: 'shipShipmentStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-ship-shipment-status-my-suffix-popup',
    template: ''
})
export class ShipShipmentStatusMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipShipmentStatusPopupService: ShipShipmentStatusMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shipShipmentStatusPopupService
                    .open(ShipShipmentStatusMySuffixDialogComponent as Component, params['id']);
            } else {
                this.shipShipmentStatusPopupService
                    .open(ShipShipmentStatusMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
