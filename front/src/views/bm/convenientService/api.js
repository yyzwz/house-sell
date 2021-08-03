// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getConvenientServiceList = (params) => {
    return getRequest('/convenientService/getByPage', params)
}
// 添加
export const addConvenientService = (params) => {
    return postRequest('/convenientService/insertOrUpdate', params)
}
// 编辑
export const editConvenientService = (params) => {
    return putRequest('/convenientService/insertOrUpdate', params)
}
// 删除
export const deleteConvenientService = (params) => {
    return postRequest('/convenientService/delByIds', params)
}