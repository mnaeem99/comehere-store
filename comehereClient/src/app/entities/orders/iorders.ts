export interface IOrders {
  orderDate?: Date;
  orderId: number;
  status: string;
  total: number;

  customersDescriptiveField?: string;
  customerId: number;
}
