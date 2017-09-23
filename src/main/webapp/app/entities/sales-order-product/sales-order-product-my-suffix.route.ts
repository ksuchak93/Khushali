import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SalesOrderProductMySuffixComponent } from './sales-order-product-my-suffix.component';
import { SalesOrderProductMySuffixDetailComponent } from './sales-order-product-my-suffix-detail.component';
import { SalesOrderProductMySuffixPopupComponent } from './sales-order-product-my-suffix-dialog.component';
import { SalesOrderProductMySuffixDeletePopupComponent } from './sales-order-product-my-suffix-delete-dialog.component';

@Injectable()
export class SalesOrderProductMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const salesOrderProductRoute: Routes = [
    {
        path: 'sales-order-product-my-suffix',
        component: SalesOrderProductMySuffixComponent,
        resolve: {
            'pagingParams': SalesOrderProductMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrderProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sales-order-product-my-suffix/:id',
        component: SalesOrderProductMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrderProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salesOrderProductPopupRoute: Routes = [
    {
        path: 'sales-order-product-my-suffix-new',
        component: SalesOrderProductMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrderProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-order-product-my-suffix/:id/edit',
        component: SalesOrderProductMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrderProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-order-product-my-suffix/:id/delete',
        component: SalesOrderProductMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrderProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
