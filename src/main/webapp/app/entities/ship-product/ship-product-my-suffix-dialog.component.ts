import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ShipProductMySuffix } from './ship-product-my-suffix.model';
import { ShipProductMySuffixPopupService } from './ship-product-my-suffix-popup.service';
import { ShipProductMySuffixService } from './ship-product-my-suffix.service';
import { ShipmentMySuffix, ShipmentMySuffixService } from '../shipment';
import { SalesOrderProductMySuffix, SalesOrderProductMySuffixService } from '../sales-order-product';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ship-product-my-suffix-dialog',
    templateUrl: './ship-product-my-suffix-dialog.component.html'
})
export class ShipProductMySuffixDialogComponent implements OnInit {

    shipProduct: ShipProductMySuffix;
    isSaving: boolean;

    shipments: ShipmentMySuffix[];

    salesorderproducts: SalesOrderProductMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shipProductService: ShipProductMySuffixService,
        private shipmentService: ShipmentMySuffixService,
        private salesOrderProductService: SalesOrderProductMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.shipmentService.query()
            .subscribe((res: ResponseWrapper) => { this.shipments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.salesOrderProductService.query()
            .subscribe((res: ResponseWrapper) => { this.salesorderproducts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shipProduct.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shipProductService.update(this.shipProduct));
        } else {
            this.subscribeToSaveResponse(
                this.shipProductService.create(this.shipProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<ShipProductMySuffix>) {
        result.subscribe((res: ShipProductMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ShipProductMySuffix) {
        this.eventManager.broadcast({ name: 'shipProductListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackShipmentById(index: number, item: ShipmentMySuffix) {
        return item.id;
    }

    trackSalesOrderProductById(index: number, item: SalesOrderProductMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ship-product-my-suffix-popup',
    template: ''
})
export class ShipProductMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipProductPopupService: ShipProductMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shipProductPopupService
                    .open(ShipProductMySuffixDialogComponent as Component, params['id']);
            } else {
                this.shipProductPopupService
                    .open(ShipProductMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
