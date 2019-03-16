/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EtlBigdataTestModule } from '../../../test.module';
import { StackOverFlowApiClientUpdateComponent } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client-update.component';
import { StackOverFlowApiClientService } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client.service';
import { StackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

describe('Component Tests', () => {
    describe('StackOverFlowApiClient Management Update Component', () => {
        let comp: StackOverFlowApiClientUpdateComponent;
        let fixture: ComponentFixture<StackOverFlowApiClientUpdateComponent>;
        let service: StackOverFlowApiClientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EtlBigdataTestModule],
                declarations: [StackOverFlowApiClientUpdateComponent]
            })
                .overrideTemplate(StackOverFlowApiClientUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StackOverFlowApiClientUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StackOverFlowApiClientService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StackOverFlowApiClient(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stackOverFlowApiClient = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StackOverFlowApiClient();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stackOverFlowApiClient = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
