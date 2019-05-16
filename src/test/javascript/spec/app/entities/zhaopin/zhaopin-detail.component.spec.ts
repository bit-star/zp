/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZpTestModule } from '../../../test.module';
import { ZhaopinDetailComponent } from 'app/entities/zhaopin/zhaopin-detail.component';
import { Zhaopin } from 'app/shared/model/zhaopin.model';

describe('Component Tests', () => {
  describe('Zhaopin Management Detail Component', () => {
    let comp: ZhaopinDetailComponent;
    let fixture: ComponentFixture<ZhaopinDetailComponent>;
    const route = ({ data: of({ zhaopin: new Zhaopin(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [ZhaopinDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ZhaopinDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZhaopinDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.zhaopin).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
