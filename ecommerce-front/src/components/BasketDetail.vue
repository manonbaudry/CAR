<template>
  <div class="q-pa-md items-start q-gutter-xs">
    <q-card>
      <q-card-section>
        <div class="text-h6">{{purchase.product.name}}</div>
        <div class="text-subtitle2">{{purchase.product.price}} £</div>
        <div class="text-subtitle2"> Quantity : {{ purchase.quantity}}</div>
      </q-card-section>
      <q-card-section vertical align="center">
        <q-btn round color="secondary" icon="delete" @click="remove(purchase.product.id)"/>
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
export default {
  name: 'BasketDetail',
  props: {
    purchase: Object
  },
  methods: {
    remove (id) {
      const basket = JSON.parse(localStorage.getItem('basket'))
      console.log(basket)
      basket.purchases.forEach(function (item, index) {
        if (item.product.id === id) {
          basket.purchases.splice(index, 1)
          localStorage.setItem('basket', JSON.stringify(basket))
        }
      })
      this.$q.notify({
        color: 'green-4',
        textColor: 'white',
        icon: 'cloud_done',
        message: 'product removed'
      })
      this.$router.push('/list')
    }
  }
}
</script>

<style scoped>

</style>
