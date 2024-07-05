import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { OrderItemsDetailsComponent, OrderItemsListComponent, OrderItemsNewComponent } from './';

const routes: Routes = [
  // { path: '', component: OrderItemsListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: OrderItemsDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: OrderItemsNewComponent, canActivate: [ AuthGuard ] },
];

export const orderItemsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
