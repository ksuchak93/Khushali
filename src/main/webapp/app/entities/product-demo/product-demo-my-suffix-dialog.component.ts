import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductDemoMySuffix } from './product-demo-my-suffix.model';
import { ProductDemoMySuffixPopupService } from './product-demo-my-suffix-popup.service';
import { ProductDemoMySuffixService } from './product-demo-my-suffix.service';
import { ProductCategoryMySuffix, ProductCategoryMySuffixService } from '../product-category';
import { SalesOrderMySuffix, SalesOrderMySuffixService } from '../sales-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-product-demo-my-suffix-dialog',
    templateUrl: './product-demo-my-suffix-dialog.component.html'
})
export class ProductDemoMySuffixDialogComponent implements OnInit {

    productDemo: ProductDemoMySuffix;
    isSaving: boolean;

    productcategories: ProductCategoryMySuffix[];

    salesorders: SalesOrderMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private productDemoService: ProductDemoMySuffixService,
        private productCategoryService: ProductCategoryMySuffixService,
        private salesOrderService: SalesOrderMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.productcategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.salesOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.salesorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productDemo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productDemoService.update(this.productDemo));
        } else {
            this.subscribeToSaveResponse(
                this.productDemoService.create(this.productDemo));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductDemoMySuffix>) {
        result.subscribe((res: ProductDemoMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProductDemoMySuffix) {
        this.eventManager.broadcast({ name: 'productDemoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackProductCategoryById(index: number, item: ProductCategoryMySuffix) {
        return item.id;
    }

    trackSalesOrderById(index: number, item: SalesOrderMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-product-demo-my-suffix-popup',
    template: ''
})
export class ProductDemoMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productDemoPopupService: ProductDemoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productDemoPopupService
                    .open(ProductDemoMySuffixDialogComponent as Component, params['id']);
            } else {
                this.productDemoPopupService
                    .open(ProductDemoMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
