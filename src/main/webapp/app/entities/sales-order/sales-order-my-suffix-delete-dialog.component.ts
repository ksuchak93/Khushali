import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SalesOrderMySuffix } from './sales-order-my-suffix.model';
import { SalesOrderMySuffixPopupService } from './sales-order-my-suffix-popup.service';
import { SalesOrderMySuffixService } from './sales-order-my-suffix.service';

@Component({
    selector: 'jhi-sales-order-my-suffix-delete-dialog',
    templateUrl: './sales-order-my-suffix-delete-dialog.component.html'
})
export class SalesOrderMySuffixDeleteDialogComponent {

    salesOrder: SalesOrderMySuffix;

    constructor(
        private salesOrderService: SalesOrderMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salesOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'salesOrderListModification',
                content: 'Deleted an salesOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sales-order-my-suffix-delete-popup',
    template: ''
})
export class SalesOrderMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private salesOrderPopupService: SalesOrderMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.salesOrderPopupService
                .open(SalesOrderMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
