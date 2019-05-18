import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IZhaopin } from 'app/shared/model/zhaopin.model';
import { ZhaopinService } from 'app/entities/zhaopin/zhaopin.service';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { element } from '@angular/core/src/render3';

@Component({
  selector: 'jhi-zhaopin-detail',
  templateUrl: './zhaopin-detail.component.html'
})
export class ZhaopinDetailComponent implements OnInit {
  zhaopin: IZhaopin;
  zhaopins: IZhaopin[];

  constructor(
    protected zhaopinService: ZhaopinService,
    protected jhiAlertService: JhiAlertService,
    protected router: Router,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zhaopin }) => {
      this.zhaopin = zhaopin;
      this.loadAll();
    });
  }

  previousState() {
    window.history.back();
  }

  loadAll() {
    this.zhaopinService
      .query({
        cluster: this.zhaopin.cluster
      })
      .subscribe(
        (res: HttpResponse<IZhaopin[]>) => this.handleZhaopins(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }
  n;

  protected handleZhaopins(data: IZhaopin[], headers: HttpHeaders) {
    let iZhaopins = data.filter(item => item.id != this.zhaopin.id);
    this.zhaopins = iZhaopins;
  }

  trackId(index: number, item: IZhaopin) {
    return item.id;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
