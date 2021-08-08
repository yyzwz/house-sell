// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getVillageIntroduceList = (params) => {
    return getRequest('/villageIntroduce/getByPage', params)
}
// 添加
export const addVillageIntroduce = (params) => {
    return postRequest('/villageIntroduce/insert', params)
}
// 编辑
export const editVillageIntroduce = (params) => {
    return putRequest('/villageIntroduce/update', params)
}
// 删除
export const deleteVillageIntroduce = (params) => {
    return postRequest('/villageIntroduce/delByIds', params)
}
export const audit = (params) => {
    return postRequest('/villageIntroduce/audit', params)
}