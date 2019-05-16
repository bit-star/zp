import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'zhaopin',
        loadChildren: './zhaopin/zhaopin.module#ZpZhaopinModule'
      },
      {
        path: 'sw',
        loadChildren: './sw/sw.module#ZpSwModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZpEntityModule {}
