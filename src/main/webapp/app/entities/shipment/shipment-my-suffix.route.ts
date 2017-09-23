import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShipmentMySuffixComponent } from './shipment-my-suffix.component';
import { ShipmentMySuffixDetailComponent } from './shipment-my-suffix-detail.component';
import { ShipmentMySuffixPopupComponent } from './shipment-my-suffix-dialog.component';
import { ShipmentMySuffixDeletePopupComponent } from './shipment-my-suffix-delete-dialog.component';

@Injectable()
export class ShipmentMySuffixResolvePagingParams implements Resolve<any> {

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

export const shipmentRoute: Routes = [
    {
        path: 'shipment-my-suffix',
        component: ShipmentMySuffixComponent,
        resolve: {
            'pagingParams': ShipmentMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shipment-my-suffix/:id',
        component: ShipmentMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipmentPopupRoute: Routes = [
    {
        path: 'shipment-my-suffix-new',
        component: ShipmentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipment-my-suffix/:id/edit',
        component: ShipmentMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shipment-my-suffix/:id/delete',
        component: ShipmentMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
