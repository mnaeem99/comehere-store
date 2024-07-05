import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { InventoryExtendedService } from '../inventory.service';
import { InventoryNewExtendedComponent } from '../new/inventory-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { SuppliersExtendedService } from 'src/app/extended/entities/suppliers/suppliers.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { InventoryListComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-list',
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.scss'],
})
export class InventoryListExtendedComponent extends InventoryListComponent implements OnInit {
  title: string = 'Inventory';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public inventoryService: InventoryExtendedService,
    public errorService: ErrorService,
    public productsService: ProductsExtendedService,
    public suppliersService: SuppliersExtendedService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(
      router,
      route,
      global,
      dialog,
      changeDetectorRefs,
      pickerDialogService,
      inventoryService,
      errorService,
      productsService,
      suppliersService,
      globalPermissionService
    );
  }

  ngOnInit() {
    super.ngOnInit();
  }

  addNew() {
    super.addNew(InventoryNewExtendedComponent);
  }
}
