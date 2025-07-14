import i18n from '@/locales';

export function useErrorHandler(){

    const handleError = (err) => {
        if(err?.response){
            const statusCode = err.response.status;

            if(statusCode === 401) {
                return i18n.global.t('error.bad_credentials')
            }
            if(statusCode >= 400 && statusCode < 500) {
                const data = err.response.data
                if(data.fieldsErrors && data.fieldsErrors.username?.length !== 0){
                    return data.fieldsErrors.username[0]
                }
                if(data.globalErrors && data.globalErrors.length !== 0){
                    return data.globalErrors[0]
                } else {
                    return data;
                }
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