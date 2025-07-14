<script setup>
import { useRouter } from 'vue-router'
const idealistes = ['INFJ', 'INFP', 'ENFJ', 'ENFP']
const rationnels = ['INTJ', 'INTP', 'ENTJ', 'ENTP']
const gardiens = ['ISTJ', 'ISFJ', 'ESTJ', 'ESFJ']
const artisans = ['ISTP', 'ISFP', 'ESTP', 'ESFP']
const router = useRouter();

function pushType(type){
    router.push({name:'personality', params: {code:`${type}`} });
}
</script>

<template>
    <div class="container-fluid">
        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les idéalistes</b></div>
            <div v-for="type in idealistes" class="col-12 col-sm-3 text-center mb-2">
                <div class="cursor" @click="pushType(type)">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </div>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les rationnels</b></div>
            <div v-for="type in rationnels" class="col-12 col-sm-3 text-center mb-2">
                <div class="cursor" @click="pushType(type)">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </div>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les gardiens</b></div>
            <div v-for="type in gardiens" class="col-12 col-sm-3 text-center mb-2">
                <div class="cursor" @click="pushType(type)">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </div>
            </div>
        </div>

        <div class="row text-center mb-4">
            <div class="title mb-3"><b>Les artisans</b></div>
            <div v-for="type in artisans" class="col-12 col-sm-3 text-center mb-2">
                <div class="cursor" @click="pushType(type)">
                    <div><h5>{{type}}</h5></div>
                    <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${type}.webp`" alt="`${type} image`">
                    <div>description</div>
                </div>
            </div>
        </div>

    </div>

</template>

<style scope>
.img-fluid{
    width: 100%;
    min-width: 60px;
    object-fit: contain;
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
.cursor{
    cursor: pointer;
}

</style>