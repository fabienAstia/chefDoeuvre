<script setup>
import arrowCircle from '@/assets/pictos/arrowCircle.svg';
</script>

<template>
    <div class="d-flex justify-content-center body">
        <div class="container g-3 m-3">

            <h1 class="text-center">« C’est formidable d’être enfin compris. »</h1>
            <p class="my-3 py-3 fs-4 text-center">
                Obtenez en 10 minutes seulement une description « incroyablement exacte » de qui vous êtes et des 
                raisons qui sous-tendent vos agissements.
            </p>

            <div class=" d-flex justify-content-center">
                <router-link to="/test" class="btn btn-success">
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