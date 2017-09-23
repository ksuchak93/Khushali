import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ShipmentMySuffix } from './shipment-my-suffix.model';
import { ShipmentMySuffixPopupService } from './shipment-my-suffix-popup.service';
import { ShipmentMySuffixService } from './shipment-my-suffix.service';

@Component({
    selector: 'jhi-shipment-my-suffix-delete-dialog',
    templateUrl: './shipment-my-suffix-delete-dialog.component.html'
})
export class ShipmentMySuffixDeleteDialogComponent {

    shipment: ShipmentMySuffix;

    constructor(
        private shipmentService: ShipmentMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shipmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shipmentListModification',
                content: 'Deleted an shipment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shipment-my-suffix-delete-popup',
    template: ''
})
export class ShipmentMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shipmentPopupService: ShipmentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shipmentPopupService
                .open(ShipmentMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
