import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { ProductsDetailsExtendedComponent, ProductsListExtendedComponent, ProductsNewExtendedComponent } from './';

const routes: Routes = [
  { path: '', component: ProductsListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [AuthGuard] },
  {
    path: ':id',
    component: ProductsDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: ProductsNewExtendedComponent, canActivate: [AuthGuard] },
];

export const productsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
