import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  OrdersExtendedService,
  OrdersDetailsExtendedComponent,
  OrdersListExtendedComponent,
  OrdersNewExtendedComponent,
} from '../';
import { IOrders } from 'src/app/entities/orders';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('OrdersListExtendedComponent', () => {
  let fixture: ComponentFixture<OrdersListExtendedComponent>;
  let component: OrdersListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [OrdersListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [OrdersExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrdersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          OrdersListExtendedComponent,
          OrdersNewExtendedComponent,
          NewComponent,
          OrdersDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'orders', component: OrdersListExtendedComponent },
            { path: 'orders/:id', component: OrdersDetailsExtendedComponent },
          ]),
        ],
        providers: [OrdersExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrdersListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
