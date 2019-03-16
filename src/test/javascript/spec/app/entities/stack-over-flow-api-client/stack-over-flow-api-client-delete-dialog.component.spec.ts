/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EtlBigdataTestModule } from '../../../test.module';
import { StackOverFlowApiClientDeleteDialogComponent } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client-delete-dialog.component';
import { StackOverFlowApiClientService } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client.service';

describe('Component Tests', () => {
    describe('StackOverFlowApiClient Management Delete Component', () => {
        let comp: StackOverFlowApiClientDeleteDialogComponent;
        let fixture: ComponentFixture<StackOverFlowApiClientDeleteDialogComponent>;
        let service: StackOverFlowApiClientService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EtlBigdataTestModule],
                declarations: [StackOverFlowApiClientDeleteDialogComponent]
            })
                .overrideTemplate(StackOverFlowApiClientDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StackOverFlowApiClientDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StackOverFlowApiClientService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
