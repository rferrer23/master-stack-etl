import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExtra } from 'app/shared/model/extra.model';

@Component({
    selector: 'jhi-extra-detail',
    templateUrl: './extra-detail.component.html'
})
export class ExtraDetailComponent implements OnInit {
    extra: IExtra;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
