import { NgModule } from '@angular/core';

import {
  SuppliersExtendedService,
  SuppliersDetailsExtendedComponent,
  SuppliersListExtendedComponent,
  SuppliersNewExtendedComponent,
} from './';
import { SuppliersService } from 'src/app/entities/suppliers';
import { SuppliersModule } from 'src/app/entities/suppliers/suppliers.module';
import { suppliersRoute } from './suppliers.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [SuppliersDetailsExtendedComponent, SuppliersListExtendedComponent, SuppliersNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [suppliersRoute, SuppliersModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: SuppliersService, useClass: SuppliersExtendedService }],
})
export class SuppliersExtendedModule {}
