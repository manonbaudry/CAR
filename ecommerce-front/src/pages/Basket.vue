<template>
  <div>
    <h3 class="q-ma-md">Shopping Cart</h3>
    <h5>{{customer.name}}</h5>
    <div class="q-pa-md row items-start q-gutter-md" v-if="cart">
      <div v-for="purchase in cart.purchases"
           v-bind:key="purchase.product.id"
           class="col-3"
      >
        <div class="col-3">
          <basket-detail :purchase="purchase"/>
        </div>
      </div>
    </div>
    <div class="q-pa-lg">
      <q-btn label="Order" color="primary" @click="order"/>
    </div>
  </div>
</template>

<script>
import BasketDetail from '../components/BasketDetail'
import { createPurchase } from '../api/PurchaseService'
import moment from 'moment'
export default {
  name: 'Basket',
  components: { BasketDetail },
  data () {
    return {
      cart: Object,
      customer: Object
    }
  },
  mounted () {
    this.cart = JSON.parse(localStorage.getItem('basket'))
    this.customer = JSON.parse(localStorage.getItem('customer'))
  },
  methods: {
    order () {
      const purchase = {
        customer: this.customer,
        products: this.cart.purchases,
        date: moment()
      }
      console.log(purchase)
      createPurchase(purchase)
      localStorage.removeItem('basket')
      this.$q.notify({
        color: 'green-4',
        textColor: 'white',
        icon: 'cloud_done',
        message: 'order complete'
      })
      this.$router.push('/history')
    }
  }
}
</script>

<style scoped>

</style>
