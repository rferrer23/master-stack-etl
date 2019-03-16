import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';
import { StackOverFlowApiClientService } from './stack-over-flow-api-client.service';
import { StackOverFlowApiClientComponent } from './stack-over-flow-api-client.component';
import { StackOverFlowApiClientDetailComponent } from './stack-over-flow-api-client-detail.component';
import { StackOverFlowApiClientUpdateComponent } from './stack-over-flow-api-client-update.component';
import { StackOverFlowApiClientDeletePopupComponent } from './stack-over-flow-api-client-delete-dialog.component';
import { IStackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

@Injectable({ providedIn: 'root' })
export class StackOverFlowApiClientResolve implements Resolve<IStackOverFlowApiClient> {
    constructor(private service: StackOverFlowApiClientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((stackOverFlowApiClient: HttpResponse<StackOverFlowApiClient>) => stackOverFlowApiClient.body));
        }
        return of(new StackOverFlowApiClient());
    }
}

export const stackOverFlowApiClientRoute: Routes = [
    {
        path: 'stack-over-flow-api-client',
        component: StackOverFlowApiClientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.stackOverFlowApiClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stack-over-flow-api-client/:id/view',
        component: StackOverFlowApiClientDetailComponent,
        resolve: {
            stackOverFlowApiClient: StackOverFlowApiClientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.stackOverFlowApiClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stack-over-flow-api-client/new',
        component: StackOverFlowApiClientUpdateComponent,
        resolve: {
            stackOverFlowApiClient: StackOverFlowApiClientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.stackOverFlowApiClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stack-over-flow-api-client/:id/edit',
        component: StackOverFlowApiClientUpdateComponent,
        resolve: {
            stackOverFlowApiClient: StackOverFlowApiClientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.stackOverFlowApiClient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stackOverFlowApiClientPopupRoute: Routes = [
    {
        path: 'stack-over-flow-api-client/:id/delete',
        component: StackOverFlowApiClientDeletePopupComponent,
        resolve: {
            stackOverFlowApiClient: StackOverFlowApiClientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'etlBigdataApp.stackOverFlowApiClient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
