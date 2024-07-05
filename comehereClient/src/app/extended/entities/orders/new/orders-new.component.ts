import { Component, OnInit, Inject } from '@angular/core';
import { OrdersExtendedService } from '../orders.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { CustomersExtendedService } from 'src/app/extended/entities/customers/customers.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { OrdersNewComponent } from 'src/app/entities/orders/index';

@Component({
  selector: 'app-orders-new',
  templateUrl: './orders-new.component.html',
  styleUrls: ['./orders-new.component.scss'],
})
export class OrdersNewExtendedComponent extends OrdersNewComponent implements OnInit {
  title: string = 'New Orders';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<OrdersNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public pickerDialogService: PickerDialogService,
    public ordersExtendedService: OrdersExtendedService,
    public errorService: ErrorService,
    public customersExtendedService: CustomersExtendedService,
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
      ordersExtendedService,
      errorService,
      customersExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
