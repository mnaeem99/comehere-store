import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { OrderItemsExtendedService } from '../order-items.service';
import { OrderItemsNewExtendedComponent } from '../new/order-items-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { OrdersExtendedService } from 'src/app/extended/entities/orders/orders.service';
import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { OrderItemsListComponent } from 'src/app/entities/order-items/index';

@Component({
  selector: 'app-order-items-list',
  templateUrl: './order-items-list.component.html',
  styleUrls: ['./order-items-list.component.scss'],
})
export class OrderItemsListExtendedComponent extends OrderItemsListComponent implements OnInit {
  title: string = 'OrderItems';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public orderItemsService: OrderItemsExtendedService,
    public errorService: ErrorService,
    public ordersService: OrdersExtendedService,
    public productsService: ProductsExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      orderItemsService,
      errorService,
      ordersService,
      productsService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(OrderItemsNewExtendedComponent);
  }
}
