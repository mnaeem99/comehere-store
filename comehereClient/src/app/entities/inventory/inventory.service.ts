import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IInventory } from './iinventory';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class InventoryService extends GenericApiService<IInventory> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'inventory');
  }

  convertEnumToArray(enumm: any) {
    const arrayObjects = [];
    // Retrieve key and values using Object.entries() method.
    for (const [propertyKey, propertyValue] of Object.entries(enumm)) {
      // Add values to array
      arrayObjects.push(propertyValue);
    }

    return arrayObjects;
  }
}
