import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sw } from 'app/shared/model/sw.model';
import { SwService } from './sw.service';
import { SwComponent } from './sw.component';
import { SwDetailComponent } from './sw-detail.component';
import { SwUpdateComponent } from './sw-update.component';
import { SwDeletePopupComponent } from './sw-delete-dialog.component';
import { ISw } from 'app/shared/model/sw.model';

@Injectable({ providedIn: 'root' })
export class SwResolve implements Resolve<ISw> {
  constructor(private service: SwService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISw> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sw>) => response.ok),
        map((sw: HttpResponse<Sw>) => sw.body)
      );
    }
    return of(new Sw());
  }
}

export const swRoute: Routes = [
  {
    path: '',
    component: SwComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zpApp.sw.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SwDetailComponent,
    resolve: {
      sw: SwResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.sw.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SwUpdateComponent,
    resolve: {
      sw: SwResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.sw.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SwUpdateComponent,
    resolve: {
      sw: SwResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.sw.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const swPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SwDeletePopupComponent,
    resolve: {
      sw: SwResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.sw.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
