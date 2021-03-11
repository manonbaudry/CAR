<template>
  <div class="q-pa-md" style="max-width: 400px">
    <h3>SignIn</h3>
    <q-form
      @submit="onSubmit"
      class="q-gutter-md"
    >
      <q-input label="Mail" v-model="connectionInfo.mail" filled type="email"/>
      <q-input v-model="connectionInfo.password" filled :type="isPwd ? 'password' : 'text'">
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
        <q-btn label="Create account"  color="primary" flat class="q-ml-sm" @click="goSignup"/>
      </div>
    </q-form>

  </div>

</template>

<script lang="ts">
import { connect, getCustomerById } from '../api/CustomerService'
import { ConnectionInfo } from 'src/api/Entities/ConnectionInfo'

export default {
  name: 'Connexion',
  data: () => ({
    connectionInfo: Object.assign(new ConnectionInfo(), { mail: '', password: '' }),
    isPwd: true
  }),
  methods: {
    async onSubmit () {
      try {
        const customer = await connect(this.connectionInfo)
        localStorage.setItem('customer', JSON.stringify(customer.data))
        this.$q.notify({
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          message: 'successful connection'
        })
        this.$router.push('/list')
      } catch (error) {
        console.log(error)
        this.$q.notify({
          color: 'red',
          textColor: 'white',
          message: 'Wrong mail or password'
        })
      }
    },
    goSignup () {
      this.$router.push('signup')
    }
  }
}
</script>

<style scoped>

</style>
