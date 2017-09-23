import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CompanyCategoryMySuffix } from './company-category-my-suffix.model';
import { CompanyCategoryMySuffixPopupService } from './company-category-my-suffix-popup.service';
import { CompanyCategoryMySuffixService } from './company-category-my-suffix.service';

@Component({
    selector: 'jhi-company-category-my-suffix-delete-dialog',
    templateUrl: './company-category-my-suffix-delete-dialog.component.html'
})
export class CompanyCategoryMySuffixDeleteDialogComponent {

    companyCategory: CompanyCategoryMySuffix;

    constructor(
        private companyCategoryService: CompanyCategoryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.companyCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'companyCategoryListModification',
                content: 'Deleted an companyCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-category-my-suffix-delete-popup',
    template: ''
})
export class CompanyCategoryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private companyCategoryPopupService: CompanyCategoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.companyCategoryPopupService
                .open(CompanyCategoryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
