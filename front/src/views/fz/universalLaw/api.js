// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getUniversalLawList = (params) => {
    return getRequest('/universalLaw/getByPage', params)
}
// 添加
export const addUniversalLaw = (params) => {
    return postRequest('/universalLaw/insertOrUpdate', params)
}
// 编辑
export const editUniversalLaw = (params) => {
    return putRequest('/universalLaw/insertOrUpdate', params)
}
// 删除
export const deleteUniversalLaw = (params) => {
    return postRequest('/universalLaw/delByIds', params)
}