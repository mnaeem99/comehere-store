import { NgModule } from '@angular/core';

import { OrderItemsDetailsComponent, OrderItemsListComponent, OrderItemsNewComponent } from './';
import { orderItemsRoute } from './order-items.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [OrderItemsDetailsComponent, OrderItemsListComponent, OrderItemsNewComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [orderItemsRoute, SharedModule, GeneralComponentsModule],
})
export class OrderItemsModule {}
