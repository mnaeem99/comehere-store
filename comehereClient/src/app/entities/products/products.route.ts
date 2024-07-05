import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { ProductsDetailsComponent, ProductsListComponent, ProductsNewComponent } from './';

const routes: Routes = [
  // { path: '', component: ProductsListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: ProductsDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: ProductsNewComponent, canActivate: [ AuthGuard ] },
];

export const productsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
