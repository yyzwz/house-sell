// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getDynamicBuildList = (params) => {
    return getRequest('/dynamicBuild/getByPage', params)
}
// 添加
export const addDynamicBuild = (params) => {
    return postRequest('/dynamicBuild/insertOrUpdate', params)
}
// 编辑
export const editDynamicBuild = (params) => {
    return putRequest('/dynamicBuild/insertOrUpdate', params)
}
// 删除
export const deleteDynamicBuild = (params) => {
    return postRequest('/dynamicBuild/delByIds', params)
}