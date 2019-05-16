/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZpTestModule } from '../../../test.module';
import { SwUpdateComponent } from 'app/entities/sw/sw-update.component';
import { SwService } from 'app/entities/sw/sw.service';
import { Sw } from 'app/shared/model/sw.model';

describe('Component Tests', () => {
  describe('Sw Management Update Component', () => {
    let comp: SwUpdateComponent;
    let fixture: ComponentFixture<SwUpdateComponent>;
    let service: SwService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [SwUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SwUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SwUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SwService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sw(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sw();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
