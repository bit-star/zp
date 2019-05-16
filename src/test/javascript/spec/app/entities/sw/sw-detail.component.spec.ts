/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZpTestModule } from '../../../test.module';
import { SwDetailComponent } from 'app/entities/sw/sw-detail.component';
import { Sw } from 'app/shared/model/sw.model';

describe('Component Tests', () => {
  describe('Sw Management Detail Component', () => {
    let comp: SwDetailComponent;
    let fixture: ComponentFixture<SwDetailComponent>;
    const route = ({ data: of({ sw: new Sw(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZpTestModule],
        declarations: [SwDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SwDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SwDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sw).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
