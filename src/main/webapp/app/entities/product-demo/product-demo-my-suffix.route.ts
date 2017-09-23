import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductDemoMySuffixComponent } from './product-demo-my-suffix.component';
import { ProductDemoMySuffixDetailComponent } from './product-demo-my-suffix-detail.component';
import { ProductDemoMySuffixPopupComponent } from './product-demo-my-suffix-dialog.component';
import { ProductDemoMySuffixDeletePopupComponent } from './product-demo-my-suffix-delete-dialog.component';

@Injectable()
export class ProductDemoMySuffixResolvePagingParams implements Resolve<any> {

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

export const productDemoRoute: Routes = [
    {
        path: 'product-demo-my-suffix',
        component: ProductDemoMySuffixComponent,
        resolve: {
            'pagingParams': ProductDemoMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productDemo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-demo-my-suffix/:id',
        component: ProductDemoMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productDemo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productDemoPopupRoute: Routes = [
    {
        path: 'product-demo-my-suffix-new',
        component: ProductDemoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productDemo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-demo-my-suffix/:id/edit',
        component: ProductDemoMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productDemo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-demo-my-suffix/:id/delete',
        component: ProductDemoMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productDemo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
