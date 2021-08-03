// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getGovernanceEffectivenessList = (params) => {
    return getRequest('/governanceEffectiveness/getByPage', params)
}
// 添加
export const addGovernanceEffectiveness = (params) => {
    return postRequest('/governanceEffectiveness/insertOrUpdate', params)
}
// 编辑
export const editGovernanceEffectiveness = (params) => {
    return putRequest('/governanceEffectiveness/insertOrUpdate', params)
}
// 删除
export const deleteGovernanceEffectiveness = (params) => {
    return postRequest('/governanceEffectiveness/delByIds', params)
}