import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { InventoryExtendedService } from '../inventory.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { SuppliersExtendedService } from 'src/app/extended/entities/suppliers/suppliers.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { InventoryDetailsComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-details',
  templateUrl: './inventory-details.component.html',
  styleUrls: ['./inventory-details.component.scss'],
})
export class InventoryDetailsExtendedComponent extends InventoryDetailsComponent implements OnInit {
  title: string = 'Inventory';
  parentUrl: string = 'inventory';
  //roles: IRole[];
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public inventoryExtendedService: InventoryExtendedService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public productsExtendedService: ProductsExtendedService,
    public suppliersExtendedService: SuppliersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      formBuilder,
      router,
      route,
      dialog,
      inventoryExtendedService,
      pickerDialogService,
      errorService,
      productsExtendedService,
      suppliersExtendedService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }
}
