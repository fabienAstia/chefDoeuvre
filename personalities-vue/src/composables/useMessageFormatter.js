import { useErrorHandler } from "./useErrorHandler";

export const formatAlert = (input) => {
    const {handleError} = useErrorHandler();

    if(typeof(input) === 'string'){
        return {type: 'info', message: input}
    }

    if(input instanceof Error){
        return {type: 'error', message: handleError(input)}
    }

    return {type: 'error', message: 'Erreur inconnue'}
}