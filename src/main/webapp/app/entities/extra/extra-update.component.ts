import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IExtra } from 'app/shared/model/extra.model';
import { ExtraService } from './extra.service';

@Component({
    selector: 'jhi-extra-update',
    templateUrl: './extra-update.component.html'
})
export class ExtraUpdateComponent implements OnInit {
    extra: IExtra;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private extraService: ExtraService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ extra }) => {
            this.extra = extra;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.extra.id !== undefined) {
            this.subscribeToSaveResponse(this.extraService.update(this.extra));
        } else {
            this.subscribeToSaveResponse(this.extraService.create(this.extra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IExtra>>) {
        result.subscribe((res: HttpResponse<IExtra>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
