<script setup>
const props = defineProps(["title"]);
</script>

<template>
  <div
    class="card d-flex blur justify-content-center shadow-lg my-sm-0 my-sm-6 mt-8 mb-5"
  >
    <div
      class="card-header p-0 position-relative mt-n4 mx-3 z-index-2 bg-transparent"
    >
      <div class="bg-gradient-success shadow-success border-radius-lg p-3">
        <h3 class="text-white text-success mb-0">{{ title }}</h3>
      </div>
      <div>
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
