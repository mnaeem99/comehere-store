import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IOrderItems } from '../iorder-items';
import { OrderItemsService } from '../order-items.service';
import { Router, ActivatedRoute } from '@angular/router';
import { OrderItemsNewComponent } from '../new/order-items-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { OrdersService } from 'src/app/entities/orders/orders.service';
import { ProductsService } from 'src/app/entities/products/products.service';

@Component({
  selector: 'app-order-items-list',
  templateUrl: './order-items-list.component.html',
  styleUrls: ['./order-items-list.component.scss'],
})
export class OrderItemsListComponent extends BaseListComponent<IOrderItems> implements OnInit {
  title = 'OrderItems';
  constructor(
    public router: Router,
    public route: ActivatedRoute,
    public global: Globals,
    public dialog: MatDialog,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public orderItemsService: OrderItemsService,
    public errorService: ErrorService,
    public ordersService: OrdersService,
    public productsService: ProductsService,
    public globalPermissionService: GlobalPermissionService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, orderItemsService, errorService);
  }

  ngOnInit() {
    this.entityName = 'OrderItems';
    this.setAssociation();
    this.setColumns();
    this.primaryKeys = ['orderItemId'];
    super.ngOnInit();
  }

  setAssociation() {
    this.associations = [
      {
        column: [
          {
            key: 'orderId',
            value: undefined,
            referencedkey: 'orderId',
          },
        ],
        isParent: false,
        descriptiveField: 'ordersDescriptiveField',
        referencedDescriptiveField: 'orderId',
        service: this.ordersService,
        associatedObj: undefined,
        table: 'orders',
        type: 'ManyToOne',
        url: 'orderItems',
        listColumn: 'orders',
        label: 'orders',
      },
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
        url: 'orderItems',
        listColumn: 'products',
        label: 'products',
      },
    ];
  }

  setColumns() {
    this.columns = [
      {
        column: 'orderItemId',
        searchColumn: 'orderItemId',
        label: 'order Item Id',
        sort: true,
        filter: true,
        type: ListColumnType.Number,
      },
      {
        column: 'price',
        searchColumn: 'price',
        label: 'price',
        sort: true,
        filter: true,
        type: ListColumnType.Number,
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
        column: 'ordersDescriptiveField',
        searchColumn: 'orders',
        label: 'orders',
        sort: true,
        filter: true,
        type: ListColumnType.String,
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
      comp = OrderItemsNewComponent;
    }
    super.addNew(comp);
  }
}
