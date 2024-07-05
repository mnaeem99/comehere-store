import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { OrdersExtendedService, OrdersDetailsExtendedComponent, OrdersListExtendedComponent } from '../';
import { IOrders } from 'src/app/entities/orders';
describe('OrdersDetailsExtendedComponent', () => {
  let component: OrdersDetailsExtendedComponent;
  let fixture: ComponentFixture<OrdersDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [OrdersDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [OrdersExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrdersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          OrdersDetailsExtendedComponent,
          OrdersListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'orders', component: OrdersDetailsExtendedComponent },
            { path: 'orders/:id', component: OrdersListExtendedComponent },
          ]),
        ],
        providers: [OrdersExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrdersDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
