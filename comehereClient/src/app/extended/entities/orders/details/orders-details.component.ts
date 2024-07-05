import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { OrdersExtendedService } from '../orders.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { CustomersExtendedService } from 'src/app/extended/entities/customers/customers.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { OrdersDetailsComponent } from 'src/app/entities/orders/index';

@Component({
  selector: 'app-orders-details',
  templateUrl: './orders-details.component.html',
  styleUrls: ['./orders-details.component.scss'],
})
export class OrdersDetailsExtendedComponent extends OrdersDetailsComponent implements OnInit {
  title: string = 'Orders';
  parentUrl: string = 'orders';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public ordersExtendedService: OrdersExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public customersExtendedService: CustomersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      ordersExtendedService,
      pickerDialogService,
      errorService,
      customersExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
