<script setup>
import { useRoute } from 'vue-router';
import { onMounted, ref, computed, watch, useTemplateRef } from 'vue';
import { useI18n } from 'vue-i18n';
import axios from 'axios'
import { formatAlert } from '@/composables/useMessageFormatter';
import AlertModal from '@/components/Alert.vue'

const {t} = useI18n();
const route = useRoute();
const code = route.params.code;
const mbtiType = ref({})
const specificJobs = ref({});
const allCoordinates = ref({})
const keyWords=ref('')
const isTruncated = ref({})
const addressesChunk = ref ([])
const modal = useTemplateRef('modal')
const pageNumber = ref(0);
const pageSize = ref(2);  
// const totalPages = ref(0);
// const totalElements = ref(0);
const metadata = ref({})

const showMessage = (msg) => {
    modal.value.openModal()
    modal.value.alertTxt = formatAlert(msg).message
}

onMounted(async() => {
    getMbtiType()
})

async function getMbtiType(){
    try{
        const response = await axios.get(`http://localhost:8080/mbti/${code}`)
        mbtiType.value = response.data;
    }catch(err) {
      showMessage(err)
    }
}

const image = computed(() => {
  return new URL(`/src/assets/images/mbtiTypes/${mbtiType.value.image}`, import.meta.url).href;
});

const sortedTraits = computed(() => {
    const traits = {force: [], faiblesse: []};
    if(!mbtiType.value.strengthAndWeaknesses) return traits;
    for(const[trait,evaluation] of Object.entries(mbtiType.value.strengthAndWeaknesses)){
        if(evaluation === "Force"){
            traits.force.push(trait);
        }else {
            traits.faiblesse.push(trait);
        }
    }
    return traits;
});

const getSpecificJobs = async() => {
    try {
        const response = await axios.get(`http://localhost:8080/jobs/paginated?motsCles=${keyWords.value}&page=${pageNumber.value}&size=${pageSize.value}`) 
        specificJobs.value = response.data.data.paginatedOfferJobViews
        allCoordinates.value = response.data.data.allCoordinates

        specificJobs.value.forEach((offerJob, index) => {
            isTruncated.value[index] = true;
        })

        metadata.value = response.data.metadata;

        console.log("specificJobs", specificJobs.value)
        console.log("allCoordinates", allCoordinates.value)

    }catch(err) {
       showMessage(err)
    }
}

const getNextPage = async() => {
    try {
        pageNumber.value++
        getSpecificJobs();
        // const response = await axios.get(`http://localhost:8080/jobs/paginated?motsCles=${keyWords.value}&page=${0}&size=${2}`) 
        // specificJobs.value = response.data.data.paginatedOfferJobViews
        // allCoordinates.value = response.data.data.allCoordinates

        // specificJobs.value.forEach((offerJob, index) => {
        //     isTruncated.value[index] = true;
        // })

        // metadata.value = response.data.metadata;

        // console.log("specificJobs", specificJobs.value)
        // console.log("allCoordinates", allCoordinates.value)

    }catch(err) {
       showMessage(err)
    }
}

const getPreviousPage = async() => {
    try {
        pageNumber.value--
        getSpecificJobs;
    } catch(err) {
        showMessage(err)
    }
}

watch(
    keyWords,
    () => {
        getSpecificJobs()
    }
)
watch(
     allCoordinates,
    () => {
        getAddress()
    }
)
watch(
     addressesChunk,
    () => {
    console.log("addressesChunk", addressesChunk.value);
        formatAddress();
    }
)

const getAddress = async() => {
    try {
        const response = await axios.post(`http://localhost:8080/address`,
            allCoordinates.value.slice(0, 20),
            {headers:{'Content-Type':'application/json'}}); 
        
        addressesChunk.value = response.data
    }catch(err){
        showMessage(err)
    }
}

function formatAddress(offerJobIndex) {
    const a = addressesChunk.value
    //    console.log('adrressesChunk', a)
    if(a){
        for(let i = 0; i < a.length; i++){
            if(i === offerJobIndex){
                console.log('Address', a[i].formatted)
                return a[i].formatted;
            }
        }
    }
}

// const formatAddress = computed(() => {
//     const a = address.value
//     return `<small> ${a.house_number ?? ''}  ${a.road ?? ''} <br> 
//         ${a.suburb ?? ''} ${a.city ?? ''}, ${a.country ?? ''} ${a.postcode ?? ''}</small>`
// })

function untruncate(index) {
    isTruncated.value[index] = !isTruncated.value[index]
    return specificJobs.value[index].description;
}

function formatSalaire(index) {
    let salaire = specificJobs.value[index].salary
    console.log('salaire', salaire)
    if(salaire){
        const regex = /\d+\.\d+/g
        const matches = salaire.match(regex)

        matches.forEach(m => {
            salaire = salaire.replace(m, Math.floor(parseFloat(m)))
        })
        return salaire
    }
    return ''
}   

const displayOffers = (job) => {
    keyWords.value = job;
}

</script>

<template>
  <AlertModal ref="modal"/>
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

            <div class="row text-center mt-5 style">
                <h3 class="text-shadow-light">Forces et Faiblesses</h3>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Forces</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.force" :key="trait">{{ trait }}</div>
                </div>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Faiblesses</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.faiblesse" :key="trait">{{ trait }}</div>
                </div>
            </div>

            <div class="row mt-5 style">
                <h3 class="text-center text-shadow-light">Métiers</h3>
                <div class="col-12 col-md-6">
                    <div class="text-center mb-1" v-for="job in mbtiType.professions" @click="displayOffers(job)" id="pointer">{{ job }}</div>
                </div>
                <div class="offersJob col-12 col-md-6" v-for="(offerJob, index) in specificJobs" :key="index">
                    <!-- <input type="text" v-model="keyWords">
                    <div>{{ keyWords }}</div> -->
            
                    <div><b>{{ offerJob.title }}</b> - {{ offerJob.contractType }}</div> 
                    <div>{{ offerJob.companyName }}</div> 
                    <!-- <div>INDEX :{{ index }}</div> -->
                    <div v-html="formatAddress(index)"></div> 
                    <div>{{ offerJob.workingHours }} </div>
                    <!-- <div>{{ formatSalaire }}</div> -->
                    <div v-html="formatSalaire(index)"></div>
                    <div>{{ offerJob.experience }}</div>
                    <div>{{ offerJob.sourceUrl }}</div>
                    <div v-if="isTruncated[index]">{{offerJob.description.substring(0, 60) + '...'}}</div> 
                    <div v-else>{{offerJob.description}}</div> 

                    <button @click="untruncate(index)" v-if="isTruncated[index] === true">read more</button>
                    <button @click="untruncate(index)" v-else>read less</button>

                    <div class="d-flex justify-content-around bg-light fs-2">
                        <button v-if="pageNumber>0" class="btn btn-outline-primary btn m-1" @click="getPreviousPage()">Previous page</button>
                        <button v-if="pageNumber<(metadata.totalPages-1)" class="btn btn-outline-primary btn m-1" @click="getNextPage()">Next page</button>
                    </div>
        
                </div>
            </div>

            <div>
                <hr>
                <div><p>specific</p>{{ specificJobs }}</div>
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
#pointer{
    cursor: pointer;
}
#pointer:hover{
    font-weight:800;
    background-color: #0077b6;
    color: #effcfe;
}

</style>