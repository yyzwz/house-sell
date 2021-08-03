// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getOnlineExaminationList = (params) => {
    return getRequest('/onlineExamination/getByPage', params)
}
// 添加
export const addOnlineExamination = (params) => {
    return postRequest('/onlineExamination/insertOrUpdate', params)
}
// 编辑
export const editOnlineExamination = (params) => {
    return putRequest('/onlineExamination/insertOrUpdate', params)
}
// 删除
export const deleteOnlineExamination = (params) => {
    return postRequest('/onlineExamination/delByIds', params)
}