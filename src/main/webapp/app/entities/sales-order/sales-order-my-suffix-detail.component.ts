import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SalesOrderMySuffix } from './sales-order-my-suffix.model';
import { SalesOrderMySuffixService } from './sales-order-my-suffix.service';

@Component({
    selector: 'jhi-sales-order-my-suffix-detail',
    templateUrl: './sales-order-my-suffix-detail.component.html'
})
export class SalesOrderMySuffixDetailComponent implements OnInit, OnDestroy {

    salesOrder: SalesOrderMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private salesOrderService: SalesOrderMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSalesOrders();
    }

    load(id) {
        this.salesOrderService.find(id).subscribe((salesOrder) => {
            this.salesOrder = salesOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSalesOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'salesOrderListModification',
            (response) => this.load(this.salesOrder.id)
        );
    }
}
