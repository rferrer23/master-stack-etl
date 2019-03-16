/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { StackOverFlowApiClientService } from 'app/entities/stack-over-flow-api-client/stack-over-flow-api-client.service';
import { IStackOverFlowApiClient, StackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

describe('Service Tests', () => {
    describe('StackOverFlowApiClient Service', () => {
        let injector: TestBed;
        let service: StackOverFlowApiClientService;
        let httpMock: HttpTestingController;
        let elemDefault: IStackOverFlowApiClient;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StackOverFlowApiClientService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new StackOverFlowApiClient(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', currentDate, false, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        firstPeriod: currentDate.format(DATE_FORMAT),
                        lastPeriod: currentDate.format(DATE_FORMAT),
                        nextSendTime: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a StackOverFlowApiClient', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        firstPeriod: currentDate.format(DATE_FORMAT),
                        lastPeriod: currentDate.format(DATE_FORMAT),
                        nextSendTime: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        firstPeriod: currentDate,
                        lastPeriod: currentDate,
                        nextSendTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new StackOverFlowApiClient(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a StackOverFlowApiClient', async () => {
                const returnedFromService = Object.assign(
                    {
                        users: 'BBBBBB',
                        firstPeriod: currentDate.format(DATE_FORMAT),
                        lastPeriod: currentDate.format(DATE_FORMAT),
                        tags: 'BBBBBB',
                        nextSendTime: currentDate.format(DATE_FORMAT),
                        active: true,
                        secret: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        firstPeriod: currentDate,
                        lastPeriod: currentDate,
                        nextSendTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of StackOverFlowApiClient', async () => {
                const returnedFromService = Object.assign(
                    {
                        users: 'BBBBBB',
                        firstPeriod: currentDate.format(DATE_FORMAT),
                        lastPeriod: currentDate.format(DATE_FORMAT),
                        tags: 'BBBBBB',
                        nextSendTime: currentDate.format(DATE_FORMAT),
                        active: true,
                        secret: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        firstPeriod: currentDate,
                        lastPeriod: currentDate,
                        nextSendTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a StackOverFlowApiClient', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
