import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ContactCategoryMySuffix } from './contact-category-my-suffix.model';
import { ContactCategoryMySuffixService } from './contact-category-my-suffix.service';

@Component({
    selector: 'jhi-contact-category-my-suffix-detail',
    templateUrl: './contact-category-my-suffix-detail.component.html'
})
export class ContactCategoryMySuffixDetailComponent implements OnInit, OnDestroy {

    contactCategory: ContactCategoryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contactCategoryService: ContactCategoryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContactCategories();
    }

    load(id) {
        this.contactCategoryService.find(id).subscribe((contactCategory) => {
            this.contactCategory = contactCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContactCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactCategoryListModification',
            (response) => this.load(this.contactCategory.id)
        );
    }
}
