import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Zhaopin } from 'app/shared/model/zhaopin.model';
import { ZhaopinService } from './zhaopin.service';
import { ZhaopinComponent } from './zhaopin.component';
import { ZhaopinDetailComponent } from './zhaopin-detail.component';
import { ZhaopinUpdateComponent } from './zhaopin-update.component';
import { ZhaopinDeletePopupComponent } from './zhaopin-delete-dialog.component';
import { IZhaopin } from 'app/shared/model/zhaopin.model';

@Injectable({ providedIn: 'root' })
export class ZhaopinResolve implements Resolve<IZhaopin> {
  constructor(private service: ZhaopinService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IZhaopin> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Zhaopin>) => response.ok),
        map((zhaopin: HttpResponse<Zhaopin>) => zhaopin.body)
      );
    }
    return of(new Zhaopin());
  }
}

export const zhaopinRoute: Routes = [
  {
    path: '',
    component: ZhaopinComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zpApp.zhaopin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ZhaopinDetailComponent,
    resolve: {
      zhaopin: ZhaopinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.zhaopin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ZhaopinUpdateComponent,
    resolve: {
      zhaopin: ZhaopinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.zhaopin.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ZhaopinUpdateComponent,
    resolve: {
      zhaopin: ZhaopinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.zhaopin.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const zhaopinPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ZhaopinDeletePopupComponent,
    resolve: {
      zhaopin: ZhaopinResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zpApp.zhaopin.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
