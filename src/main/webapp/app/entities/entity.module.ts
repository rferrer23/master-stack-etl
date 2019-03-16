import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EtlBigdataStackOverFlowApiClientModule } from './stack-over-flow-api-client/stack-over-flow-api-client.module';
import { EtlBigdataExtraModule } from './extra/extra.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        EtlBigdataStackOverFlowApiClientModule,
        EtlBigdataExtraModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EtlBigdataEntityModule {}
