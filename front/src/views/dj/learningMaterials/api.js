// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getLearningMaterialsList = (params) => {
    return getRequest('/learningMaterials/getByPage', params)
}
// 添加
export const addLearningMaterials = (params) => {
    return postRequest('/learningMaterials/insertOrUpdate', params)
}
// 编辑
export const editLearningMaterials = (params) => {
    return putRequest('/learningMaterials/insertOrUpdate', params)
}
// 删除
export const deleteLearningMaterials = (params) => {
    return postRequest('/learningMaterials/delByIds', params)
}