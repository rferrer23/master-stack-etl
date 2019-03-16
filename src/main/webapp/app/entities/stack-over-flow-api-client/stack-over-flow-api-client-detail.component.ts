import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

@Component({
    selector: 'jhi-stack-over-flow-api-client-detail',
    templateUrl: './stack-over-flow-api-client-detail.component.html'
})
export class StackOverFlowApiClientDetailComponent implements OnInit {
    stackOverFlowApiClient: IStackOverFlowApiClient;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stackOverFlowApiClient }) => {
            this.stackOverFlowApiClient = stackOverFlowApiClient;
        });
    }

    previousState() {
        window.history.back();
    }
}
