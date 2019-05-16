import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IZhaopin, Zhaopin } from 'app/shared/model/zhaopin.model';
import { ZhaopinService } from './zhaopin.service';

@Component({
  selector: 'jhi-zhaopin-update',
  templateUrl: './zhaopin-update.component.html'
})
export class ZhaopinUpdateComponent implements OnInit {
  zhaopin: IZhaopin;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    zwmc: [],
    gsmc: [],
    gzdd: [],
    xzLow: [],
    xzHeight: [],
    ptime: [],
    href: [],
    cluster: []
  });

  constructor(protected zhaopinService: ZhaopinService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zhaopin }) => {
      this.updateForm(zhaopin);
      this.zhaopin = zhaopin;
    });
  }

  updateForm(zhaopin: IZhaopin) {
    this.editForm.patchValue({
      id: zhaopin.id,
      zwmc: zhaopin.zwmc,
      gsmc: zhaopin.gsmc,
      gzdd: zhaopin.gzdd,
      xzLow: zhaopin.xzLow,
      xzHeight: zhaopin.xzHeight,
      ptime: zhaopin.ptime != null ? zhaopin.ptime.format(DATE_TIME_FORMAT) : null,
      href: zhaopin.href,
      cluster: zhaopin.cluster
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const zhaopin = this.createFromForm();
    if (zhaopin.id !== undefined) {
      this.subscribeToSaveResponse(this.zhaopinService.update(zhaopin));
    } else {
      this.subscribeToSaveResponse(this.zhaopinService.create(zhaopin));
    }
  }

  private createFromForm(): IZhaopin {
    const entity = {
      ...new Zhaopin(),
      id: this.editForm.get(['id']).value,
      zwmc: this.editForm.get(['zwmc']).value,
      gsmc: this.editForm.get(['gsmc']).value,
      gzdd: this.editForm.get(['gzdd']).value,
      xzLow: this.editForm.get(['xzLow']).value,
      xzHeight: this.editForm.get(['xzHeight']).value,
      ptime: this.editForm.get(['ptime']).value != null ? moment(this.editForm.get(['ptime']).value, DATE_TIME_FORMAT) : undefined,
      href: this.editForm.get(['href']).value,
      cluster: this.editForm.get(['cluster']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZhaopin>>) {
    result.subscribe((res: HttpResponse<IZhaopin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
