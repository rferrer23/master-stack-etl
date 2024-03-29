import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExtra } from 'app/shared/model/extra.model';

type EntityResponseType = HttpResponse<IExtra>;
type EntityArrayResponseType = HttpResponse<IExtra[]>;

@Injectable({ providedIn: 'root' })
export class ExtraService {
    private resourceUrl = SERVER_API_URL + 'api/extras';

    constructor(private http: HttpClient) {}

    create(extra: IExtra): Observable<EntityResponseType> {
        return this.http.post<IExtra>(this.resourceUrl, extra, { observe: 'response' });
    }

    update(extra: IExtra): Observable<EntityResponseType> {
        return this.http.put<IExtra>(this.resourceUrl, extra, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExtra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExtra[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
