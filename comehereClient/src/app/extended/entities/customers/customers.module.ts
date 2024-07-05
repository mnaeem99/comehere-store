import { NgModule } from '@angular/core';

import {
  CustomersExtendedService,
  CustomersDetailsExtendedComponent,
  CustomersListExtendedComponent,
  CustomersNewExtendedComponent,
} from './';
import { CustomersService } from 'src/app/entities/customers';
import { CustomersModule } from 'src/app/entities/customers/customers.module';
import { customersRoute } from './customers.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [CustomersDetailsExtendedComponent, CustomersListExtendedComponent, CustomersNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [customersRoute, CustomersModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: CustomersService, useClass: CustomersExtendedService }],
})
export class CustomersExtendedModule {}
