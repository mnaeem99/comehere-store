import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  OrderItemsExtendedService,
  OrderItemsDetailsExtendedComponent,
  OrderItemsListExtendedComponent,
  OrderItemsNewExtendedComponent,
} from '../';
import { IOrderItems } from 'src/app/entities/order-items';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('OrderItemsListExtendedComponent', () => {
  let fixture: ComponentFixture<OrderItemsListExtendedComponent>;
  let component: OrderItemsListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [OrderItemsListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [OrderItemsExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrderItemsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          OrderItemsListExtendedComponent,
          OrderItemsNewExtendedComponent,
          NewComponent,
          OrderItemsDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'orderitems', component: OrderItemsListExtendedComponent },
            { path: 'orderitems/:id', component: OrderItemsDetailsExtendedComponent },
          ]),
        ],
        providers: [OrderItemsExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrderItemsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
