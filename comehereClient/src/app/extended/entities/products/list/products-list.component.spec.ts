import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import {
  ProductsExtendedService,
  ProductsDetailsExtendedComponent,
  ProductsListExtendedComponent,
  ProductsNewExtendedComponent,
} from '../';
import { IProducts } from 'src/app/entities/products';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('ProductsListExtendedComponent', () => {
  let fixture: ComponentFixture<ProductsListExtendedComponent>;
  let component: ProductsListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [ProductsListExtendedComponent, ListComponent],
        imports: [TestingModule],
        providers: [ProductsExtendedService, ChangeDetectorRef],
        schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProductsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });

  describe('Integration tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ProductsListExtendedComponent,
          ProductsNewExtendedComponent,
          NewComponent,
          ProductsDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp,
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'products', component: ProductsListExtendedComponent },
            { path: 'products/:id', component: ProductsDetailsExtendedComponent },
          ]),
        ],
        providers: [ProductsExtendedService, ChangeDetectorRef],
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProductsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  });
});
