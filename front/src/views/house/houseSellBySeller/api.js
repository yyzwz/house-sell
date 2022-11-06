import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getHouseSellOne = (params) => {
    return getRequest('/houseSell/getOne', params)
}
export const getHouseSellList = (params) => {
    return getRequest('/houseSell/getBySeller', params)
}
export const getHouseSellCount = (params) => {
    return getRequest('/houseSell/count', params)
}
export const addHouseSell = (params) => {
    return postRequest('/houseSell/insertBySeller', params)
}
export const editHouseSell = (params) => {
    return postRequest('/houseSell/update', params)
}
export const addOrEditHouseSell = (params) => {
    return postRequest('/houseSell/insertOrUpdate', params)
}
export const deleteHouseSell = (params) => {
    return postRequest('/houseSell/delByIds', params)
}