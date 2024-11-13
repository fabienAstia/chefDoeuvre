<script setup>
import { useI18n } from 'vue-i18n';
import { validateRegistration } from './validateRegistration';
const {t} = useI18n;

const {
  validatedRegisterForm,
  registerForm,
  handleSubmit,
  v$
} = validateRegistration();

const emit = defineEmits(['registered']);
</script>


<template>
<!-- register -->
<div class="modal fade" id="register" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header text-bg-light">
        <h5 class="modal-title">{{$t('register.title')}}</h5>
        <button type="button" class="btn-close text-bg-light" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form @submit.prevent="handleSubmit">
        <div class="modal-body text-bg-light" >
            <div class="form-group">
              <label for="username" class="col-form-label">{{$t('register.username')}} :</label>
              <input type="text" class="form-control" id="username" v-model="registerForm.username" @blur="v$.username.$touch()">   
              <span v-for=" error in v$.username.$errors"   class="text-danger">{{ error.$message }}</span>
            </div>
            <div class="form-group">
              <label for="email" class="col-form-label">{{$t('register.email')}} :</label>
              <input type="email" class="form-control" id="email" v-model="registerForm.email" @blur="v$.email.$touch()">
              <span v-for="error in v$.email.$errors" :key="error.$uid" class="text-danger"><div>{{ error.$message }}</div></span>
            </div>
            <div class="form-group">
              <label for="password" class="col-form-label">{{$t('register.password')}} :</label>
              <input type="password" class="form-control" id="password" v-model="registerForm.password" @blur="v$.password.$touch()">
              <span v-for="error in v$.password.$errors" :key="error.$uid" class="text-danger "><div>{{ error.$message }}</div></span>
            </div>
        </div>
        <div class="modal-footer text-bg-light">
          <button type="submit" class="btn btn-secondary" >{{$t('register.submit')}}</button>
        </div>
    </form>

    </div>
  </div>
</div>

</template> 


<style scoped>
  .container{
    max-width: 768px;
  }
</style>
