import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SalesOrderProductMySuffix } from './sales-order-product-my-suffix.model';
import { SalesOrderProductMySuffixService } from './sales-order-product-my-suffix.service';

@Component({
    selector: 'jhi-sales-order-product-my-suffix-detail',
    templateUrl: './sales-order-product-my-suffix-detail.component.html'
})
export class SalesOrderProductMySuffixDetailComponent implements OnInit, OnDestroy {

    salesOrderProduct: SalesOrderProductMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private salesOrderProductService: SalesOrderProductMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSalesOrderProducts();
    }

    load(id) {
        this.salesOrderProductService.find(id).subscribe((salesOrderProduct) => {
            this.salesOrderProduct = salesOrderProduct;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSalesOrderProducts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'salesOrderProductListModification',
            (response) => this.load(this.salesOrderProduct.id)
        );
    }
}
