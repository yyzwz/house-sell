import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getHouseSellOne = (params) => {
    return getRequest('/houseSell/getOne', params)
}
export const getHouseSellList = (params) => {
    return getRequest('/houseSell/getByPage', params)
}
export const getHouseSellCount = (params) => {
    return getRequest('/houseSell/count', params)
}
export const addHouseSell = (params) => {
    return postRequest('/houseSell/insert', params)
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
export const pass = (params) => {
    return postRequest('/houseSell/pass', params)
}
export const notPass = (params) => {
    return postRequest('/houseSell/notPass', params)
}