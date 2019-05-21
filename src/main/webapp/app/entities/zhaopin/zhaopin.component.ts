import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IZhaopin, Zhaopin } from 'app/shared/model/zhaopin.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ZhaopinService } from './zhaopin.service';

@Component({
  selector: 'jhi-zhaopin',
  templateUrl: './zhaopin.component.html'
})
export class ZhaopinComponent implements OnInit, OnDestroy {
  currentAccount: any;
  zhaopins: IZhaopin[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  words: any;

  constructor(
    protected zhaopinService: ZhaopinService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.zhaopinService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        words: this.words
      })
      .subscribe(
        (res: HttpResponse<IZhaopin[]>) => this.paginateZhaopins(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  refresh() {
    this.zhaopinService
      .refresh()
      .pipe(
        filter((response: HttpResponse<any>) => response.ok),
        map((res: HttpResponse<any>) => res.body)
      )
      .subscribe(res => {
        console.log('refresh result:' + JSON.stringify(res));
      });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/zhaopin'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/zhaopin',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInZhaopins();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IZhaopin) {
    return item.id;
  }

  registerChangeInZhaopins() {
    this.eventSubscriber = this.eventManager.subscribe('zhaopinListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateZhaopins(data: IZhaopin[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.zhaopins = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
