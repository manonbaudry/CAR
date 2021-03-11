import { Product } from 'src/api/Entities/Product'

export interface Stock {
  id: number;
  remainingProducts: number;
  product: Product;
}
