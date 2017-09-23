import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactMySuffix } from './contact-my-suffix.model';
import { ContactMySuffixPopupService } from './contact-my-suffix-popup.service';
import { ContactMySuffixService } from './contact-my-suffix.service';

@Component({
    selector: 'jhi-contact-my-suffix-delete-dialog',
    templateUrl: './contact-my-suffix-delete-dialog.component.html'
})
export class ContactMySuffixDeleteDialogComponent {

    contact: ContactMySuffix;

    constructor(
        private contactService: ContactMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contactListModification',
                content: 'Deleted an contact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-my-suffix-delete-popup',
    template: ''
})
export class ContactMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPopupService: ContactMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contactPopupService
                .open(ContactMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
