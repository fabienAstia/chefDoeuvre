import {ref} from 'vue';
import { jwtDecode } from 'jwt-decode';

const sharedState = ref('');

export function useSharedState(){
    const token = localStorage.getItem('jwt');
    if(!token) {
        sharedState.value = 'guest';
        return sharedState;
    } else {
        const decodedToken = jwtDecode(token);
        if(decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN') {
            sharedState.value = 'logged'
        } else {
            sharedState.value = 'guest'
        }
    }
    return sharedState;
}