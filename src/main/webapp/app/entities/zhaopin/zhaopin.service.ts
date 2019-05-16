import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IZhaopin } from 'app/shared/model/zhaopin.model';

type EntityResponseType = HttpResponse<IZhaopin>;
type EntityArrayResponseType = HttpResponse<IZhaopin[]>;

@Injectable({ providedIn: 'root' })
export class ZhaopinService {
  public resourceUrl = SERVER_API_URL + 'api/zhaopins';

  constructor(protected http: HttpClient) {}

  create(zhaopin: IZhaopin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zhaopin);
    return this.http
      .post<IZhaopin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(zhaopin: IZhaopin): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(zhaopin);
    return this.http
      .put<IZhaopin>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IZhaopin>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IZhaopin[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(zhaopin: IZhaopin): IZhaopin {
    const copy: IZhaopin = Object.assign({}, zhaopin, {
      ptime: zhaopin.ptime != null && zhaopin.ptime.isValid() ? zhaopin.ptime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ptime = res.body.ptime != null ? moment(res.body.ptime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((zhaopin: IZhaopin) => {
        zhaopin.ptime = zhaopin.ptime != null ? moment(zhaopin.ptime) : null;
      });
    }
    return res;
  }
}
