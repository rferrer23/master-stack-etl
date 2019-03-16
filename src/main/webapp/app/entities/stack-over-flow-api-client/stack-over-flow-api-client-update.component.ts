import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IStackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';
import { StackOverFlowApiClientService } from './stack-over-flow-api-client.service';
import { IExtra } from 'app/shared/model/extra.model';
import { ExtraService } from 'app/entities/extra';

@Component({
    selector: 'jhi-stack-over-flow-api-client-update',
    templateUrl: './stack-over-flow-api-client-update.component.html'
})
export class StackOverFlowApiClientUpdateComponent implements OnInit {
    stackOverFlowApiClient: IStackOverFlowApiClient;
    isSaving: boolean;

    extras: IExtra[];
    firstPeriodDp: any;
    lastPeriodDp: any;
    nextSendTimeDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private stackOverFlowApiClientService: StackOverFlowApiClientService,
        private extraService: ExtraService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stackOverFlowApiClient }) => {
            this.stackOverFlowApiClient = stackOverFlowApiClient;
        });
        this.extraService.query().subscribe(
            (res: HttpResponse<IExtra[]>) => {
                this.extras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stackOverFlowApiClient.id !== undefined) {
            this.subscribeToSaveResponse(this.stackOverFlowApiClientService.update(this.stackOverFlowApiClient));
        } else {
            this.subscribeToSaveResponse(this.stackOverFlowApiClientService.create(this.stackOverFlowApiClient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStackOverFlowApiClient>>) {
        result.subscribe(
            (res: HttpResponse<IStackOverFlowApiClient>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackExtraById(index: number, item: IExtra) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
