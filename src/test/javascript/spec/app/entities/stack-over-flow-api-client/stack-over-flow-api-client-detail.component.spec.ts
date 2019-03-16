/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtlBigdataTestModule } from '../../../test.module';
import { StackOverFlowApiClientDetailComponent } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client-detail.component';
import { StackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

describe('Component Tests', () => {
    describe('StackOverFlowApiClient Management Detail Component', () => {
        let comp: StackOverFlowApiClientDetailComponent;
        let fixture: ComponentFixture<StackOverFlowApiClientDetailComponent>;
        const route = ({ data: of({ stackOverFlowApiClient: new StackOverFlowApiClient(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EtlBigdataTestModule],
                declarations: [StackOverFlowApiClientDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StackOverFlowApiClientDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StackOverFlowApiClientDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stackOverFlowApiClient).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
