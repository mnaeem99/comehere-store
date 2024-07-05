import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import {
  OrderItemsDetailsExtendedComponent,
  OrderItemsListExtendedComponent,
  OrderItemsNewExtendedComponent,
} from './';

const routes: Routes = [
  {
    path: '',
    component: OrderItemsListExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  {
    path: ':id',
    component: OrderItemsDetailsExtendedComponent,
    canDeactivate: [CanDeactivateGuard],
    canActivate: [AuthGuard],
  },
  { path: 'new', component: OrderItemsNewExtendedComponent, canActivate: [AuthGuard] },
];

export const orderItemsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
