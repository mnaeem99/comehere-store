import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { OrderItemsExtendedService } from '../order-items.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { OrdersExtendedService } from 'src/app/extended/entities/orders/orders.service';
import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { OrderItemsDetailsComponent } from 'src/app/entities/order-items/index';

@Component({
  selector: 'app-order-items-details',
  templateUrl: './order-items-details.component.html',
  styleUrls: ['./order-items-details.component.scss'],
})
export class OrderItemsDetailsExtendedComponent extends OrderItemsDetailsComponent implements OnInit {
  title: string = 'OrderItems';
  parentUrl: string = 'orderitems';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public orderItemsExtendedService: OrderItemsExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public ordersExtendedService: OrdersExtendedService,
    public productsExtendedService: ProductsExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      orderItemsExtendedService,
      pickerDialogService,
      errorService,
      ordersExtendedService,
      productsExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
