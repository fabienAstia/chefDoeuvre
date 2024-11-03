<script setup lang="ts">
import { RouterLink } from 'vue-router';
</script>

<template>
    <nav id="navbar" class="navbar navbar-expand-sm bg-body-tertiary" data-bs-theme="dark">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand p-0">
          <img id="logo" src="../assets/images/logo.JPG" alt="logo" class="header">
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
  
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <!-- <a class="nav-link active" aria-current="page" href="#">Tests</a> -->
              <router-link to="/test" class="nav-link active">Test</router-link>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Personnalités</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Articles</a>
            </li>
          </ul>
  
          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <!-- Flag icon can be inserted here if needed -->
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Action</a></li>
                <li><a class="dropdown-item" href="#">Another action</a></li>
                <li><a class="dropdown-item" href="#">Something else here</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
</template>
  
  <style scoped>
  .header {
    height: 60px;
  }
  #logo {
    max-width: 100%;
    max-height: 100%;
  }
  </style> 