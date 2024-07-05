import { NgModule } from '@angular/core';

import {
  OrderItemsExtendedService,
  OrderItemsDetailsExtendedComponent,
  OrderItemsListExtendedComponent,
  OrderItemsNewExtendedComponent,
} from './';
import { OrderItemsService } from 'src/app/entities/order-items';
import { OrderItemsModule } from 'src/app/entities/order-items/order-items.module';
import { orderItemsRoute } from './order-items.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [OrderItemsDetailsExtendedComponent, OrderItemsListExtendedComponent, OrderItemsNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [orderItemsRoute, OrderItemsModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: OrderItemsService, useClass: OrderItemsExtendedService }],
})
export class OrderItemsExtendedModule {}
