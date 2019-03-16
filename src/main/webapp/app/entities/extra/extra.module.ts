import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EtlBigdataSharedModule } from 'app/shared';
import {
    ExtraComponent,
    ExtraDetailComponent,
    ExtraUpdateComponent,
    ExtraDeletePopupComponent,
    ExtraDeleteDialogComponent,
    extraRoute,
    extraPopupRoute
} from './';

const ENTITY_STATES = [...extraRoute, ...extraPopupRoute];

@NgModule({
    imports: [EtlBigdataSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ExtraComponent, ExtraDetailComponent, ExtraUpdateComponent, ExtraDeleteDialogComponent, ExtraDeletePopupComponent],
    entryComponents: [ExtraComponent, ExtraUpdateComponent, ExtraDeleteDialogComponent, ExtraDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtlBigdataExtraModule {}
