<template>
  <div class="q-pa-md" style="max-width: 400px">
    <h3>SignUp</h3>
    <q-form
      @submit="onSubmit"
      @reset="onReset"
      class="q-gutter-md"
    >
      <q-input
        filled
        v-model="customer.name"
        label="Name"
        :rules="[ val => val && val.length > 0 || 'Please type your name']"
      />

      <q-input
        filled
        v-model="customer.lastName"
        label="last Name"
        lazy-rules
        :rules="[
          val => val !== null && val !== '' || 'Please type your last Name'
        ]"
      />
      <q-input label="Mail" v-model="customer.mail" filled type="email"/>
      <q-input v-model="customer.password" filled :type="isPwd ? 'password' : 'text'">
        <template v-slot:append>
          <q-icon
            :name="isPwd ? 'visibility_off' : 'visibility'"
            class="cursor-pointer"
            @click="isPwd = !isPwd"
          />
        </template>
      </q-input>
      <div>
        <q-btn label="Submit" type="submit" color="primary"/>
        <q-btn label="Reset" type="reset" color="primary" flat class="q-ml-sm" />
      </div>
    </q-form>

  </div>
</template>

<script>

import { createCustomer } from '../api/CustomerService'

export default {
  name: 'SignUp',
  data: () => ({
    customer: {
      name: '',
      lastName: '',
      mail: '',
      password: ''
    },
    isPwd: true
  }),
  methods: {
    async onSubmit () {
      try{
        const newCustomer = await createCustomer(this.customer)
        localStorage.setItem('customer', JSON.stringify(newCustomer.data))

        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'You have been succressfully registered'
        })
        this.$router.push('/list')
      } catch (error) {
        console.log(error)
        this.$q.notify({
          color: 'red',
          textColor: 'white',
          message: 'an error occured...'
      })
    }

    },
    onReset () {
      this.customer.name = ''
      this.customer.lastName = ''
      this.customer.mail = ''
      this.customer.password = ''
    }
  }
}
</script>

<style scoped>

</style>
