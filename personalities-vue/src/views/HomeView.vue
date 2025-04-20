<script setup>
import arrowCircle from '@/assets/pictos/arrowCircle.svg';



import { onMounted, ref } from 'vue';
import axios from 'axios'

onMounted(() => {
    getJobs();
})
const jobs = ref({});
const specificJobs = ref({});

const keyWords=ref('Développeur')

const getSpecificJobs = async() => {
    try {
        const response = await axios.get(`http://localhost:8080/jobs/specific?motsCles=${keyWords.value}`) 
        specificJobs.value = response.data.resultats[0].id;
        console.log('specificJobs:'+specificJobs.value)
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
const getJobs = async() => {
    try {
        const response = await axios.get('http://localhost:8080/jobs') 
        jobs.value = response.data.resultats[0];
        console.log('jobs:'+jobs.value)
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
getSpecificJobs();

</script>


<template>
    <div>
        <input type="text" v-model="keyWords">
        <div>{{ keyWords }}</div>
        <div><p>specific</p>{{ specificJobs }}</div>
        <div>{{ jobs }}</div> 
        
    </div>

    <div class="d-flex justify-content-center body">
        <div class="container g-3 m-3">

            <h1 class="text-center">« C’est formidable d’être enfin compris. »</h1>
            <p class="my-3 py-3 fs-4 text-center">
                Obtenez en 10 minutes seulement une description « incroyablement exacte » de qui vous êtes et des 
                raisons qui sous-tendent vos agissements.
            </p>

            <div class=" d-flex justify-content-center">
                <router-link to="/answers" class="btn btn-success">
                    <span class="d-flex gap-2 fs-5">
                        Passer le test 
                        <img :src="arrowCircle">
                    </span>
                </router-link>
            </div>
        </div>
    </div>

</template>

<style scoped>
    .container{
        max-width: 800px;
    }
</style>

