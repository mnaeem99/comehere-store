import { NgModule } from '@angular/core';

import {
  ProductsExtendedService,
  ProductsDetailsExtendedComponent,
  ProductsListExtendedComponent,
  ProductsNewExtendedComponent,
} from './';
import { ProductsService } from 'src/app/entities/products';
import { ProductsModule } from 'src/app/entities/products/products.module';
import { productsRoute } from './products.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [ProductsDetailsExtendedComponent, ProductsListExtendedComponent, ProductsNewExtendedComponent];
@NgModule({
  declarations: entities,
  exports: entities,
  imports: [productsRoute, ProductsModule, SharedModule, GeneralComponentsExtendedModule],
  providers: [{ provide: ProductsService, useClass: ProductsExtendedService }],
})
export class ProductsExtendedModule {}
