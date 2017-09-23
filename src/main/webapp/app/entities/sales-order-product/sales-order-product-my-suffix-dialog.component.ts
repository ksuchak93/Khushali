import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SalesOrderProductMySuffix } from './sales-order-product-my-suffix.model';
import { SalesOrderProductMySuffixPopupService } from './sales-order-product-my-suffix-popup.service';
import { SalesOrderProductMySuffixService } from './sales-order-product-my-suffix.service';
import { SalesOrderMySuffix, SalesOrderMySuffixService } from '../sales-order';
import { ProductDemoMySuffix, ProductDemoMySuffixService } from '../product-demo';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sales-order-product-my-suffix-dialog',
    templateUrl: './sales-order-product-my-suffix-dialog.component.html'
})
export class SalesOrderProductMySuffixDialogComponent implements OnInit {

    salesOrderProduct: SalesOrderProductMySuffix;
    isSaving: boolean;

    salesorders: SalesOrderMySuffix[];

    productdemos: ProductDemoMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private salesOrderProductService: SalesOrderProductMySuffixService,
        private salesOrderService: SalesOrderMySuffixService,
        private productDemoService: ProductDemoMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.salesOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.salesorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.productDemoService.query()
            .subscribe((res: ResponseWrapper) => { this.productdemos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.salesOrderProduct.id !== undefined) {
            this.subscribeToSaveResponse(
                this.salesOrderProductService.update(this.salesOrderProduct));
        } else {
            this.subscribeToSaveResponse(
                this.salesOrderProductService.create(this.salesOrderProduct));
        }
    }

    private subscribeToSaveResponse(result: Observable<SalesOrderProductMySuffix>) {
        result.subscribe((res: SalesOrderProductMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SalesOrderProductMySuffix) {
        this.eventManager.broadcast({ name: 'salesOrderProductListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackSalesOrderById(index: number, item: SalesOrderMySuffix) {
        return item.id;
    }

    trackProductDemoById(index: number, item: ProductDemoMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sales-order-product-my-suffix-popup',
    template: ''
})
export class SalesOrderProductMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesOrderProductPopupService: SalesOrderProductMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.salesOrderProductPopupService
                    .open(SalesOrderProductMySuffixDialogComponent as Component, params['id']);
            } else {
                this.salesOrderProductPopupService
                    .open(SalesOrderProductMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
