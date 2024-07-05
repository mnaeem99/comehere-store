export interface IOrderItems {
  orderItemId: number;
  price: number;
  quantity: number;

  ordersDescriptiveField?: number;
  orderId: number;
  productsDescriptiveField?: number;
  productId: number;
}
