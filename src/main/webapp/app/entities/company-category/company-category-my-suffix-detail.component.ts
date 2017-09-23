import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyCategoryMySuffix } from './company-category-my-suffix.model';
import { CompanyCategoryMySuffixService } from './company-category-my-suffix.service';

@Component({
    selector: 'jhi-company-category-my-suffix-detail',
    templateUrl: './company-category-my-suffix-detail.component.html'
})
export class CompanyCategoryMySuffixDetailComponent implements OnInit, OnDestroy {

    companyCategory: CompanyCategoryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private companyCategoryService: CompanyCategoryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCompanyCategories();
    }

    load(id) {
        this.companyCategoryService.find(id).subscribe((companyCategory) => {
            this.companyCategory = companyCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCompanyCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'companyCategoryListModification',
            (response) => this.load(this.companyCategory.id)
        );
    }
}
