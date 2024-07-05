import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SuppliersService } from 'src/app/entities/suppliers/suppliers.service';
@Injectable({
  providedIn: 'root',
})
export class SuppliersExtendedService extends SuppliersService {
  constructor(protected httpclient: HttpClient) {
    super(httpclient);
    this.url += '/extended';
  }
}
