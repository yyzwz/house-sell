// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getVillageAppointmentList = (params) => {
    return getRequest('/villageAppointment/getByPage', params)
}
// 添加
export const addVillageAppointment = (params) => {
    return postRequest('/villageAppointment/insertOrUpdate', params)
}
// 编辑
export const editVillageAppointment = (params) => {
    return putRequest('/villageAppointment/insertOrUpdate', params)
}
// 删除
export const deleteVillageAppointment = (params) => {
    return postRequest('/villageAppointment/delByIds', params)
}