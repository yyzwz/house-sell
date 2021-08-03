// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getTaskDisclosureList = (params) => {
    return getRequest('/taskDisclosure/getByPage', params)
}
// 添加
export const addTaskDisclosure = (params) => {
    return postRequest('/taskDisclosure/insertOrUpdate', params)
}
// 编辑
export const editTaskDisclosure = (params) => {
    return putRequest('/taskDisclosure/insertOrUpdate', params)
}
// 删除
export const deleteTaskDisclosure = (params) => {
    return postRequest('/taskDisclosure/delByIds', params)
}