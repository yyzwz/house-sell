import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getCustomerLeaseOne = (params) => {
    return getRequest('/customerLease/getOne', params)
}
export const getCustomerLeaseList = (params) => {
    return getRequest('/customerLease/getBySellerPage', params)
}
export const getCustomerLeaseCount = (params) => {
    return getRequest('/customerLease/count', params)
}
export const addCustomerLease = (params) => {
    return postRequest('/customerLease/insert', params)
}
export const editCustomerLease = (params) => {
    return postRequest('/customerLease/update', params)
}
export const addOrEditCustomerLease = (params) => {
    return postRequest('/customerLease/insertOrUpdate', params)
}
export const deleteCustomerLease = (params) => {
    return postRequest('/customerLease/delByIds', params)
}
export const passCustomerLease = (params) => {
    return postRequest('/customerLease/pass', params)
}
export const notPassCustomerLease = (params) => {
    return postRequest('/customerLease/notPass', params)
}