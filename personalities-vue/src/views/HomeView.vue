<script setup>
import arrowCircle from '@/assets/pictos/arrowCircle.svg';
import image_background from '@/assets/images/home_img.png';
</script>


<template>

    <div class="d-flex justify-content-center body">
        <!-- <div class="container g-3 m-3" :style="{backgroundImage : `url(${image_background})`}"> -->
        <div class="container g-3 m-3">

            <h1 class="text-center">« Mieux vous comprendre, c’est déjà avancer. »</h1>
            <p class="my-3 pt-3 fs-4 text-center">
                En 10 minutes chrono, découvrez un aperçu précis de votre personnalité
                et des raisons qui sous-tendent vos agissements.
            </p>
            <p class="my-3 pb-3 fs-4 text-center">
                C’est la première application qui connecte votre type MBTI à des opportunités professionnelles faites pour vous.
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

