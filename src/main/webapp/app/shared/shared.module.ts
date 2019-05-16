import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ZpSharedLibsModule, ZpSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ZpSharedLibsModule, ZpSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ZpSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZpSharedModule {
  static forRoot() {
    return {
      ngModule: ZpSharedModule
    };
  }
}
