<script setup>
import { useRoute } from 'vue-router';
import { onMounted, ref, computed } from 'vue';
import axios from 'axios'

const route = useRoute();
const code = route.params.code;
const mbtiType = ref({})

onMounted(async() => {
    getMbtiType()
    sortedTraits()
    // getSpecificJobs();
    // truncateText();
})

async function getMbtiType(){
    try{
        const response = await axios.get(`http://localhost:8080/mbti/${code}`)
        console.log('name2', response.data)

        mbtiType.value = response.data;
        console.log('strength', mbtiType.value.strengthAndWeaknesses)
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

const image = computed(() => {
  return new URL(`/src/assets/images/mbtiTypes/${mbtiType.value.image}`, import.meta.url).href;
});

const sortedTraits = computed(() => {
    const traits = {force: [], faiblesse: []};
    for(const[trait,evaluation] of Object.entries(mbtiType.value.strengthAndWeaknesses)){
        if(evaluation === "Force"){
            traits.force.push(trait);
        }else {
            traits.faiblesse.push(trait);
        }
    }
    console.log("traits =" , traits)
    return traits;
});

//pôle Emploi


const specificJobs = ref({});
const specificJobs2 = ref({intitule:'', description:'', lieuTravail:'', typeContrat:'', entreprise:'', salaire:''})
const keyWords=ref('Développeur')

function truncateText(){
    const text = document.getElementById("truncate").innerHTML;
    const truncated = text.substring(0, 50) + "...";
    document.getElementById("truncate").innerHTML = truncated;
}

const getSpecificJobs = async() => {
    try {
        const response = await axios.get(`http://localhost:8080/jobs/specific?motsCles=${keyWords.value}`) 
        specificJobs.value = response.data.resultats[0];

        specificJobs2.value.intitule = response.data.resultats[0].intitule;
        specificJobs2.value.description = response.data.resultats[0].description;
        specificJobs2.value.lieuTravail = response.data.resultats[0].lieuTravail;
        specificJobs2.value.typeContrat = response.data.resultats[0].typeContrat;
        specificJobs2.value.horaires = response.data.resultats[0].contexteTravail.horaires[0];
        specificJobs2.value.entreprise = response.data.resultats[0].entreprise.nom;
        specificJobs2.value.salaire = response.data.resultats[0].salaire.libelle;


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


</script>

<template>
    <div class="container">
        <div class="container-fluid">
            <div class="row mb-3">
                <div class="col text-center">
                    <h1 class="text-shadow mb-5"><b>{{ mbtiType.code }}</b></h1>
                </div>
            </div>
            <div class="row align-items-center my-3 style">
                <h3 class="text-center text-shadow-light">Présentation</h3>
                <div class="col-12 col-md-6 justify-content-center fs-6 fs-md-5" >
                    <div class="text-center text-shadow-light w-100 mb-3"><b>{{ mbtiType.code }} - {{ mbtiType.name }}</b></div>
                    <ul class="fs-6 fs-md-5" style="list-style-type: '▸';">
                        <li class="my-3 ms-3">{{ mbtiType.description }}</li>
                        <li class="my-3 ms-3">{{ mbtiType.populationPercentage }}% of the population</li>
                        <li class="my-3 ms-3">{{ mbtiType.interestingFact }}</li>
                    </ul>
                </div>
                <div class="d-flex col-12 col-md-6 justify-content-center">
                    <img class="img-fluid" :src="image" alt="mbtiType image" id="image">
                </div>
            </div>

            <!-- <div class="row text-center mt-5 style">
                <h3 class="text-shadow-light">Forces et Faiblesses</h3>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Forces</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.force" :key="trait">{{ trait }}</div>
                </div>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Faiblesses</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.faiblesse" :key="trait">{{ trait }}</div>
                </div>
            </div> -->

            <div class="row mt-5 style">
                <h3 class="text-center text-shadow-light">Métiers</h3>
                <div class="col">
                    <div class="text-center mb-1" v-for="job in mbtiType.professions">{{ job }}</div>
                </div>
            </div>

            <div>
                <input type="text" v-model="keyWords">
                <div>{{ keyWords }}</div>
                <div><p>specific</p>{{ specificJobs }}</div>
                <hr>
                <div>🏢{{ specificJobs2.entreprise }} 📍{{ specificJobs2.lieuTravail }}</div> 
                <div>📌{{ specificJobs2.intitule }} {{ specificJobs2.typeContrat }}</div> 
                <div>🕒{{ specificJobs2.horaires }} - {{ specificJobs2.salaire }}</div>
                <div id="truncate">📝{{ specificJobs2.description }}</div> 
                <button @click="truncateText()"></button>
                <div></div> 


            </div>

        </div>
    </div>
</template>

<style>
html{
    background-color: #effcfe;
}
.container{
    max-width: 900px!important; 
    padding: 15px;
}
ul{
    padding: 0;
}
h1{
    font-size: 4em;
    color: #0077b6;
}
h3{
    margin-bottom: 25px;
    padding-top: 20px;
    padding-bottom: 20px;
    border-bottom: 2px solid #0079bb21;
    background-color: white;
    border: 2px solid white;
}
.style{
    background-color: #effcfe;
    border: 2px solid #0079bb21;
}
.text-shadow{
    text-shadow:
        0px 1px 0px #270446,
        0px 2px 0px #270446,
        0px 3px 0px #270446,
        0px 4px 0px #270446,
        0px 5px 0px #270446,
        0px 6px 0px #270446,
        0px 7px 0px #270446,
        0px 8px 0px #270446,
        0px 9px 0px #270446,
        0px 10px 0px #270446,

        0px 10px 5px #270446;
}
.text-shadow-light{
    color: #0077b6;
    text-shadow:
        0px 1px 0px #270446,
        0px 2px 0px #270446,
        0px 3px 0px #270446,

        0px 10px 5px #270446;
}
.img-fluid{
    width: 80%;
    height: auto;
    max-height: 300px;
    max-width: 300px;
    min-height: 100px;
    min-width: 100px;
}
#myChart{
    max-width: 350px;
    max-height: 300px;
    min-height: 100px;
}
.progress-bar{
    background-color: #0077b6;
    border-color: #00344f;
}
.progress{
    background-color:#4e038f;
    border-color: #270446;
}
.no-bullet{
    list-style: none;
}
/* #text{

} */

</style>