import request from '../../utils/request';

export const loginSubmit = (username,password) => {
    return request({
        url: '/api/login',
        method: 'post',
        params: {
            username: username,
            password: password
        }
    });
};

export function loginOut() {
    return request({
        url: 'api/login/loginout',
        method: 'delete',
    })
}
