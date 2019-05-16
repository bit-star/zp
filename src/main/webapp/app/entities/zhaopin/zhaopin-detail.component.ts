import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZhaopin } from 'app/shared/model/zhaopin.model';

@Component({
  selector: 'jhi-zhaopin-detail',
  templateUrl: './zhaopin-detail.component.html'
})
export class ZhaopinDetailComponent implements OnInit {
  zhaopin: IZhaopin;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zhaopin }) => {
      this.zhaopin = zhaopin;
    });
  }

  previousState() {
    window.history.back();
  }
}
