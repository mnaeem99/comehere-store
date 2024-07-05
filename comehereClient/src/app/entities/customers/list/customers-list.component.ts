import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ICustomers } from '../icustomers';
import { CustomersService } from '../customers.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CustomersNewComponent } from '../new/customers-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

@Component({
  selector: 'app-customers-list',
  templateUrl: './customers-list.component.html',
  styleUrls: ['./customers-list.component.scss'],
})
export class CustomersListComponent extends BaseListComponent<ICustomers> implements OnInit {
  title = 'Customers';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public customersService: CustomersService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, customersService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Customers';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['customerId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [];
  }

  setColumns() {
    this.columns = [
      {
        column: 'customerId',
        searchColumn: 'customerId',
        label: 'customer Id',
        sort: true,
        filter: true,
        type: ListColumnType.Number,
      },
      {
        column: 'email',
        searchColumn: 'email',
        label: 'email',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'firstName',
        searchColumn: 'firstName',
        label: 'first Name',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'lastName',
        searchColumn: 'lastName',
        label: 'last Name',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'address',
        searchColumn: 'address',
        label: 'address',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'phone',
        searchColumn: 'phone',
        label: 'phone',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'createdAt',
        searchColumn: 'createdAt',
        label: 'created At',
        sort: true,
        filter: true,
        type: ListColumnType.DateTime,
      },
      {
        column: 'updatedAt',
        searchColumn: 'updatedAt',
        label: 'updated At',
        sort: true,
        filter: true,
        type: ListColumnType.DateTime,
      },
      {
        column: 'actions',
        label: 'Actions',
        sort: false,
        filter: false,
        type: ListColumnType.String,
      },
    ];
    this.selectedColumns = this.columns;
    this.displayedColumns = this.columns.map((obj) => {
      return obj.column;
    });
  }
  addNew(comp: any) {
    if (!comp) {
      comp = CustomersNewComponent;
    }
    super.addNew(comp);
  }
}
