import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';
import { StackOverFlowApiClientService } from './stack-over-flow-api-client.service';

@Component({
    selector: 'jhi-stack-over-flow-api-client-delete-dialog',
    templateUrl: './stack-over-flow-api-client-delete-dialog.component.html'
})
export class StackOverFlowApiClientDeleteDialogComponent {
    stackOverFlowApiClient: IStackOverFlowApiClient;

    constructor(
        private stackOverFlowApiClientService: StackOverFlowApiClientService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stackOverFlowApiClientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stackOverFlowApiClientListModification',
                content: 'Deleted an stackOverFlowApiClient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stack-over-flow-api-client-delete-popup',
    template: ''
})
export class StackOverFlowApiClientDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stackOverFlowApiClient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StackOverFlowApiClientDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stackOverFlowApiClient = stackOverFlowApiClient;
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
