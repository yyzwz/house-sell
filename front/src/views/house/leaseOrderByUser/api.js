import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getHouseLeaseOrderOne = (params) => {
    return getRequest('/houseLeaseOrder/getOne', params)
}
export const getHouseLeaseOrderList = (params) => {
    return getRequest('/houseLeaseOrder/getByBuyPage', params)
}
export const getHouseLeaseOrderCount = (params) => {
    return getRequest('/houseLeaseOrder/count', params)
}
export const addHouseLeaseOrder = (params) => {
    return postRequest('/houseLeaseOrder/insert', params)
}
export const editHouseLeaseOrder = (params) => {
    return postRequest('/houseLeaseOrder/update', params)
}
export const addOrEditHouseLeaseOrder = (params) => {
    return postRequest('/houseLeaseOrder/insertOrUpdate', params)
}
export const deleteHouseLeaseOrder = (params) => {
    return postRequest('/houseLeaseOrder/delByIds', params)
}
export const pay = (params) => {
    return postRequest('/houseLeaseOrder/pay', params)
}