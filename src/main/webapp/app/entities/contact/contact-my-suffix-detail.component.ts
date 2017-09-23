import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ContactMySuffix } from './contact-my-suffix.model';
import { ContactMySuffixService } from './contact-my-suffix.service';

@Component({
    selector: 'jhi-contact-my-suffix-detail',
    templateUrl: './contact-my-suffix-detail.component.html'
})
export class ContactMySuffixDetailComponent implements OnInit, OnDestroy {

    contact: ContactMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contactService: ContactMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContacts();
    }

    load(id) {
        this.contactService.find(id).subscribe((contact) => {
            this.contact = contact;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContacts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contactListModification',
            (response) => this.load(this.contact.id)
        );
    }
}
