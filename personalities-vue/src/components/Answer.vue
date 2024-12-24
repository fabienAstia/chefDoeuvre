<script setup>
import {ref} from 'vue';
import{useI18n} from 'vue-i18n';
const{t} = useI18n;
import { useQuestions } from './useQuestions';

const {
  questions,
  question,
  addQuestion,
  loadQuestions,
  editQuestion,
  updateQuestion,
  deleteQuestion,
  sortAxe,
  sortDim,
} = useQuestions();
const hover =  ref(false);
const colorCondition = (buttonIndex) => buttonIndex === 0 ? 'grey' : buttonIndex > 0 ? 'blue' : 'purple';

const addAnswer = async(idQuestion, buttonIndex) => {
  console.log("idQuestion:"+ idQuestion);
  const url ='http://localhost:8080/answers';
  const options = {
    method:'POST',
    headers:{'Content-Type':'application/json'},
    body:JSON.stringify({questionId: idQuestion, rating: buttonIndex})
  }
  try {
    const response = await fetch(url, options);
    if(response.ok){
      alert('You have sent your response');
    }else{
      alert('A client or server error has occured')
    }
  }
  catch(err){
    alert('An unexpected error has occured');
    console.error('An unexpected error has occured', err);
  }
}
</script>

<template>
    <!-- barre de progression -->

   
      <div v-for= "(q, questionIndex) in questions" class=" my-5 py-4 border-bottom d-flex"> <!--export GET Questions -->
        
        <div class="container-fluid">
          <div class=" row fs-2 m-3 text-center ">
            <b>{{questionIndex+1}}. {{q.text}}</b>
          </div>

          <div class="row fs-4 m-3 p-3 d-flex justify-content-center align-items-center" >
            <div class="col-md-2 d-none d-md-block justify-content-end text-end" :style="{color:'purple'}">
              <b>{{$t('answer.agree')}}</b>
            </div>

            <div class="col-12 col-sm-8 col-md-8 buttons d-flex justify-content-center align-items-center m-3 p-0 gap-3">
              <button
                v-for="buttonIndex in [-3, -2, -1, 0, 1, 2, 3]"
                :key="buttonIndex" 
                @mouseover="hover = {questionIndex, buttonIndex}"
                @mouseleave="hover = false"
                @click="addAnswer(q.id, buttonIndex)"
                :class="'btn-circle '" 
                :style="{width:`buttonsWidth=${3 + Math.abs(buttonIndex)*1.1}vw`, 
                  height:`${3 + Math.abs(buttonIndex)*1.1}vw`, 
                  aspectRatio:1/1,
                  borderColor: colorCondition(buttonIndex),
                  backgroundColor: hover.questionIndex === questionIndex && hover.buttonIndex === buttonIndex ? colorCondition(buttonIndex) : 'rgb(240, 240, 240)'}"
              >
              </button>
            </div>

            <div class="col-md-2 d-none d-md-block" :style="{color:'blue'}">
              <b>{{$t('answer.disagree')}}</b>
            </div>

          </div>

          <div class="row d-md-none d-flex justify-content-between w-100 text-center mt-2 buttonsWidth" >
                <div class="col-6 " :style="{color:'purple'}">
                  <b>{{$t('answer.agree')}}</b>
                </div>
                <div class="col-6 text-center" :style="{color:'blue'}">
                  <b>{{$t('answer.disagree')}}</b>
                </div>
            </div>

        </div>

     </div>

</template>

<style scoped>


.buttons{
  min-width: none;
  width: auto; 
}


.btn-circle{
  border-radius: 50%;
  border: solid 3px;
  width: auto;
  height: auto;
  margin: 0;
}

.buttonsWidth{
  width: buttonsWidth;
}
</style>