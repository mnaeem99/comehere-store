import { Component, OnInit, Inject } from '@angular/core';
import { InventoryExtendedService } from '../inventory.service';

import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { SuppliersExtendedService } from 'src/app/extended/entities/suppliers/suppliers.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { InventoryNewComponent } from 'src/app/entities/inventory/index';

@Component({
  selector: 'app-inventory-new',
  templateUrl: './inventory-new.component.html',
  styleUrls: ['./inventory-new.component.scss'],
})
export class InventoryNewExtendedComponent extends InventoryNewComponent implements OnInit {
  title: string = 'New Inventory';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<InventoryNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public pickerDialogService: PickerDialogService,
    public inventoryExtendedService: InventoryExtendedService,
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
      dialogRef,
      data,
      pickerDialogService,
      inventoryExtendedService,
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
