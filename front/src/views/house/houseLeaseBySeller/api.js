import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getHouseLeaseOne = (params) => {
    return getRequest('/houseLease/getOne', params)
}
export const getHouseLeaseList = (params) => {
    return getRequest('/houseLease/getBySeller', params)
}
export const getHouseLeaseCount = (params) => {
    return getRequest('/houseLease/count', params)
}
export const addHouseLease = (params) => {
    return postRequest('/houseLease/insertBySeller', params)
}
export const editHouseLease = (params) => {
    return postRequest('/houseLease/update', params)
}
export const addOrEditHouseLease = (params) => {
    return postRequest('/houseLease/insertOrUpdate', params)
}
export const deleteHouseLease = (params) => {
    return postRequest('/houseLease/delByIds', params)
}