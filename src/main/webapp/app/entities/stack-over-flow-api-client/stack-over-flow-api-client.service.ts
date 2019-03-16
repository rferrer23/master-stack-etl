import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStackOverFlowApiClient } from 'app/shared/model/stack-over-flow-api-client.model';

type EntityResponseType = HttpResponse<IStackOverFlowApiClient>;
type EntityArrayResponseType = HttpResponse<IStackOverFlowApiClient[]>;

@Injectable({ providedIn: 'root' })
export class StackOverFlowApiClientService {
    private resourceUrl = SERVER_API_URL + 'api/stack-over-flow-api-clients';

    constructor(private http: HttpClient) {}

    create(stackOverFlowApiClient: IStackOverFlowApiClient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stackOverFlowApiClient);
        return this.http
            .post<IStackOverFlowApiClient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stackOverFlowApiClient: IStackOverFlowApiClient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stackOverFlowApiClient);
        return this.http
            .put<IStackOverFlowApiClient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStackOverFlowApiClient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStackOverFlowApiClient[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(stackOverFlowApiClient: IStackOverFlowApiClient): IStackOverFlowApiClient {
        const copy: IStackOverFlowApiClient = Object.assign({}, stackOverFlowApiClient, {
            firstPeriod:
                stackOverFlowApiClient.firstPeriod != null && stackOverFlowApiClient.firstPeriod.isValid()
                    ? stackOverFlowApiClient.firstPeriod.format(DATE_FORMAT)
                    : null,
            lastPeriod:
                stackOverFlowApiClient.lastPeriod != null && stackOverFlowApiClient.lastPeriod.isValid()
                    ? stackOverFlowApiClient.lastPeriod.format(DATE_FORMAT)
                    : null,
            nextSendTime:
                stackOverFlowApiClient.nextSendTime != null && stackOverFlowApiClient.nextSendTime.isValid()
                    ? stackOverFlowApiClient.nextSendTime.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.firstPeriod = res.body.firstPeriod != null ? moment(res.body.firstPeriod) : null;
        res.body.lastPeriod = res.body.lastPeriod != null ? moment(res.body.lastPeriod) : null;
        res.body.nextSendTime = res.body.nextSendTime != null ? moment(res.body.nextSendTime) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((stackOverFlowApiClient: IStackOverFlowApiClient) => {
            stackOverFlowApiClient.firstPeriod =
                stackOverFlowApiClient.firstPeriod != null ? moment(stackOverFlowApiClient.firstPeriod) : null;
            stackOverFlowApiClient.lastPeriod =
                stackOverFlowApiClient.lastPeriod != null ? moment(stackOverFlowApiClient.lastPeriod) : null;
            stackOverFlowApiClient.nextSendTime =
                stackOverFlowApiClient.nextSendTime != null ? moment(stackOverFlowApiClient.nextSendTime) : null;
        });
        return res;
    }
}
