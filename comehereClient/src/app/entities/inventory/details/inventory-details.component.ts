import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { InventoryService } from '../inventory.service';
import { IInventory } from '../iinventory';
import { BaseDetailsComponent, FieldType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { ProductsService } from 'src/app/entities/products/products.service';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';

@Component({
  selector: 'app-inventory-details',
  templateUrl: './inventory-details.component.html',
  styleUrls: ['./inventory-details.component.scss'],
})
export class InventoryDetailsComponent extends BaseDetailsComponent<IInventory> implements OnInit {
  title = 'Inventory';
  parentUrl = 'inventory';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public inventoryService: InventoryService,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public productsService: ProductsService,
    public suppliersService: SuppliersService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(formBuilder, router, route, dialog, pickerDialogService, inventoryService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Inventory';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.getItem();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      inventoryId: [{ value: '', disabled: true }, Validators.required],
      lastRestocked: [''],
      lastRestockedTime: [''],
      quantity: ['', Validators.required],
      productId: ['', Validators.required],
      productsDescriptiveField: ['', Validators.required],
      supplierId: ['', Validators.required],
      suppliersDescriptiveField: ['', Validators.required],
    });

    this.fields = [
      {
        name: 'lastRestocked',
        label: 'last Restocked',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Date,
      },
      {
        name: 'lastRestockedTime',
        label: 'last Restocked Time',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Time,
      },
      {
        name: 'quantity',
        label: 'quantity',
        isRequired: true,
        isAutoGenerated: false,
        type: FieldType.Number,
      },
    ];
  }

  onItemFetched(item: IInventory) {
    this.item = item;
    this.itemForm.get('lastRestocked')?.setValue(item.lastRestocked ? new Date(item.lastRestocked) : null);
    this.itemForm.get('lastRestockedTime')?.setValue(this.inventoryService.formatDateStringToAMPM(item.lastRestocked));
    this.itemForm.patchValue(item);
  }

  setAssociations() {
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
        table: 'products',
        type: 'ManyToOne',
        label: 'products',
        service: this.productsService,
        descriptiveField: 'productsDescriptiveField',
        referencedDescriptiveField: 'productId',
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
        table: 'suppliers',
        type: 'ManyToOne',
        label: 'suppliers',
        service: this.suppliersService,
        descriptiveField: 'suppliersDescriptiveField',
        referencedDescriptiveField: 'supplierId',
      },
    ];

    this.childAssociations = this.associations.filter((association) => {
      return association.isParent;
    });

    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let inventory = this.itemForm.getRawValue();

    inventory.lastRestocked = this.dataService.combineDateAndTime(inventory.lastRestocked, inventory.lastRestockedTime);
    delete inventory.lastRestockedTime;

    super.onSubmit(inventory);
  }
}