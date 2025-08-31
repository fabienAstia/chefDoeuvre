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
        const current_time = Math.floor(Date.now() / 1000);
        if(current_time >= decodedToken.exp) {
            alert(t('guard.expired_token'))
            return next('/')
        }
        if(decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN'){
            return next();
        } 
        return next('/');
    } catch(error) {
        return next('/');
    }
}

