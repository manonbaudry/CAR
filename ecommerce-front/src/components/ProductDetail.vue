<template>
  <div class="q-pa-md row items-start q-gutter-md">
    <q-card>
      <q-card-section>
        <div class="text-h6">{{product.name}}</div>
        <div class="text-subtitle2">{{product.price}} £</div>
      </q-card-section>

      <q-card-section>
        {{ product.description }}
      </q-card-section>

      <q-separator dark />

      <q-card-actions>
        <q-input
          outlined
          dense
          filled
          type="number"
          label="Quantity"
          lazy-rules
          v-model="quantity"
          :rules="[
          val => val !== null && val !== '' || 'Please type your age'
        ]"
        />
      </q-card-actions>
      <q-separator dark />
      <q-card-actions v-if="quantity > 0">
        <q-btn label="Add to cart" @click="submit" color="primary"/>
        <q-btn label="Reset" @click="reset" color="primary" flat class="q-ml-sm" />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script>

export default {
  name: 'ProductDetail',
  data: () => ({
    quantity: 0
  }),
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  methods: {
    submit () {
      const purchase = {
        product: this.product,
        quantity: this.quantity
      }
      if (localStorage.getItem('basket')) {
        const basket = JSON.parse(localStorage.getItem('basket'))
        basket.purchases.push(purchase)
        localStorage.setItem('basket', JSON.stringify(basket))
      } else {
        const basket = {
          purchases: [purchase]
        }
        localStorage.setItem('basket', JSON.stringify(basket))
      }
      this.$q.notify({
        color: 'green-4',
        textColor: 'white',
        icon: 'cloud_done',
        message: 'Product succressfully added'
      })
    },
    reset () {
      this.quantity = 0
    }
  }
}
</script>

<style scoped>

</style>
