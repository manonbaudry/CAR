<template>
  <div class="q-pa-md items-start q-gutter-xs">
    <q-card>
      <q-card-section>
        <div class="text-subtitle2">Purchase n°{{purchase.id}}</div>
        <div class="text-subtitle2">Date : {{purchase.date }}</div>
      </q-card-section>
      <q-separator />
      <q-card-section>
        <div class="text-subtitle2"> Detail : </div>
        <div v-for="p in purchase.products"
             v-bind:key="p.product.id"
        >
          {{p.product.name}} <br/>
          Quantity : {{p.quantity}}
        </div>
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
export default {
  name: 'HistoryDetail',
  props: {
    purchase: {
      type: Object,
      required: true
    }
  },
  mounted () {
    console.log(this.purchase)
    // console.log(this.purchase.date.formatDate('DD-MM-YYYY HH:mm:ss'))
  }
}
</script>

<style scoped>

</style>
