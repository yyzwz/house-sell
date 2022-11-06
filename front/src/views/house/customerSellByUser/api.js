import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getCustomerSellOne = (params) => {
    return getRequest('/customerSell/getOne', params)
}
export const getCustomerSellList = (params) => {
    return getRequest('/customerSell/getByMyPage', params)
}
export const getCustomerSellCount = (params) => {
    return getRequest('/customerSell/count', params)
}
export const addCustomerSell = (params) => {
    return postRequest('/customerSell/insert', params)
}
export const editCustomerSell = (params) => {
    return postRequest('/customerSell/update', params)
}
export const addOrEditCustomerSell = (params) => {
    return postRequest('/customerSell/insertOrUpdate', params)
}
export const deleteCustomerSell = (params) => {
    return postRequest('/customerSell/delByIds', params)
}
export const passCustomerSell = (params) => {
    return postRequest('/customerSell/pass', params)
}
export const notPassCustomerSell = (params) => {
    return postRequest('/customerSell/notPass', params)
}