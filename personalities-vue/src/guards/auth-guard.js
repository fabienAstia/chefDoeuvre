import router from '@/router/index'
import { jwtDecode } from 'jwt-decode';

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

export function userRole(to){
    let token = localStorage.getItem('jwt');
    const decodedToken = jwtDecode(token);
    if(token){
        return decodedToken.role === 'ROLE_USER';
    }

    router.push('/')
}