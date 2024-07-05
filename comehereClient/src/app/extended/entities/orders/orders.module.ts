import { NgModule } from '@angular/core';

import {
  OrdersExtendedService,
  OrdersDetailsExtendedComponent,
  OrdersListExtendedComponent,
  OrdersNewExtendedComponent,
} from './';
import { OrdersService } from 'src/app/entities/orders';
import { OrdersModule } from 'src/app/entities/orders/orders.module';
import { ordersRoute } from './orders.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [OrdersDetailsExtendedComponent, OrdersListExtendedComponent, OrdersNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [ordersRoute, OrdersModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: OrdersService, useClass: OrdersExtendedService }],
})
export class OrdersExtendedModule {}
