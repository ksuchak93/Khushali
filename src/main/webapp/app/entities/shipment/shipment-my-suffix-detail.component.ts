import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShipmentMySuffix } from './shipment-my-suffix.model';
import { ShipmentMySuffixService } from './shipment-my-suffix.service';

@Component({
    selector: 'jhi-shipment-my-suffix-detail',
    templateUrl: './shipment-my-suffix-detail.component.html'
})
export class ShipmentMySuffixDetailComponent implements OnInit, OnDestroy {

    shipment: ShipmentMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shipmentService: ShipmentMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShipments();
    }

    load(id) {
        this.shipmentService.find(id).subscribe((shipment) => {
            this.shipment = shipment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShipments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shipmentListModification',
            (response) => this.load(this.shipment.id)
        );
    }
}
