// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getReplyList = (params) => {
    return getRequest('/autoChat/getByCondition', params)
}
// 添加
export const addReply = (params) => {
    return postRequest('/autoChat/save', params)
}
// 编辑
export const editReply = (params) => {
    return putRequest('/autoChat/update', params)
}
// 删除
export const deleteReply = (params) => {
    return postRequest('/autoChat/delByIds', params)
}