// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getVillageStyleList = (params) => {
    return getRequest('/villageStyle/getByPage', params)
}
// 添加
export const addVillageStyle = (params) => {
    return postRequest('/villageStyle/insertOrUpdate', params)
}
// 编辑
export const editVillageStyle = (params) => {
    return putRequest('/villageStyle/insertOrUpdate', params)
}
// 删除
export const deleteVillageStyle = (params) => {
    return postRequest('/villageStyle/delByIds', params)
}