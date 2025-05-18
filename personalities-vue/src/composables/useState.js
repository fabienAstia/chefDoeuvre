import {ref} from 'vue';

const sharedState = ref('');
const isLogged = () => {
    
    // ref(localStorage.getItem('token'))
}
export function useSharedState(){
    return sharedState;
}