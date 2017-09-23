import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContactCategoryMySuffix } from './contact-category-my-suffix.model';
import { ContactCategoryMySuffixPopupService } from './contact-category-my-suffix-popup.service';
import { ContactCategoryMySuffixService } from './contact-category-my-suffix.service';

@Component({
    selector: 'jhi-contact-category-my-suffix-dialog',
    templateUrl: './contact-category-my-suffix-dialog.component.html'
})
export class ContactCategoryMySuffixDialogComponent implements OnInit {

    contactCategory: ContactCategoryMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contactCategoryService: ContactCategoryMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contactCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactCategoryService.update(this.contactCategory));
        } else {
            this.subscribeToSaveResponse(
                this.contactCategoryService.create(this.contactCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContactCategoryMySuffix>) {
        result.subscribe((res: ContactCategoryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ContactCategoryMySuffix) {
        this.eventManager.broadcast({ name: 'contactCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-contact-category-my-suffix-popup',
    template: ''
})
export class ContactCategoryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactCategoryPopupService: ContactCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactCategoryPopupService
                    .open(ContactCategoryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.contactCategoryPopupService
                    .open(ContactCategoryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
