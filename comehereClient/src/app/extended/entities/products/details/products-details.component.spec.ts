import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { ProductsExtendedService, ProductsDetailsExtendedComponent, ProductsListExtendedComponent } from '../';
import { IProducts } from 'src/app/entities/products';
describe('ProductsDetailsExtendedComponent', () => {
  let component: ProductsDetailsExtendedComponent;
  let fixture: ComponentFixture<ProductsDetailsExtendedComponent>;
  let el: HTMLElement;

  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ProductsDetailsExtendedComponent, DetailsComponent],
        imports: [TestingModule],
        providers: [ProductsExtendedService],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProductsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ProductsDetailsExtendedComponent,
          ProductsListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'products', component: ProductsDetailsExtendedComponent },
            { path: 'products/:id', component: ProductsListExtendedComponent },
          ]),
        ],
        providers: [ProductsExtendedService],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProductsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
