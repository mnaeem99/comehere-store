import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { InventoryDetailsComponent, InventoryListComponent, InventoryNewComponent } from './';

const routes: Routes = [
  // { path: '', component: InventoryListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: ':id', component: InventoryDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
  // { path: 'new', component: InventoryNewComponent, canActivate: [ AuthGuard ] },
];

export const inventoryRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);
