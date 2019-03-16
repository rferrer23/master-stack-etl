import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Extra } from 'app/shared/model/extra.model';
import { ExtraService } from './extra.service';
import { ExtraComponent } from './extra.component';
import { ExtraDetailComponent } from './extra-detail.component';
import { ExtraUpdateComponent } from './extra-update.component';
import { ExtraDeletePopupComponent } from './extra-delete-dialog.component';
import { IExtra } from 'app/shared/model/extra.model';

@Injectable({ providedIn: 'root' })
export class ExtraResolve implements Resolve<IExtra> {
    constructor(private service: ExtraService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((extra: HttpResponse<Extra>) => extra.body));
        }
        return of(new Extra());
    }
}

export const extraRoute: Routes = [
    {
        path: 'extra',
        component: ExtraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'extra/:id/view',
        component: ExtraDetailComponent,
        resolve: {
            extra: ExtraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'extra/new',
        component: ExtraUpdateComponent,
        resolve: {
            extra: ExtraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'extra/:id/edit',
        component: ExtraUpdateComponent,
        resolve: {
            extra: ExtraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const extraPopupRoute: Routes = [
    {
        path: 'extra/:id/delete',
        component: ExtraDeletePopupComponent,
        resolve: {
            extra: ExtraResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.extra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
