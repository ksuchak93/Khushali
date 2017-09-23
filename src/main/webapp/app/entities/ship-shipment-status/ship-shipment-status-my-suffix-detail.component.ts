import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShipShipmentStatusMySuffix } from './ship-shipment-status-my-suffix.model';
import { ShipShipmentStatusMySuffixService } from './ship-shipment-status-my-suffix.service';

@Component({
    selector: 'jhi-ship-shipment-status-my-suffix-detail',
    templateUrl: './ship-shipment-status-my-suffix-detail.component.html'
})
export class ShipShipmentStatusMySuffixDetailComponent implements OnInit, OnDestroy {

    shipShipmentStatus: ShipShipmentStatusMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shipShipmentStatusService: ShipShipmentStatusMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShipShipmentStatuses();
    }

    load(id) {
        this.shipShipmentStatusService.find(id).subscribe((shipShipmentStatus) => {
            this.shipShipmentStatus = shipShipmentStatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShipShipmentStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shipShipmentStatusListModification',
            (response) => this.load(this.shipShipmentStatus.id)
        );
    }
}
