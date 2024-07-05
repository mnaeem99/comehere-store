import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { OrderItemsExtendedService, OrderItemsDetailsExtendedComponent, OrderItemsListExtendedComponent } from '../';
import { IOrderItems } from 'src/app/entities/order-items';
describe('OrderItemsDetailsExtendedComponent', () => {
  let component: OrderItemsDetailsExtendedComponent;
  let fixture: ComponentFixture<OrderItemsDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [OrderItemsDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [OrderItemsExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrderItemsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          OrderItemsDetailsExtendedComponent,
          OrderItemsListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'orderitems', component: OrderItemsDetailsExtendedComponent },
            { path: 'orderitems/:id', component: OrderItemsListExtendedComponent },
          ]),
        ],
        providers: [OrderItemsExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(OrderItemsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
