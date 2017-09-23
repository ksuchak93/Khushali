import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductDemoMySuffix } from './product-demo-my-suffix.model';
import { ProductDemoMySuffixPopupService } from './product-demo-my-suffix-popup.service';
import { ProductDemoMySuffixService } from './product-demo-my-suffix.service';

@Component({
    selector: 'jhi-product-demo-my-suffix-delete-dialog',
    templateUrl: './product-demo-my-suffix-delete-dialog.component.html'
})
export class ProductDemoMySuffixDeleteDialogComponent {

    productDemo: ProductDemoMySuffix;

    constructor(
        private productDemoService: ProductDemoMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productDemoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productDemoListModification',
                content: 'Deleted an productDemo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-demo-my-suffix-delete-popup',
    template: ''
})
export class ProductDemoMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productDemoPopupService: ProductDemoMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productDemoPopupService
                .open(ProductDemoMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
