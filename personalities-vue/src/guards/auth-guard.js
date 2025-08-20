import { jwtDecode } from 'jwt-decode';
import i18n from '@/locales';

const t = i18n.global.t;

export function adminRole(to, from, next){
    const token = localStorage.getItem('jwt');
    if(!token){
        return next('/');
    }
    try {
        const decodedToken = jwtDecode(token);
        if(decodedToken.role === 'ROLE_ADMIN'){
            next();
        } else {
            next('/')
        }
    } catch(error) {
        console.error('invalid token', error);
        next('/');
    }
}

export function userRole(to, from, next){
    let token = localStorage.getItem('jwt');
    if(!token){
        alert(t('guard.no_token'))
        return next('/');
    }
    try {
        const decodedToken = jwtDecode(token);
        console.log('decodedToken =', decodedToken);
        const current_time = new Date().getTime() / 1000;
        if((decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN') 
            && current_time < decodedToken.exp ){
            next();
        } else if(current_time > decodedToken.exp){
            alert(t('guard.expired_token'))
            next('/')
        }
    } catch(error) {
        console.error('invalid token', error);
        next('/');
    }
}

