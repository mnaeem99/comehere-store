export interface IInventory {
  inventoryId: number;
  lastRestocked?: Date;
  quantity: number;

  productsDescriptiveField?: number;
  productId: number;
  suppliersDescriptiveField?: number;
  supplierId: number;
}
