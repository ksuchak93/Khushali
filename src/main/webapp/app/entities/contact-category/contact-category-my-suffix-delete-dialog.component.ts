import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ContactCategoryMySuffix } from './contact-category-my-suffix.model';
import { ContactCategoryMySuffixPopupService } from './contact-category-my-suffix-popup.service';
import { ContactCategoryMySuffixService } from './contact-category-my-suffix.service';

@Component({
    selector: 'jhi-contact-category-my-suffix-delete-dialog',
    templateUrl: './contact-category-my-suffix-delete-dialog.component.html'
})
export class ContactCategoryMySuffixDeleteDialogComponent {

    contactCategory: ContactCategoryMySuffix;

    constructor(
        private contactCategoryService: ContactCategoryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.contactCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'contactCategoryListModification',
                content: 'Deleted an contactCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-contact-category-my-suffix-delete-popup',
    template: ''
})
export class ContactCategoryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactCategoryPopupService: ContactCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.contactCategoryPopupService
                .open(ContactCategoryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
