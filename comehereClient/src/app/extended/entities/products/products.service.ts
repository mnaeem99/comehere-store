import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductsService } from 'src/app/entities/products/products.service';
@Injectable({
  providedIn: 'root',
})
export class ProductsExtendedService extends ProductsService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
