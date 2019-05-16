import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISw } from 'app/shared/model/sw.model';
import { SwService } from './sw.service';

@Component({
  selector: 'jhi-sw-delete-dialog',
  templateUrl: './sw-delete-dialog.component.html'
})
export class SwDeleteDialogComponent {
  sw: ISw;

  constructor(protected swService: SwService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.swService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'swListModification',
        content: 'Deleted an sw'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sw-delete-popup',
  template: ''
})
export class SwDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sw }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SwDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sw = sw;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sw', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sw', { outlets: { popup: null } }]);
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
