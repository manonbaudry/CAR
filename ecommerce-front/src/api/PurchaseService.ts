import axios from 'axios'

export function createPurchase (purchase : any) {
  return axios.post('http://localhost:8080/purchase', purchase)
}

export function getHistory (id : number) {
  return axios.get(`http://localhost:8080/purchase/${id}`)
}
