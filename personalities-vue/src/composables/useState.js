import {ref} from 'vue';

const sharedState = ref('');

export function useSharedState(){
    return sharedState;
}