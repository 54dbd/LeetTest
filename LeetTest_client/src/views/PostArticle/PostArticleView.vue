<script setup>
// example components
import DefaultNavbar from "../../ui/navbars/NavbarDefault.vue";
import Header from "../../ui/Header.vue";

// sections
import Footer from "./Sections/AuthorFooter.vue";

// image
import image from "@/assets/img/city-profile.jpg";
import PostMainBody from "@/views/PostArticle/Sections/PostMainBody.vue";
</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header min-height-200"
      :style="{ backgroundImage: `url(${image})` }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-8"></span>
    </div>
  </Header>
  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
    <PostMainBody />
  </div>
  <Footer />
</template>
