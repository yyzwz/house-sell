// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getFamilyStyleList = (params) => {
    return getRequest('/familyStyle/getByPage', params)
}
// 添加
export const addFamilyStyle = (params) => {
    return postRequest('/familyStyle/insertOrUpdate', params)
}
// 编辑
export const editFamilyStyle = (params) => {
    return putRequest('/familyStyle/insertOrUpdate', params)
}
// 删除
export const deleteFamilyStyle = (params) => {
    return postRequest('/familyStyle/delByIds', params)
}