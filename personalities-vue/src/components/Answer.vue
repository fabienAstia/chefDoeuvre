<script setup>
import {ref} from 'vue';
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
const buttonsWidth = ref(0);
const colorCondition = (buttonIndex) => buttonIndex === 0 ? 'grey' : buttonIndex > 0 ? 'blue' : 'purple';
const answer = () => {};
</script>

<template>
    <!-- barre de progression -->

   
     <div v-for= "(q, questionIndex) in questions" class=" my-5 py-4 border-bottom d-flex">
        
        <div class="container-fluid">
          <!-- question -->
          <div class=" row fs-2 m-3 text-center ">
            <b>{{questionIndex+1}}. {{q.name }}</b>
          </div>

          <!-- espace réponse -->
          <div class="row fs-5 m-3 p-3 d-flex justify-content-center align-items-center" >
            
            <div class="col-md-2 d-none d-md-block" :style="{color:'purple'}">
              <b >En désaccord </b>
            </div>

            <div class="col-12 col-sm-8 col-md-8 buttons d-flex justify-content-center align-items-center m-3 p-0 gap-3">
              <button
                v-for="buttonIndex in [-3, -2, -1, 0, 1, 2, 3]"
                :key="buttonIndex" 
                @mouseover="hover = {questionIndex, buttonIndex}"
                @mouseleave="hover = false"
                @click=""
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
              <b>D'accord</b>
            </div>

          </div>

          <div class="row d-md-none d-flex justify-content-between w-100 text-center mt-2 buttonsWidth" >
                <div class="col-6 " :style="{color:'purple'}">
                  <b >En désaccord </b>
                </div>
                <div class="col-6 text-end" :style="{color:'blue'}">
                  <b>D'accord</b>
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