import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProductCategoryMySuffixComponent } from './product-category-my-suffix.component';
import { ProductCategoryMySuffixDetailComponent } from './product-category-my-suffix-detail.component';
import { ProductCategoryMySuffixPopupComponent } from './product-category-my-suffix-dialog.component';
import { ProductCategoryMySuffixDeletePopupComponent } from './product-category-my-suffix-delete-dialog.component';

@Injectable()
export class ProductCategoryMySuffixResolvePagingParams implements Resolve<any> {

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

export const productCategoryRoute: Routes = [
    {
        path: 'product-category-my-suffix',
        component: ProductCategoryMySuffixComponent,
        resolve: {
            'pagingParams': ProductCategoryMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'product-category-my-suffix/:id',
        component: ProductCategoryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productCategoryPopupRoute: Routes = [
    {
        path: 'product-category-my-suffix-new',
        component: ProductCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-category-my-suffix/:id/edit',
        component: ProductCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'product-category-my-suffix/:id/delete',
        component: ProductCategoryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.productCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
