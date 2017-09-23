import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SalesOrderProductMySuffix } from './sales-order-product-my-suffix.model';
import { SalesOrderProductMySuffixPopupService } from './sales-order-product-my-suffix-popup.service';
import { SalesOrderProductMySuffixService } from './sales-order-product-my-suffix.service';

@Component({
    selector: 'jhi-sales-order-product-my-suffix-delete-dialog',
    templateUrl: './sales-order-product-my-suffix-delete-dialog.component.html'
})
export class SalesOrderProductMySuffixDeleteDialogComponent {

    salesOrderProduct: SalesOrderProductMySuffix;

    constructor(
        private salesOrderProductService: SalesOrderProductMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salesOrderProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'salesOrderProductListModification',
                content: 'Deleted an salesOrderProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sales-order-product-my-suffix-delete-popup',
    template: ''
})
export class SalesOrderProductMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesOrderProductPopupService: SalesOrderProductMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.salesOrderProductPopupService
                .open(SalesOrderProductMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
