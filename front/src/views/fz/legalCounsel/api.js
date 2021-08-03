// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getLegalCounselList = (params) => {
    return getRequest('/legalCounsel/getByPage', params)
}
// 添加
export const addLegalCounsel = (params) => {
    return postRequest('/legalCounsel/insertOrUpdate', params)
}
// 编辑
export const editLegalCounsel = (params) => {
    return putRequest('/legalCounsel/insertOrUpdate', params)
}
// 删除
export const deleteLegalCounsel = (params) => {
    return postRequest('/legalCounsel/delByIds', params)
}