import { Component, OnInit, Inject } from '@angular/core';
import { OrderItemsExtendedService } from '../order-items.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { OrdersExtendedService } from 'src/app/extended/entities/orders/orders.service';
import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { OrderItemsNewComponent } from 'src/app/entities/order-items/index';

@Component({
  selector: 'app-order-items-new',
  templateUrl: './order-items-new.component.html',
  styleUrls: ['./order-items-new.component.scss'],
})
export class OrderItemsNewExtendedComponent extends OrderItemsNewComponent implements OnInit {
  title: string = 'New OrderItems';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<OrderItemsNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public pickerDialogService: PickerDialogService,
    public orderItemsExtendedService: OrderItemsExtendedService,
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
      dialogRef,
      data,
      pickerDialogService,
      orderItemsExtendedService,
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
