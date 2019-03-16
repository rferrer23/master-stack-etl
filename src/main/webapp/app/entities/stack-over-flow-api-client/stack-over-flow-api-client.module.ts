import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtlBigdataSharedModule } from 'app/shared';
import {
    StackOverFlowApiClientComponent,
    StackOverFlowApiClientDetailComponent,
    StackOverFlowApiClientUpdateComponent,
    StackOverFlowApiClientDeletePopupComponent,
    StackOverFlowApiClientDeleteDialogComponent,
    stackOverFlowApiClientRoute,
    stackOverFlowApiClientPopupRoute
} from './';

const ENTITY_STATES = [...stackOverFlowApiClientRoute, ...stackOverFlowApiClientPopupRoute];

@NgModule({
    imports: [EtlBigdataSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StackOverFlowApiClientComponent,
        StackOverFlowApiClientDetailComponent,
        StackOverFlowApiClientUpdateComponent,
        StackOverFlowApiClientDeleteDialogComponent,
        StackOverFlowApiClientDeletePopupComponent
    ],
    entryComponents: [
        StackOverFlowApiClientComponent,
        StackOverFlowApiClientUpdateComponent,
        StackOverFlowApiClientDeleteDialogComponent,
        StackOverFlowApiClientDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtlBigdataStackOverFlowApiClientModule {}
