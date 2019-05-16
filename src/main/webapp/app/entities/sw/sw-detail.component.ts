import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISw } from 'app/shared/model/sw.model';

@Component({
  selector: 'jhi-sw-detail',
  templateUrl: './sw-detail.component.html'
})
export class SwDetailComponent implements OnInit {
  sw: ISw;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sw }) => {
      this.sw = sw;
    });
  }

  previousState() {
    window.history.back();
  }
}
