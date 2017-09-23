import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyMySuffix } from './company-my-suffix.model';
import { CompanyMySuffixPopupService } from './company-my-suffix-popup.service';
import { CompanyMySuffixService } from './company-my-suffix.service';
import { AddressMySuffix, AddressMySuffixService } from '../address';
import { CompanyCategoryMySuffix, CompanyCategoryMySuffixService } from '../company-category';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-company-my-suffix-dialog',
    templateUrl: './company-my-suffix-dialog.component.html'
})
export class CompanyMySuffixDialogComponent implements OnInit {

    company: CompanyMySuffix;
    isSaving: boolean;

    addresses: AddressMySuffix[];

    companycategories: CompanyCategoryMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyService: CompanyMySuffixService,
        private addressService: AddressMySuffixService,
        private companyCategoryService: CompanyCategoryMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.addressService.query()
            .subscribe((res: ResponseWrapper) => { this.addresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.companyCategoryService.query()
            .subscribe((res: ResponseWrapper) => { this.companycategories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.company.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyService.update(this.company));
        } else {
            this.subscribeToSaveResponse(
                this.companyService.create(this.company));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyMySuffix>) {
        result.subscribe((res: CompanyMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyMySuffix) {
        this.eventManager.broadcast({ name: 'companyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: AddressMySuffix) {
        return item.id;
    }

    trackCompanyCategoryById(index: number, item: CompanyCategoryMySuffix) {
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
    selector: 'jhi-company-my-suffix-popup',
    template: ''
})
export class CompanyMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyPopupService: CompanyMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyPopupService
                    .open(CompanyMySuffixDialogComponent as Component, params['id']);
            } else {
                this.companyPopupService
                    .open(CompanyMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
