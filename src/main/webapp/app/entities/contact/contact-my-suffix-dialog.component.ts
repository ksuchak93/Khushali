import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ContactMySuffix } from './contact-my-suffix.model';
import { ContactMySuffixPopupService } from './contact-my-suffix-popup.service';
import { ContactMySuffixService } from './contact-my-suffix.service';
import { CompanyMySuffix, CompanyMySuffixService } from '../company';
import { ContactCategoryMySuffix, ContactCategoryMySuffixService } from '../contact-category';
import { AddressMySuffix, AddressMySuffixService } from '../address';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-contact-my-suffix-dialog',
    templateUrl: './contact-my-suffix-dialog.component.html'
})
export class ContactMySuffixDialogComponent implements OnInit {

    contact: ContactMySuffix;
    isSaving: boolean;

    companies: CompanyMySuffix[];

    contactcategories: ContactCategoryMySuffix[];

    addresses: AddressMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private contactService: ContactMySuffixService,
        private companyService: CompanyMySuffixService,
        private contactCategoryService: ContactCategoryMySuffixService,
        private addressService: AddressMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.companyService.query()
            .subscribe((res: ResponseWrapper) => { this.companies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.contactCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.contactcategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.addressService.query()
            .subscribe((res: ResponseWrapper) => { this.addresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.contactService.update(this.contact));
        } else {
            this.subscribeToSaveResponse(
                this.contactService.create(this.contact));
        }
    }

    private subscribeToSaveResponse(result: Observable<ContactMySuffix>) {
        result.subscribe((res: ContactMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ContactMySuffix) {
        this.eventManager.broadcast({ name: 'contactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackCompanyById(index: number, item: CompanyMySuffix) {
        return item.id;
    }

    trackContactCategoryById(index: number, item: ContactCategoryMySuffix) {
        return item.id;
    }

    trackAddressById(index: number, item: AddressMySuffix) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-contact-my-suffix-popup',
    template: ''
})
export class ContactMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private contactPopupService: ContactMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.contactPopupService
                    .open(ContactMySuffixDialogComponent as Component, params['id']);
            } else {
                this.contactPopupService
                    .open(ContactMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
