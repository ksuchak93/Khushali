import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShipProductMySuffix } from './ship-product-my-suffix.model';
import { ShipProductMySuffixPopupService } from './ship-product-my-suffix-popup.service';
import { ShipProductMySuffixService } from './ship-product-my-suffix.service';

@Component({
    selector: 'jhi-ship-product-my-suffix-delete-dialog',
    templateUrl: './ship-product-my-suffix-delete-dialog.component.html'
})
export class ShipProductMySuffixDeleteDialogComponent {

    shipProduct: ShipProductMySuffix;

    constructor(
        private shipProductService: ShipProductMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipProductService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shipProductListModification',
                content: 'Deleted an shipProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ship-product-my-suffix-delete-popup',
    template: ''
})
export class ShipProductMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipProductPopupService: ShipProductMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shipProductPopupService
                .open(ShipProductMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
