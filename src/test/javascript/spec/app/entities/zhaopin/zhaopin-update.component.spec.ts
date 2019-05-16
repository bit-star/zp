/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZpTestModule } from '../../../test.module';
import { ZhaopinUpdateComponent } from 'app/entities/zhaopin/zhaopin-update.component';
import { ZhaopinService } from 'app/entities/zhaopin/zhaopin.service';
import { Zhaopin } from 'app/shared/model/zhaopin.model';

describe('Component Tests', () => {
  describe('Zhaopin Management Update Component', () => {
    let comp: ZhaopinUpdateComponent;
    let fixture: ComponentFixture<ZhaopinUpdateComponent>;
    let service: ZhaopinService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [ZhaopinUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ZhaopinUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZhaopinUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZhaopinService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Zhaopin(123);
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
        const entity = new Zhaopin();
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
