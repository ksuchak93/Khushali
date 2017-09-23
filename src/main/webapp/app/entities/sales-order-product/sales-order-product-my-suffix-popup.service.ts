import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SalesOrderProductMySuffix } from './sales-order-product-my-suffix.model';
import { SalesOrderProductMySuffixService } from './sales-order-product-my-suffix.service';

@Injectable()
export class SalesOrderProductMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private salesOrderProductService: SalesOrderProductMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.salesOrderProductService.find(id).subscribe((salesOrderProduct) => {
                    salesOrderProduct.shipDate = this.datePipe
                        .transform(salesOrderProduct.shipDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.salesOrderProductModalRef(component, salesOrderProduct);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.salesOrderProductModalRef(component, new SalesOrderProductMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    salesOrderProductModalRef(component: Component, salesOrderProduct: SalesOrderProductMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.salesOrderProduct = salesOrderProduct;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
