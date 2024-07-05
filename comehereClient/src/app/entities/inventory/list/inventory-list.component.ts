import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IInventory } from '../iinventory';
import { InventoryService } from '../inventory.service';
import { Router, ActivatedRoute } from '@angular/router';
import { InventoryNewComponent } from '../new/inventory-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { ProductsService } from 'src/app/entities/products/products.service';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';

@Component({
  selector: 'app-inventory-list',
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.scss'],
})
export class InventoryListComponent extends BaseListComponent<IInventory> implements OnInit {
  title = 'Inventory';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public inventoryService: InventoryService,
    public errorService: ErrorService,
    public productsService: ProductsService,
    public suppliersService: SuppliersService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, inventoryService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Inventory';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['inventoryId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'productId',
            value: undefined,
            referencedkey: 'productId',
          },
        ],
        isParent: false,
        descriptiveField: 'productsDescriptiveField',
        referencedDescriptiveField: 'productId',
        service: this.productsService,
        associatedObj: undefined,
        table: 'products',
        type: 'ManyToOne',
        url: 'inventorys',
        listColumn: 'products',
        label: 'products',
      },
      {
        column: [
          {
            key: 'supplierId',
            value: undefined,
            referencedkey: 'supplierId',
          },
        ],
        isParent: false,
        descriptiveField: 'suppliersDescriptiveField',
        referencedDescriptiveField: 'supplierId',
        service: this.suppliersService,
        associatedObj: undefined,
        table: 'suppliers',
        type: 'ManyToOne',
        url: 'inventorys',
        listColumn: 'suppliers',
        label: 'suppliers',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'inventoryId',
        searchColumn: 'inventoryId',
        label: 'inventory Id',
        sort: true,
        filter: true,
        type: ListColumnType.Number,
      },
      {
        column: 'lastRestocked',
        searchColumn: 'lastRestocked',
        label: 'last Restocked',
        sort: true,
        filter: true,
        type: ListColumnType.DateTime,
      },
      {
        column: 'quantity',
        searchColumn: 'quantity',
        label: 'quantity',
        sort: true,
        filter: true,
        type: ListColumnType.Number,
      },
      {
        column: 'productsDescriptiveField',
        searchColumn: 'products',
        label: 'products',
        sort: true,
        filter: true,
        type: ListColumnType.String,
      },
      {
        column: 'suppliersDescriptiveField',
        searchColumn: 'suppliers',
        label: 'suppliers',
        sort: true,
        filter: true,
        type: ListColumnType.String,
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
      comp = InventoryNewComponent;
    }
    super.addNew(comp);
  }
}
