import axios from 'axios'

export function getAllProductsInStock () {
  return axios.get('http://localhost:8080/stock')
}
