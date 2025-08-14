<script>

</script>

<template>
<nav id="footer" class="navbar nav navbar-expand-sm" >
      <div class="container-fluid justify-content-center ">
        <span class="p-1">© 2025 Personalities </span>
        <router-link to="/legalNotices" class="p-1" :aria-label="$t('accessibility_header.home')">
          <span> Mentions légales</span>
        </router-link>
        <router-link to="/privacyPolicy" class="p-1" :aria-label="$t('accessibility_header.home')">
          <span> Politiques de confidentialité</span>
        </router-link>
  
      </div>
    </nav>
</template>

<style scoped>
#footer{
    height: 50px;
    bottom: 0;
    z-index: 1250; 
    background-color: #addee4;
}
</style>