// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getVillageTravelList = (params) => {
    return getRequest('/villageTravel/getByPage', params)
}
// 添加
export const addVillageTravel = (params) => {
    return postRequest('/villageTravel/insertOrUpdate', params)
}
// 编辑
export const editVillageTravel = (params) => {
    return putRequest('/villageTravel/insertOrUpdate', params)
}
// 删除
export const deleteVillageTravel = (params) => {
    return postRequest('/villageTravel/delByIds', params)
}