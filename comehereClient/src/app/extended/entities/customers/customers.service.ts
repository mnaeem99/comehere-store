import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CustomersService } from 'src/app/entities/customers/customers.service';
@Injectable({
  providedIn: 'root',
})
export class CustomersExtendedService extends CustomersService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
