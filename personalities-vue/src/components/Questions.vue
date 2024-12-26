<script setup>
import {ref} from 'vue';
import { useI18n } from 'vue-i18n';
import { useQuestions } from '@/components/useQuestions';
import bluePencil from '@/assets/bluePencil.svg';
import orangeArrows from '@/assets/orangeArrows.svg';
import redTrash from '@/assets/redTrash.svg';

const{t} = useI18n();

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


</script>


<template>
  <form @submit.prevent="addQuestion">
    
    <div class="row g-3 mb-3 align-items-end">
      <div class="col-12 col-sm-8 col-lg-10">
        <label>{{$t('admin.questionLabel')}}</label>
        <textarea class="form-control" v-model="question.text" placeholder="Ecrivez-une question"></textarea>
      </div>

      <div class="col-12 col-sm-2 col-lg-1">
        <select class="form-select create col" v-model="question.dimension" aria-placeholder="Dimension">
          <option value="E">E</option>
          <option value="I">I</option>
          <option value="N">N</option>
          <option value="S">S</option>
          <option value="T">T</option>
          <option value="F">F</option>
          <option value="P">P</option>
          <option value="J">J</option>
        </select>
      </div>

      <div class="col-12 col-sm-2 col-lg-1">
        <button class="btn btn-success" type="submit" :disabled="question.text==='' || question.dimension===''">{{$t('admin.creation')}}</button>
      </div>
    </div>
    
  </form>


  <table class="table table-hover">
    <thead class="table-dark">
      <tr data-bs-theme="dark">
        <th class="col-1" @click="loadQuestions">#</th>
        <th class="col-1"> 

          <div class="dropdown list-unstyled ">
            <a class="dropdown-item dropdown-toggle" data-bs-auto-close="ouside" data-bs-toggle="dropdown" role="button" aria-expanded="false">{{$t('admin.filter')}}</a>
            <ul class="dropdown-menu">

              <li class="dropdown-item dropdown-submenu">
                <a class="dropdown-item dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">Axe</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" @click="sortAxe('IE')">I/E</a></li>
                  <li><a class="dropdown-item" @click="sortAxe('NS')">N/S</a></li>
                  <li><a class="dropdown-item" @click="sortAxe('TF')">T/F</a></li>
                  <li><a class="dropdown-item" @click="sortAxe('PJ')">P/J</a></li>
                </ul>
              </li>

              <li class="dropdown-item dropdown-submenu">
                <a class="dropdown-item dropdown-toggle"  role="button" data-bs-toggle="dropdown" aria-expanded="false">Dimension</a>
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" @click="sortDim('I')">I</a></li>
                  <li><a class="dropdown-item" @click="sortDim('E')">E</a></li>
                  <li><a class="dropdown-item" @click="sortDim('N')">N</a></li>
                  <li><a class="dropdown-item" @click="sortDim('S')">S</a></li>
                  <li><a class="dropdown-item" @click="sortDim('T')">T</a></li>
                  <li><a class="dropdown-item" @click="sortDim('F')">F</a></li>
                  <li><a class="dropdown-item" @click="sortDim('P')">P</a></li>
                  <li><a class="dropdown-item" @click="sortDim('J')">J</a></li>
                </ul>
              </li>
            </ul>
          </div>
        </th>

        <th class="col-9">
          <div class="dropdown list-unstyled col-9">
              <a class="dropdown-item dropdown-toggle"  role="button" data-bs-toggle="dropdown" aria-expanded="false">{{$t('admin.dimension')}}</a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" @click="sortDim('I')">I</a></li>
                <li><a class="dropdown-item" @click="sortDim('E')">E</a></li>
                <li><a class="dropdown-item" @click="sortDim('N')">N</a></li>
                <li><a class="dropdown-item" @click="sortDim('S')">S</a></li>
                <li><a class="dropdown-item" @click="sortDim('T')">T</a></li>
                <li><a class="dropdown-item" @click="sortDim('F')">F</a></li>
                <li><a class="dropdown-item" @click="sortDim('P')">P</a></li>
                <li><a class="dropdown-item" @click="sortDim('J')">J</a></li>
              </ul>
            </div>
        </th>
        <th class="fw-normal col-1">{{$t('admin.operations')}}</th>
      </tr>
    </thead>
    
    <tbody>
      <tr v-for="(q, index) in questions" :key="q.id" >
        <td>{{index+1}} </td>
        <td><span v-if="!q.isEditable">{{q.dimension}}</span>
          <select v-else v-model="q.dimension" name="dimension">
            <option value="E">E</option>
            <option value="I">I</option>
            <option value="N">N</option>
            <option value="S">S</option>
            <option value="T">T</option>
            <option value="F">F</option>
            <option value="P">P</option>
            <option value="J">J</option>
          </select>
        </td> <!-- <input type="text" v-else  v-model="q.dimension" class="col-12"> -->
        <td><span v-if="!q.isEditable">{{q.text}}</span><input type="text" v-else  v-model="q.text" class="col-12"> </td>
        <td>
          <div class="d-flex">
            <button @click="editQuestion(q.id)" class="btn edit"><img :src="bluePencil" width="25px"></button> <!--mouseover--> 
            <button v-if="q.isEditable === true" @click="updateQuestion(q.id)" class="btn update"><img :src="orangeArrows" width="25px"></button>
            <button v-else @click="deleteQuestion(q.id)" class="btn delete"><img :src="redTrash" width="25px"></button>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</template>


<style scoped>
form{
  width: 100%;
}
.create{
  min-width: 10px;
  height: 60px;
}
button{
  height: auto;
  width: 100%;
  padding: 0;
}
.btn-success{
  height: 60px;
}
.edit:hover {
  background-color: rgb(169, 230, 250);
}
.delete:hover{
   background-color: lightpink;
}
.update:hover{
  background-color: lightgoldenrodyellow;
}
select{
  height: 30px;
  padding: 1px;
}

.dropdown-menu:hover > .dropdown-submenu{
  display: block;
}

</style>

 



