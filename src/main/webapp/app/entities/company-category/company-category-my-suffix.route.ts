import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanyCategoryMySuffixComponent } from './company-category-my-suffix.component';
import { CompanyCategoryMySuffixDetailComponent } from './company-category-my-suffix-detail.component';
import { CompanyCategoryMySuffixPopupComponent } from './company-category-my-suffix-dialog.component';
import { CompanyCategoryMySuffixDeletePopupComponent } from './company-category-my-suffix-delete-dialog.component';

@Injectable()
export class CompanyCategoryMySuffixResolvePagingParams implements Resolve<any> {

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

export const companyCategoryRoute: Routes = [
    {
        path: 'company-category-my-suffix',
        component: CompanyCategoryMySuffixComponent,
        resolve: {
            'pagingParams': CompanyCategoryMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.companyCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-category-my-suffix/:id',
        component: CompanyCategoryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.companyCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companyCategoryPopupRoute: Routes = [
    {
        path: 'company-category-my-suffix-new',
        component: CompanyCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.companyCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-category-my-suffix/:id/edit',
        component: CompanyCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.companyCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-category-my-suffix/:id/delete',
        component: CompanyCategoryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.companyCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
