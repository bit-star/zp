import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ZpSharedModule } from 'app/shared';
import {
  ZhaopinComponent,
  ZhaopinDetailComponent,
  ZhaopinUpdateComponent,
  ZhaopinDeletePopupComponent,
  ZhaopinDeleteDialogComponent,
  zhaopinRoute,
  zhaopinPopupRoute
} from './';

const ENTITY_STATES = [...zhaopinRoute, ...zhaopinPopupRoute];

@NgModule({
  imports: [ZpSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ZhaopinComponent,
    ZhaopinDetailComponent,
    ZhaopinUpdateComponent,
    ZhaopinDeleteDialogComponent,
    ZhaopinDeletePopupComponent
  ],
  entryComponents: [ZhaopinComponent, ZhaopinUpdateComponent, ZhaopinDeleteDialogComponent, ZhaopinDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZpZhaopinModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
