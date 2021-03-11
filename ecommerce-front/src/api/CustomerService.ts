import axios from 'axios'
import { Customer } from 'src/api/Entities/Customer'
import { ConnectionInfo } from 'src/api/Entities/ConnectionInfo'

export function connect (connectionInfo : ConnectionInfo) {
  return axios.post('http://localhost:8080/customer/connect', connectionInfo)
}

export function getCustomerById (id:number) {
  return axios.get(`http://localhost:8080/customer/${id}`)
}

export function createCustomer (customer: Customer) {
  return axios.post('http://localhost:8080/customer', customer)
}
