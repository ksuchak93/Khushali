import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SalesOrderMySuffixComponent } from './sales-order-my-suffix.component';
import { SalesOrderMySuffixDetailComponent } from './sales-order-my-suffix-detail.component';
import { SalesOrderMySuffixPopupComponent } from './sales-order-my-suffix-dialog.component';
import { SalesOrderMySuffixDeletePopupComponent } from './sales-order-my-suffix-delete-dialog.component';

@Injectable()
export class SalesOrderMySuffixResolvePagingParams implements Resolve<any> {

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

export const salesOrderRoute: Routes = [
    {
        path: 'sales-order-my-suffix',
        component: SalesOrderMySuffixComponent,
        resolve: {
            'pagingParams': SalesOrderMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sales-order-my-suffix/:id',
        component: SalesOrderMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrder.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salesOrderPopupRoute: Routes = [
    {
        path: 'sales-order-my-suffix-new',
        component: SalesOrderMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-order-my-suffix/:id/edit',
        component: SalesOrderMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sales-order-my-suffix/:id/delete',
        component: SalesOrderMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.salesOrder.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
