<template>
  <div class="q-pa-md">
    <h3>Your order history</h3>
    <h5>{{customer.name}}</h5>

    <div v-for="purchase in purchaseHistory.data"
         v-bind:key="purchase.id"
         class="col-3"
    >
      <history-detail :purchase="purchase"/>
    </div>
  </div>
</template>

<script>
import { getHistory } from '../api/PurchaseService'
import HistoryDetail from '../components/HistoryDetail'

export default {
  name: 'History',
  // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
  components: { HistoryDetail },
  data () {
    return {
      purchaseHistory: Object,
      customer: Object
    }
  },
  async mounted () {
    this.customer = JSON.parse(localStorage.getItem('customer'))
    this.purchaseHistory = await getHistory(this.customer.id)
  }
}
</script>

<style scoped>

</style>
