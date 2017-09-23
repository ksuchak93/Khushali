import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProductDemoMySuffix } from './product-demo-my-suffix.model';
import { ProductDemoMySuffixService } from './product-demo-my-suffix.service';

@Component({
    selector: 'jhi-product-demo-my-suffix-detail',
    templateUrl: './product-demo-my-suffix-detail.component.html'
})
export class ProductDemoMySuffixDetailComponent implements OnInit, OnDestroy {

    productDemo: ProductDemoMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private productDemoService: ProductDemoMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductDemos();
    }

    load(id) {
        this.productDemoService.find(id).subscribe((productDemo) => {
            this.productDemo = productDemo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductDemos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productDemoListModification',
            (response) => this.load(this.productDemo.id)
        );
    }
}
