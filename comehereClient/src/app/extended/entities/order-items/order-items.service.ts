import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrderItemsService } from 'src/app/entities/order-items/order-items.service';
@Injectable({
  providedIn: 'root',
})
export class OrderItemsExtendedService extends OrderItemsService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
