import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShipShipmentStatusMySuffixComponent } from './ship-shipment-status-my-suffix.component';
import { ShipShipmentStatusMySuffixDetailComponent } from './ship-shipment-status-my-suffix-detail.component';
import { ShipShipmentStatusMySuffixPopupComponent } from './ship-shipment-status-my-suffix-dialog.component';
import { ShipShipmentStatusMySuffixDeletePopupComponent } from './ship-shipment-status-my-suffix-delete-dialog.component';

@Injectable()
export class ShipShipmentStatusMySuffixResolvePagingParams implements Resolve<any> {

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

export const shipShipmentStatusRoute: Routes = [
    {
        path: 'ship-shipment-status-my-suffix',
        component: ShipShipmentStatusMySuffixComponent,
        resolve: {
            'pagingParams': ShipShipmentStatusMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipShipmentStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ship-shipment-status-my-suffix/:id',
        component: ShipShipmentStatusMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipShipmentStatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shipShipmentStatusPopupRoute: Routes = [
    {
        path: 'ship-shipment-status-my-suffix-new',
        component: ShipShipmentStatusMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipShipmentStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ship-shipment-status-my-suffix/:id/edit',
        component: ShipShipmentStatusMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipShipmentStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ship-shipment-status-my-suffix/:id/delete',
        component: ShipShipmentStatusMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.shipShipmentStatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
