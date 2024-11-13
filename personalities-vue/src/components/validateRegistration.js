import {ref, reactive, computed} from 'vue';
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength } from '@vuelidate/validators'


export function validateRegistration(){
    const registerForm = reactive({
        username: '',
        email: '',
        password:''
    })
      
    const emailPattern = /^(?=.{1,64}@)\w+([\.-]?\w+)*@(?=.{4,252}$)\w+([\.-]?\w+)*(\.\w{2,4})+$/;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s])\S{8,16}$/;

    const upperCase = /(?=.*[A-Z])/;
    const lowerCase = /(?=.*[a-z])/;
    const digit = /(?=.*\d)/;
    const specialChar = /(?=.*[^\w\s])/;

    const validEmail = (registerForm) => emailPattern.test(registerForm);
    const containsUppercase = (registerForm) => upperCase.test(registerForm);
    const containsLowercase = (registerForm) => lowerCase.test(registerForm);
    const containsDigit = (registerForm) => digit.test(registerForm);
    const containSpecialChar = (registerForm) => specialChar.test(registerForm);

    const rules = computed(() =>{
        return {
            username: { 
                required: helpers.withMessage("Username is required", required),
                minLength: minLength(3),
                maxLength: maxLength(20)
            }, 
            email: { 
                required: helpers.withMessage("Email is required", required), 
                validMail: helpers.withMessage("Email must be valid", validEmail)
            }, 
            password: {
                required: helpers.withMessage("Password is required", required), 
                minLength: minLength(8),
                maxLength: maxLength(16),
                withUppercase: helpers.withMessage("Password must contain 1 uppercase", containsUppercase),
                withLowercase: helpers.withMessage("Password must contain 1 lowercase", containsLowercase),
                withDigit: helpers.withMessage("Password must contain 1 digit", containsDigit),
                withSpecialChar: helpers.withMessage("Password must contain 1 special character", containSpecialChar)
            }
        }
    })

    function touchField(field) {
        field.value.$touch();
    }

    const validatedRegisterForm = ref(false);

    async function handleSubmit() {
        const result = await v$.value.$touch() 
        if (!result) {
          alert('The form has errors')
          return 
        }
        alert('Form submitted successfully');
        validatedRegisterForm  = true;
        newRegistration();
    }
      
    const v$ = useVuelidate(rules, registerForm)

     
    const newRegistration = async() => {
        const url = 'http://localhost:8080/api/auth/register'
        const options = {
            method:'POST',
            headers:{'Content-type':'application/json'},
            body:JSON.stringify(registerForm.value)
        }
        try {
            const response = await fetch(url, options);
            if (response.ok){
            emit('registered');
            console.log('Emission de l\'événement "registered"');
            }else{
            alert('A client or server side error has occured');
            }
        } catch(err) {
            alert('An expected error has occured');
            console.error('An expected error has occured', err);
        }
    }

    return {
        validatedRegisterForm,
        registerForm,
        emailPattern,
        passwordPattern,
        rules,
        handleSubmit,
        v$,
        newRegistration,
        touchField
    }
}


