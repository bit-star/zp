import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZhaopin } from 'app/shared/model/zhaopin.model';
import { ZhaopinService } from './zhaopin.service';

@Component({
  selector: 'jhi-zhaopin-delete-dialog',
  templateUrl: './zhaopin-delete-dialog.component.html'
})
export class ZhaopinDeleteDialogComponent {
  zhaopin: IZhaopin;

  constructor(protected zhaopinService: ZhaopinService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.zhaopinService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'zhaopinListModification',
        content: 'Deleted an zhaopin'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-zhaopin-delete-popup',
  template: ''
})
export class ZhaopinDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zhaopin }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ZhaopinDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.zhaopin = zhaopin;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/zhaopin', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/zhaopin', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
