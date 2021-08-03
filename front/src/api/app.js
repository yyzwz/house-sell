// 统一请求路径前缀在libs/axios.js中修改
import { getRequest, postRequest, postNoAuthRequest, postBodyRequest, getNoAuthRequest } from '@/libs/axios';


// 登录
export const login = (params) => {
    return postNoAuthRequest('/app/v1/login', params)
}



// 分页获取数据
export const getMemberList = (params) => {
    return getRequest('/app/member/getByCondition', params)
}
// 添加
export const addMember = (params) => {
    return postRequest('/app/member/admin/add', params)
}
// 编辑
export const editMember = (params) => {
    return postRequest('/app/member/admin/edit', params)
}
// 状态
export const statusMember = (params) => {
    return postRequest('/app/member/admin/status', params)
}
// 删除
export const deleteMember = (params) => {
    return postRequest('/app/member/delByIds', params)
}






