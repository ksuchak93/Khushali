import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContactMySuffixComponent } from './contact-my-suffix.component';
import { ContactMySuffixDetailComponent } from './contact-my-suffix-detail.component';
import { ContactMySuffixPopupComponent } from './contact-my-suffix-dialog.component';
import { ContactMySuffixDeletePopupComponent } from './contact-my-suffix-delete-dialog.component';

@Injectable()
export class ContactMySuffixResolvePagingParams implements Resolve<any> {

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

export const contactRoute: Routes = [
    {
        path: 'contact-my-suffix',
        component: ContactMySuffixComponent,
        resolve: {
            'pagingParams': ContactMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact-my-suffix/:id',
        component: ContactMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactPopupRoute: Routes = [
    {
        path: 'contact-my-suffix-new',
        component: ContactMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-my-suffix/:id/edit',
        component: ContactMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-my-suffix/:id/delete',
        component: ContactMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khushFinalApp.contact.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
