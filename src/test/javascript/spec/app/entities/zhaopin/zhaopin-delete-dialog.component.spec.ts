/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZpTestModule } from '../../../test.module';
import { ZhaopinDeleteDialogComponent } from 'app/entities/zhaopin/zhaopin-delete-dialog.component';
import { ZhaopinService } from 'app/entities/zhaopin/zhaopin.service';

describe('Component Tests', () => {
  describe('Zhaopin Management Delete Component', () => {
    let comp: ZhaopinDeleteDialogComponent;
    let fixture: ComponentFixture<ZhaopinDeleteDialogComponent>;
    let service: ZhaopinService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [ZhaopinDeleteDialogComponent]
      })
        .overrideTemplate(ZhaopinDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZhaopinDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZhaopinService);
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
