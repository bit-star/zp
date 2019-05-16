import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISw } from 'app/shared/model/sw.model';

type EntityResponseType = HttpResponse<ISw>;
type EntityArrayResponseType = HttpResponse<ISw[]>;

@Injectable({ providedIn: 'root' })
export class SwService {
  public resourceUrl = SERVER_API_URL + 'api/sws';

  constructor(protected http: HttpClient) {}

  create(sw: ISw): Observable<EntityResponseType> {
    return this.http.post<ISw>(this.resourceUrl, sw, { observe: 'response' });
  }

  update(sw: ISw): Observable<EntityResponseType> {
    return this.http.put<ISw>(this.resourceUrl, sw, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISw>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISw[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
