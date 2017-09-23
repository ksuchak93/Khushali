import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AddressMySuffix } from './address-my-suffix.model';
import { AddressMySuffixPopupService } from './address-my-suffix-popup.service';
import { AddressMySuffixService } from './address-my-suffix.service';
import { CountryMySuffix, CountryMySuffixService } from '../country';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-address-my-suffix-dialog',
    templateUrl: './address-my-suffix-dialog.component.html'
})
export class AddressMySuffixDialogComponent implements OnInit {

    address: AddressMySuffix;
    isSaving: boolean;

    countries: CountryMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private addressService: AddressMySuffixService,
        private countryService: CountryMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.countryService.query()
            .subscribe((res: ResponseWrapper) => { this.countries = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.address.id !== undefined) {
            this.subscribeToSaveResponse(
                this.addressService.update(this.address));
        } else {
            this.subscribeToSaveResponse(
                this.addressService.create(this.address));
        }
    }

    private subscribeToSaveResponse(result: Observable<AddressMySuffix>) {
        result.subscribe((res: AddressMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AddressMySuffix) {
        this.eventManager.broadcast({ name: 'addressListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackCountryById(index: number, item: CountryMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-address-my-suffix-popup',
    template: ''
})
export class AddressMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private addressPopupService: AddressMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.addressPopupService
                    .open(AddressMySuffixDialogComponent as Component, params['id']);
            } else {
                this.addressPopupService
                    .open(AddressMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
