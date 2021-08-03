// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getFolkActivitiesList = (params) => {
    return getRequest('/folkActivities/getByPage', params)
}
// 添加
export const addFolkActivities = (params) => {
    return postRequest('/folkActivities/insertOrUpdate', params)
}
// 编辑
export const editFolkActivities = (params) => {
    return putRequest('/folkActivities/insertOrUpdate', params)
}
// 删除
export const deleteFolkActivities = (params) => {
    return postRequest('/folkActivities/delByIds', params)
}