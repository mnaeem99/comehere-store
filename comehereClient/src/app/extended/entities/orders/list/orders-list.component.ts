import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { OrdersExtendedService } from '../orders.service';
import { OrdersNewExtendedComponent } from '../new/orders-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { CustomersExtendedService } from 'src/app/extended/entities/customers/customers.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { OrdersListComponent } from 'src/app/entities/orders/index';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.scss'],
})
export class OrdersListExtendedComponent extends OrdersListComponent implements OnInit {
  title: string = 'Orders';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public ordersService: OrdersExtendedService,
    public errorService: ErrorService,
    public customersService: CustomersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      ordersService,
      errorService,
      customersService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(OrdersNewExtendedComponent);
  }
}
