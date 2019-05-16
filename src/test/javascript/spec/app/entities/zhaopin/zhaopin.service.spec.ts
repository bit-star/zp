/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ZhaopinService } from 'app/entities/zhaopin/zhaopin.service';
import { IZhaopin, Zhaopin } from 'app/shared/model/zhaopin.model';

describe('Service Tests', () => {
  describe('Zhaopin Service', () => {
    let injector: TestBed;
    let service: ZhaopinService;
    let httpMock: HttpTestingController;
    let elemDefault: IZhaopin;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ZhaopinService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Zhaopin(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, currentDate, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            ptime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Zhaopin', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ptime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            ptime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Zhaopin(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Zhaopin', async () => {
        const returnedFromService = Object.assign(
          {
            zwmc: 'BBBBBB',
            gsmc: 'BBBBBB',
            gzdd: 'BBBBBB',
            xzLow: 1,
            xzHeight: 1,
            ptime: currentDate.format(DATE_TIME_FORMAT),
            href: 'BBBBBB',
            cluster: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ptime: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Zhaopin', async () => {
        const returnedFromService = Object.assign(
          {
            zwmc: 'BBBBBB',
            gsmc: 'BBBBBB',
            gzdd: 'BBBBBB',
            xzLow: 1,
            xzHeight: 1,
            ptime: currentDate.format(DATE_TIME_FORMAT),
            href: 'BBBBBB',
            cluster: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            ptime: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Zhaopin', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
