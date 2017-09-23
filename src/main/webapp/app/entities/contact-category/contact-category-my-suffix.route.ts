import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContactCategoryMySuffixComponent } from './contact-category-my-suffix.component';
import { ContactCategoryMySuffixDetailComponent } from './contact-category-my-suffix-detail.component';
import { ContactCategoryMySuffixPopupComponent } from './contact-category-my-suffix-dialog.component';
import { ContactCategoryMySuffixDeletePopupComponent } from './contact-category-my-suffix-delete-dialog.component';

@Injectable()
export class ContactCategoryMySuffixResolvePagingParams implements Resolve<any> {

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

export const contactCategoryRoute: Routes = [
    {
        path: 'contact-category-my-suffix',
        component: ContactCategoryMySuffixComponent,
        resolve: {
            'pagingParams': ContactCategoryMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contactCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact-category-my-suffix/:id',
        component: ContactCategoryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contactCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactCategoryPopupRoute: Routes = [
    {
        path: 'contact-category-my-suffix-new',
        component: ContactCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contactCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-category-my-suffix/:id/edit',
        component: ContactCategoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contactCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-category-my-suffix/:id/delete',
        component: ContactCategoryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contactCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
