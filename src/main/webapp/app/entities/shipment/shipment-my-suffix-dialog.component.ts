import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShipmentMySuffix } from './shipment-my-suffix.model';
import { ShipmentMySuffixPopupService } from './shipment-my-suffix-popup.service';
import { ShipmentMySuffixService } from './shipment-my-suffix.service';
import { CompanyMySuffix, CompanyMySuffixService } from '../company';
import { ShipShipmentStatusMySuffix, ShipShipmentStatusMySuffixService } from '../ship-shipment-status';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-shipment-my-suffix-dialog',
    templateUrl: './shipment-my-suffix-dialog.component.html'
})
export class ShipmentMySuffixDialogComponent implements OnInit {

    shipment: ShipmentMySuffix;
    isSaving: boolean;

    shippercompanies: CompanyMySuffix[];

    shipshipmentstatuses: ShipShipmentStatusMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shipmentService: ShipmentMySuffixService,
        private companyService: CompanyMySuffixService,
        private shipShipmentStatusService: ShipShipmentStatusMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService
            .query({filter: 'shipment-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.shipment.shipperCompany || !this.shipment.shipperCompany.id) {
                    this.shippercompanies = res.json;
                } else {
                    this.companyService
                        .find(this.shipment.shipperCompany.id)
                        .subscribe((subRes: CompanyMySuffix) => {
                            this.shippercompanies = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.shipShipmentStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.shipshipmentstatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shipment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shipmentService.update(this.shipment));
        } else {
            this.subscribeToSaveResponse(
                this.shipmentService.create(this.shipment));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShipmentMySuffix>) {
        result.subscribe((res: ShipmentMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ShipmentMySuffix) {
        this.eventManager.broadcast({ name: 'shipmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: CompanyMySuffix) {
        return item.id;
    }

    trackShipShipmentStatusById(index: number, item: ShipShipmentStatusMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shipment-my-suffix-popup',
    template: ''
})
export class ShipmentMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipmentPopupService: ShipmentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shipmentPopupService
                    .open(ShipmentMySuffixDialogComponent as Component, params['id']);
            } else {
                this.shipmentPopupService
                    .open(ShipmentMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
