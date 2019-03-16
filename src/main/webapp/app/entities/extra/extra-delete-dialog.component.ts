import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExtra } from 'app/shared/model/extra.model';
import { ExtraService } from './extra.service';

@Component({
    selector: 'jhi-extra-delete-dialog',
    templateUrl: './extra-delete-dialog.component.html'
})
export class ExtraDeleteDialogComponent {
    extra: IExtra;

    constructor(private extraService: ExtraService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.extraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'extraListModification',
                content: 'Deleted an extra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-extra-delete-popup',
    template: ''
})
export class ExtraDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ extra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExtraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.extra = extra;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
