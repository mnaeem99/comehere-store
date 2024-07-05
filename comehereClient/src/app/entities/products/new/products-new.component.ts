import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProductsService } from '../products.service';
import { IProducts } from '../iproducts';
import { BaseNewComponent, FieldType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

@Component({
  selector: 'app-products-new',
  templateUrl: './products-new.component.html',
  styleUrls: ['./products-new.component.scss'],
})
export class ProductsNewComponent extends BaseNewComponent<IProducts> implements OnInit {
  title: string = 'New Products';
  constructor(
    public formBuilder: FormBuilder,
    public router: Router,
    public route: ActivatedRoute,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ProductsNewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public pickerDialogService: PickerDialogService,
    public productsService: ProductsService,
    public errorService: ErrorService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, productsService, errorService);
  }

  ngOnInit() {
    this.entityName = 'Products';
    this.setAssociations();
    super.ngOnInit();
    this.setForm();
    this.checkPassedData();
  }

  setForm() {
    this.itemForm = this.formBuilder.group({
      category: [''],
      createdAt: [''],
      createdAtTime: ['12:00 AM'],
      description: [''],
      name: ['', Validators.required],
      price: ['', Validators.required],
      updatedAt: [''],
      updatedAtTime: ['12:00 AM'],
    });

    this.fields = [
      {
        name: 'category',
        label: 'category',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.String,
      },
      {
        name: 'createdAt',
        label: 'created At',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Date,
      },
      {
        name: 'createdAtTime',
        label: 'created At Time',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Time,
      },
      {
        name: 'description',
        label: 'description',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.String,
      },
      {
        name: 'name',
        label: 'name',
        isRequired: true,
        isAutoGenerated: false,
        type: FieldType.String,
      },
      {
        name: 'price',
        label: 'price',
        isRequired: true,
        isAutoGenerated: false,
        type: FieldType.Number,
      },
      {
        name: 'updatedAt',
        label: 'updated At',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Date,
      },
      {
        name: 'updatedAtTime',
        label: 'updated At Time',
        isRequired: false,
        isAutoGenerated: false,
        type: FieldType.Time,
      },
    ];
  }

  setAssociations() {
    this.associations = [];
    this.parentAssociations = this.associations.filter((association) => {
      return !association.isParent;
    });
  }

  onSubmit() {
    let products = this.itemForm.getRawValue();

    products.createdAt = this.dataService.combineDateAndTime(products.createdAt, products.createdAtTime);
    delete products.createdAtTime;
    products.updatedAt = this.dataService.combineDateAndTime(products.updatedAt, products.updatedAtTime);
    delete products.updatedAtTime;

    super.onSubmit(products);
  }
}
