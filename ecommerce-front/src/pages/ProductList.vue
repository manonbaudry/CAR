<template>
  <div>
    <h3 lass="q-ma-md">Products</h3>
    <div class="q-pa-md row items-start q-gutter-md">
    <div v-for="product in products.data"
         v-bind:key="product.id"
         class="col-3"
    >
      <div class="col-3">
        <product-detail v-bind="product"/>
      </div>
    </div>
    </div>
  </div>
</template>

<script>

import { getAllProductsInStock } from '../api/StockService'
import ProductDetail from '../components/ProductDetail'

export default {
  name: 'ProductList',
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  components: { ProductDetail },
  data () {
    return {
      products: []
    }
  },
  methods: {
  },
  async mounted () {
    this.products = await getAllProductsInStock()
  }

}
</script>

<style scoped>

</style>
