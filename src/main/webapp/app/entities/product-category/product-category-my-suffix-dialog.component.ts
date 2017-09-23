import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductCategoryMySuffix } from './product-category-my-suffix.model';
import { ProductCategoryMySuffixPopupService } from './product-category-my-suffix-popup.service';
import { ProductCategoryMySuffixService } from './product-category-my-suffix.service';

@Component({
    selector: 'jhi-product-category-my-suffix-dialog',
    templateUrl: './product-category-my-suffix-dialog.component.html'
})
export class ProductCategoryMySuffixDialogComponent implements OnInit {

    productCategory: ProductCategoryMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private productCategoryService: ProductCategoryMySuffixService,
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
        if (this.productCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productCategoryService.update(this.productCategory));
        } else {
            this.subscribeToSaveResponse(
                this.productCategoryService.create(this.productCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductCategoryMySuffix>) {
        result.subscribe((res: ProductCategoryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProductCategoryMySuffix) {
        this.eventManager.broadcast({ name: 'productCategoryListModification', content: 'OK'});
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
    selector: 'jhi-product-category-my-suffix-popup',
    template: ''
})
export class ProductCategoryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCategoryPopupService: ProductCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productCategoryPopupService
                    .open(ProductCategoryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.productCategoryPopupService
                    .open(ProductCategoryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
