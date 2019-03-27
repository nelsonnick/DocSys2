import axios from './api'

/* 将所有接口统一起来便于维护
 * 如果项目很大可以将 url 独立成文件，接口分成不同的模块
 */

// 单独导出
export const DepartmentAdd = (name, phone, address) => {
  return axios({
    url: `/department/add?name=${name}&phone=${phone}&address=${address}`,
    method: 'get'
  })
}
export const query = (queryURL, keyword, pageCurrent, pageSize) => {
  return axios({
    url: `${queryURL}?keyword=${keyword}&pageCurrent=${pageCurrent}&pageSize=${pageSize}`,
    method: 'get'
  })
}
export const total = (totalURL, keyword) => {
  return axios({
    url: `${totalURL}?keyword=${keyword}`,
    method: 'get'
  })
}
export const list = (id) => {
  return axios({
    url: `/list${id}`,
    method: 'get'
  })
}

export const upload = data => {
  return axios({
    url: '/upload',
    method: 'post',
    data
  })
}

// 默认全部导出
export default {
  DepartmentAdd,
  query,
  total,
  list,
  upload
}
