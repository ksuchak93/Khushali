import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { SalesOrderMySuffix } from './sales-order-my-suffix.model';
import { SalesOrderMySuffixService } from './sales-order-my-suffix.service';

@Injectable()
export class SalesOrderMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private salesOrderService: SalesOrderMySuffixService

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
                this.salesOrderService.find(id).subscribe((salesOrder) => {
                    salesOrder.orderDate = this.datePipe
                        .transform(salesOrder.orderDate, 'yyyy-MM-ddTHH:mm:ss');
                    salesOrder.shipDate = this.datePipe
                        .transform(salesOrder.shipDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.salesOrderModalRef(component, salesOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.salesOrderModalRef(component, new SalesOrderMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    salesOrderModalRef(component: Component, salesOrder: SalesOrderMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.salesOrder = salesOrder;
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
