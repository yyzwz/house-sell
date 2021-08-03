// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getCivilizedPracticeList = (params) => {
    return getRequest('/civilizedPractice/getByPage', params)
}
// 添加
export const addCivilizedPractice = (params) => {
    return postRequest('/civilizedPractice/insertOrUpdate', params)
}
// 编辑
export const editCivilizedPractice = (params) => {
    return putRequest('/civilizedPractice/insertOrUpdate', params)
}
// 删除
export const deleteCivilizedPractice = (params) => {
    return postRequest('/civilizedPractice/delByIds', params)
}