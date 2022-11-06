import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getHouseSellOrderOne = (params) => {
    return getRequest('/houseSellOrder/getOne', params)
}
export const getHouseSellOrderList = (params) => {
    return getRequest('/houseSellOrder/getBySellPage', params)
}
export const getHouseSellOrderCount = (params) => {
    return getRequest('/houseSellOrder/count', params)
}
export const addHouseSellOrder = (params) => {
    return postRequest('/houseSellOrder/insert', params)
}
export const editHouseSellOrder = (params) => {
    return postRequest('/houseSellOrder/update', params)
}
export const addOrEditHouseSellOrder = (params) => {
    return postRequest('/houseSellOrder/insertOrUpdate', params)
}
export const deleteHouseSellOrder = (params) => {
    return postRequest('/houseSellOrder/delByIds', params)
}