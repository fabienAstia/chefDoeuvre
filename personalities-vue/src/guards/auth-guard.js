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

export function userRole(to, from, next){
    let token = localStorage.getItem('jwt');
    if(!token){
        alert('You need to sign up first or sign in')
        return next('/');
    }
    try {
        const decodedToken = jwtDecode(token);
        console.log('decodedToken =' + decodedToken);
        const current_time = new Date().getTime() / 1000;
        if((decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN') 
            && current_time < decodedToken.exp ){
            next();
        } else if(current_time > decodedToken.exp){
            alert('You need to sign in')
            next('/')
        }
    } catch(error) {
        console.error('invalid token', error);
        next('/');
    }
}

