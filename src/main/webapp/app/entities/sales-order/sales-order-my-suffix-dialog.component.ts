import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SalesOrderMySuffix } from './sales-order-my-suffix.model';
import { SalesOrderMySuffixPopupService } from './sales-order-my-suffix-popup.service';
import { SalesOrderMySuffixService } from './sales-order-my-suffix.service';
import { CompanyMySuffix, CompanyMySuffixService } from '../company';
import { ShipmentMySuffix, ShipmentMySuffixService } from '../shipment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sales-order-my-suffix-dialog',
    templateUrl: './sales-order-my-suffix-dialog.component.html'
})
export class SalesOrderMySuffixDialogComponent implements OnInit {

    salesOrder: SalesOrderMySuffix;
    isSaving: boolean;

    companies: CompanyMySuffix[];

    shipments: ShipmentMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private salesOrderService: SalesOrderMySuffixService,
        private companyService: CompanyMySuffixService,
        private shipmentService: ShipmentMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.shipmentService.query()
            .subscribe((res: ResponseWrapper) => { this.shipments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.salesOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.salesOrderService.update(this.salesOrder));
        } else {
            this.subscribeToSaveResponse(
                this.salesOrderService.create(this.salesOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<SalesOrderMySuffix>) {
        result.subscribe((res: SalesOrderMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SalesOrderMySuffix) {
        this.eventManager.broadcast({ name: 'salesOrderListModification', content: 'OK'});
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

    trackShipmentById(index: number, item: ShipmentMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sales-order-my-suffix-popup',
    template: ''
})
export class SalesOrderMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesOrderPopupService: SalesOrderMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.salesOrderPopupService
                    .open(SalesOrderMySuffixDialogComponent as Component, params['id']);
            } else {
                this.salesOrderPopupService
                    .open(SalesOrderMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
