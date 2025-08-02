<script setup>
import arrowCircle from '@/assets/pictos/arrowCircle.svg';
import home_background from '@/assets/images/home_img.png';
import { onMounted, onUnmounted } from 'vue'

onMounted(() => {
    document.body.style.backgroundImage = `url(${home_background})`;
    document.body.style.backgroundPosition = 'center';
    document.body.style.backgroundSize = 'cover'
    document.body.style.backgroundRepeat = 'no-repeat';
    document.body.style.height = '100vh';
    document.body.style.margin = '0';
    document.body.style.overflow = 'hidden';
})
onUnmounted(() =>{
    document.body.removeAttribute('style');
})
</script>


<template>
    <div class="fullscreen-wrapper">
        <div class="d-flex justify-content-center container">
            <div class=" g-3 p-3 text-wrapper">
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
    </div>
</template>

<style scoped> 
.text-wrapper {
  background-color: rgba(0, 0, 0, 0.5); /* noir transparent */
  color: white;
  padding: 2rem;
  border-radius: 1rem;
}
.container{
    max-width: 980px;
    backdrop-filter: blur(10px);
}

</style>

