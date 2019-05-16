/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZpTestModule } from '../../../test.module';
import { SwDeleteDialogComponent } from 'app/entities/sw/sw-delete-dialog.component';
import { SwService } from 'app/entities/sw/sw.service';

describe('Component Tests', () => {
  describe('Sw Management Delete Component', () => {
    let comp: SwDeleteDialogComponent;
    let fixture: ComponentFixture<SwDeleteDialogComponent>;
    let service: SwService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [SwDeleteDialogComponent]
      })
        .overrideTemplate(SwDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SwDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SwService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
