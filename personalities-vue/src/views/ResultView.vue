<script setup>
    import { useRoute } from 'vue-router';

    const route = useRoute();
    const mbtiType = JSON.parse(route.query.mbtiType);     

</script>


<template>
        <h1 class="text-center"><b class="style">{{ mbtiType.code }}</b></h1>
        <div class="d-flex justify-content-center">
            <img :src="`http://localhost:8080${mbtiType.image}`" class="mb-4" alt="mbtiType image">
        </div>
        <p class="text-center">{{ mbtiType.description }}</p>
</template>