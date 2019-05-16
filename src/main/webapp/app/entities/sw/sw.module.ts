import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ZpSharedModule } from 'app/shared';
import {
  SwComponent,
  SwDetailComponent,
  SwUpdateComponent,
  SwDeletePopupComponent,
  SwDeleteDialogComponent,
  swRoute,
  swPopupRoute
} from './';

const ENTITY_STATES = [...swRoute, ...swPopupRoute];

@NgModule({
  imports: [ZpSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SwComponent, SwDetailComponent, SwUpdateComponent, SwDeleteDialogComponent, SwDeletePopupComponent],
  entryComponents: [SwComponent, SwUpdateComponent, SwDeleteDialogComponent, SwDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZpSwModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
