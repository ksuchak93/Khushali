import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ShipProductMySuffix } from './ship-product-my-suffix.model';
import { ShipProductMySuffixService } from './ship-product-my-suffix.service';

@Component({
    selector: 'jhi-ship-product-my-suffix-detail',
    templateUrl: './ship-product-my-suffix-detail.component.html'
})
export class ShipProductMySuffixDetailComponent implements OnInit, OnDestroy {

    shipProduct: ShipProductMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shipProductService: ShipProductMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShipProducts();
    }

    load(id) {
        this.shipProductService.find(id).subscribe((shipProduct) => {
            this.shipProduct = shipProduct;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShipProducts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shipProductListModification',
            (response) => this.load(this.shipProduct.id)
        );
    }
}
