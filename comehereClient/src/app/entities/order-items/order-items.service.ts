import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IOrderItems } from './iorder-items';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root',
})
export class OrderItemsService extends GenericApiService<IOrderItems> {
  constructor(protected httpclient: HttpClient) {
    super(httpclient, 'orderItems');
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
