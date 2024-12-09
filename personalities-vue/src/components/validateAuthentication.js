import {ref, reactive, computed} from 'vue';
import {useRouter} from 'vue-router';
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength } from '@vuelidate/validators'


export function validateAuthentication(){
    const router = useRouter();

    const createUserForm = ref({
        email: '',
        password:''
    })

    const isValidLength = computed(() => {
        return createUserForm.value.password.length <= 20 && createUserForm.value.password.length >= 8;
      });
      const containsUpperCase = computed(() => {
         return createUserForm.value.password !== createUserForm.value.password.toLowerCase();
      });
      const containsLowerCase = computed(() => {
         return createUserForm.value.password !== createUserForm.value.password.toUpperCase();
      });
      const hasDigit = computed(() => {
         return /\d/.test(createUserForm.value.password);
      });
      const containsSpecialChar = computed(() => {
         return /[^\w\s]/.test(createUserForm.value.password);
      });
      
    const emailPattern = /^(?=.{1,64}@)\w+([\.-]?\w+)*@(?=.{4,252}$)\w+([\.-]?\w+)*(\.\w{2,4})+$/;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s])\S{8,16}$/;

    const upperCase = /(?=.*[A-Z])/;
    const lowerCase = /(?=.*[a-z])/;
    const digit = /(?=.*\d)/;
    const specialChar = /(?=.*[^\w\s])/;

    const validEmail = (createUserForm) => emailPattern.test(createUserForm);
    const containsUppercase = (createUserForm) => upperCase.test(createUserForm);
    const containsLowercase = (createUserForm) => lowerCase.test(createUserForm);
    const containsDigit = (createUserForm) => digit.test(createUserForm);
    const containSpecialChar = (createUserForm) => specialChar.test(createUserForm);

    const rules = computed(() =>{
        return {
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

    const validatedRegisterForm = new Event("registered");

    async function handleSubmit() {
        const result = await v$.value.$validate() 
        if (!result) {
            alert('The form has errors');
        }else{
            alert('Form submitted successfully');
            newUser();
        }
    }
      
    const v$ = useVuelidate(rules, createUserForm)

     
    const newUser = async() => {
        const url = 'http://localhost:8080/users'
        const options = {
            method:'POST',
            headers:{'Content-type':'application/json'},
            body:JSON.stringify(createUserForm.value)
        }
        try {
            const response = await fetch(url, options);
            if (response.ok){
                alert('you have created an account');
                router.push('/authenticate');
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
        createUserForm,
        emailPattern,
        passwordPattern,
        rules,
        handleSubmit,
        v$,
        newUser,
        touchField
    }
}


