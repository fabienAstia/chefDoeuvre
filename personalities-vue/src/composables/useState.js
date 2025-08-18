import {ref} from 'vue';
import { jwtDecode } from 'jwt-decode';

const sharedState = ref('');
const token = localStorage.getItem('jwt');
const decodedToken = jwtDecode(token);

export function useSharedState(){
    if(!decodedToken) sharedState.value = '';
    if(decodedToken.role === 'ROLE_USER' || decodedToken.role === 'ROLE_ADMIN') sharedState.value = 'logged'
    return sharedState;
}