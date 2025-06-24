import {ref} from 'vue'; 
import i18n from '@/locales';

export function useErrorHandler(){
    const error = ref({
        data:{
             fieldsErrors: [],
             GlobalErrors: []
            }
    })

    const handleError = (err) => {
        if(err.response){
            const statusCode = err.response.status;
            error.value.data = err.response.data;
            if(statusCode === 401) {
                return i18n.global.t('error.bad_credentials')
            }
            if(statusCode >= 400 && statusCode < 500) {
                return error.value.data;
            } else if (statusCode >= 500 && statusCode < 600) {
                return i18n.global.t('error.server')
            }
        } else {
            console.error('An unexpected error has occured', err);
            return i18n.global.t('error.unexpected')
        }
    }

    return {
        handleError
    }
}