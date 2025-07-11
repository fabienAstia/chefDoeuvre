<script setup>
import {ref, onMounted } from 'vue';

const {offerJob, address} = defineProps({
  offerJob: {
    type: Object
  },
  address: {
    type: String
  }
});

onMounted(() => {
    formatSalary(offerJob.salary)
    console.log('address', address)
})

const salary = ref('');
const formatSalary = (input) => {
    if(input){
        const regex = /\d+\.\d+/g
        const matches = input.match(regex)
        if(matches) {
            matches.forEach(m => {
                input = input.replace(m, Math.floor(parseFloat(m)))
            })
        }
        salary.value = input
    }
    return salary.value
}   

const isTruncated = ref(true)
function untruncate(){
    isTruncated.value = !isTruncated.value
}

</script>

<template>
    <div class="mb-1">
        <div><b>{{ offerJob.title }}</b> - {{ offerJob.contractType }}</div> 
        <div>{{ offerJob.companyName }}</div> 
        <div>{{ address }}</div>
        <div>{{ offerJob.workingHours }} </div>
        <div>{{ salary }}</div>
        <div>{{ offerJob.experience }}</div>
        <div>{{ offerJob.sourceUrl }}</div>
        <div v-if="isTruncated" class="d-inline">
            {{offerJob.description.substring(0, 60) + '...'}}
            <button @click="untruncate()" v-if="isTruncated === true" class="btn btn-outline-primary btn-sm p-0 m-1">
                more
            </button>
        </div> 
        <div v-else>
            {{offerJob.description}}
            <button @click="untruncate()" class="btn btn-outline-primary btn-sm p-0 m-1">
                less
            </button>
        </div> 
    </div>

</template>

<style scoped>
.inline{
    display:flex;
}
</style>

