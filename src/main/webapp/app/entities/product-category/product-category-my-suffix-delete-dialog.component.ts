import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductCategoryMySuffix } from './product-category-my-suffix.model';
import { ProductCategoryMySuffixPopupService } from './product-category-my-suffix-popup.service';
import { ProductCategoryMySuffixService } from './product-category-my-suffix.service';

@Component({
    selector: 'jhi-product-category-my-suffix-delete-dialog',
    templateUrl: './product-category-my-suffix-delete-dialog.component.html'
})
export class ProductCategoryMySuffixDeleteDialogComponent {

    productCategory: ProductCategoryMySuffix;

    constructor(
        private productCategoryService: ProductCategoryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productCategoryListModification',
                content: 'Deleted an productCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-category-my-suffix-delete-popup',
    template: ''
})
export class ProductCategoryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productCategoryPopupService: ProductCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productCategoryPopupService
                .open(ProductCategoryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
