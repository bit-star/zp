import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISw, Sw } from 'app/shared/model/sw.model';
import { SwService } from './sw.service';

@Component({
  selector: 'jhi-sw-update',
  templateUrl: './sw-update.component.html'
})
export class SwUpdateComponent implements OnInit {
  sw: ISw;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    text: []
  });

  constructor(protected swService: SwService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sw }) => {
      this.updateForm(sw);
      this.sw = sw;
    });
  }

  updateForm(sw: ISw) {
    this.editForm.patchValue({
      id: sw.id,
      text: sw.text
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sw = this.createFromForm();
    if (sw.id !== undefined) {
      this.subscribeToSaveResponse(this.swService.update(sw));
    } else {
      this.subscribeToSaveResponse(this.swService.create(sw));
    }
  }

  private createFromForm(): ISw {
    const entity = {
      ...new Sw(),
      id: this.editForm.get(['id']).value,
      text: this.editForm.get(['text']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISw>>) {
    result.subscribe((res: HttpResponse<ISw>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
