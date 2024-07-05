import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrdersService } from 'src/app/entities/orders/orders.service';
@Injectable({
  providedIn: 'root',
})
export class OrdersExtendedService extends OrdersService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
