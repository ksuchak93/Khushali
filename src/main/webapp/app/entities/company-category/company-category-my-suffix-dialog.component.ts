import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CompanyCategoryMySuffix } from './company-category-my-suffix.model';
import { CompanyCategoryMySuffixPopupService } from './company-category-my-suffix-popup.service';
import { CompanyCategoryMySuffixService } from './company-category-my-suffix.service';

@Component({
    selector: 'jhi-company-category-my-suffix-dialog',
    templateUrl: './company-category-my-suffix-dialog.component.html'
})
export class CompanyCategoryMySuffixDialogComponent implements OnInit {

    companyCategory: CompanyCategoryMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private companyCategoryService: CompanyCategoryMySuffixService,
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
        if (this.companyCategory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.companyCategoryService.update(this.companyCategory));
        } else {
            this.subscribeToSaveResponse(
                this.companyCategoryService.create(this.companyCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<CompanyCategoryMySuffix>) {
        result.subscribe((res: CompanyCategoryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CompanyCategoryMySuffix) {
        this.eventManager.broadcast({ name: 'companyCategoryListModification', content: 'OK'});
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
    selector: 'jhi-company-category-my-suffix-popup',
    template: ''
})
export class CompanyCategoryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyCategoryPopupService: CompanyCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.companyCategoryPopupService
                    .open(CompanyCategoryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.companyCategoryPopupService
                    .open(CompanyCategoryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
