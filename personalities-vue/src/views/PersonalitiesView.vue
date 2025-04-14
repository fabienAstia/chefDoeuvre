<script setup>
import { useMbtiStore } from '@/stores/mbtiStore'
import axios from 'axios'
const idealistes = ['INFJ', 'INFP', 'ENFJ', 'ENFP']
const rationnels = ['INTJ', 'INTP', 'ENTJ', 'ENTP']
const gardiens = ['ISTJ', 'ISFJ', 'ESTJ', 'ESFJ']
const artisans = ['ISTP', 'ISFP', 'ESTP', 'ESFP']
const mbtiStore = useMbtiStore();

async function addType(type){
try{
    const response = await axios.get(`http://localhost:8080/mbti/${type}`)
    mbtiStore.setResult(response.data)
    //router.push({name:'result'});
}catch(err) {
    if(err.response){
        const statusCode = err.response.status;
        if(statusCode >= 400 && statusCode < 500){
          alert('A client error has occurred!')
        }else if(statusCode >= 500 && statusCode < 600){
          alert('A server error has occurred!')
        }
    }else{
        alert('an unexpected error has occured');
        console.error('an unexpected error has occured', err);
    }
  }
}
</script>

<template>
    <div class="container-fluid">
        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les idéalistes</b></div>
            <div v-for="type in idealistes" class="col-12 col-sm-3 text-center mb-2">
                <router-link to="/personality" @click="addType(type)">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </router-link>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les rationnels</b></div>
            <div v-for="type in rationnels" class="col-12 col-sm-3 text-center mb-2">
                <router-link to="/personality">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </router-link>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les gardiens</b></div>
            <div v-for="type in gardiens" class="col-12 col-sm-3 text-center mb-2">
                <router-link to="/personality">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </router-link>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les artisans</b></div>
            <div v-for="type in artisans" class="col-12 col-sm-3 text-center mb-2">
                <router-link to="/personality">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </router-link>
            </div>
        </div>

    </div>

</template>

<style scope>
.img-fluid{
    width: 100%;
    min-width: 60px;
}
.title{
    font-size: x-large;
    color: #0077b6;
    text-shadow:
        0px 1px 0px #270446,
        0px 2px 0px #270446,
        0px 3px 0px #270446,

        0px 5px 5px #270446;
}

</style>