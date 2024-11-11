import {reactive, computed} from 'vue';
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength, between } from '@vuelidate/validators'


export function validateRegistration(){
    const registerForm = reactive({
        username: '',
        email: '',
        password:''
    })
      
    const emailPattern = /^(?=.{1,64}@)\w+([\.-]?\w+)*@(?=.{4,252}$)\w+([\.-]?\w+)*(\.\w{2,4})+$/;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s])\S{8,16}$/;
    const containsUppercase = (registerForm) => {
        return /[A-Z]/.test(registerForm)
    }
      
    const rules = computed(() =>{
        return {
            username: { 
                required: helpers.withMessage("This field cannot be empty", required),
                minLength: helpers.withMessage("Username must contain at least 3 characters.",  minLength(3)),
                maxLength: helpers.withMessage("Username must not contain most than 20 characters", maxLength(20))
            }, 
            email: { 
                required: helpers.withMessage("This field cannot be empty", required), 
                minLength: helpers.withMessage("Email must contain at least 4 characters", minLength(4))
            }, 
            password: {
                required: helpers.withMessage("This field cannot be empty", required), 
                minLength: helpers.withMessage("Password must contain at least 8 charactesr", minLength(8)), 
                maxLength: helpers.withMessage("Password must not exceed 16 characters", maxLength(16)), 
                // containsUppercase: helpers.withMessage("Password must contain 1 uppercase", containsUppercase),

                // containsUppercase: helpers.withMessage(
                //     () => `The password requires an uppercase`,
                //     (registerForm) => /[A-Z]/.test(registerForm)
                // ),
                containsUppercase: function(registerForm){
                    return /[A-Z]/.test(registerForm)
                },
                containsUppercase: function(registerForm) {
                    return /[A-Z]/.test(registerForm)
                },
                containsLowercase: function(registerForm) {
                    return /[a-z]/.test(registerForm)
                },
                containsNumber: function(registerForm) {
                    return /[0-9]/.test(registerForm)
                },
                containsSpecial: function(registerForm) {
                    return /[#?!@$%^&*-]/.test(registerForm)
                },
                // containsPasswordRequirement: helpers.withMessage(
                //     () => `The password requires an uppercase, lowercase, number and special character`, 
                //     (value) => /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])/.test(value)
                // )
            }
        }
    })

    async function handleSubmit() {
        const result = await v$.value.$validate()
        if (!result) {
          alert('The form has errors')
          return
        }
        alert('Form submitted successfully');
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
        registerForm,
        emailPattern,
        passwordPattern,
        rules,
        handleSubmit,
        v$,
        newRegistration
    }
}


