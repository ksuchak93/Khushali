import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShipProductMySuffixComponent } from './ship-product-my-suffix.component';
import { ShipProductMySuffixDetailComponent } from './ship-product-my-suffix-detail.component';
import { ShipProductMySuffixPopupComponent } from './ship-product-my-suffix-dialog.component';
import { ShipProductMySuffixDeletePopupComponent } from './ship-product-my-suffix-delete-dialog.component';

@Injectable()
export class ShipProductMySuffixResolvePagingParams implements Resolve<any> {

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

export const shipProductRoute: Routes = [
    {
        path: 'ship-product-my-suffix',
        component: ShipProductMySuffixComponent,
        resolve: {
            'pagingParams': ShipProductMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ship-product-my-suffix/:id',
        component: ShipProductMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipProduct.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipProductPopupRoute: Routes = [
    {
        path: 'ship-product-my-suffix-new',
        component: ShipProductMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ship-product-my-suffix/:id/edit',
        component: ShipProductMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ship-product-my-suffix/:id/delete',
        component: ShipProductMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipProduct.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
