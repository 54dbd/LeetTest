<script setup>
defineProps({
  links: {
    type: Array,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  subtitle: {
    type: String,
    required: true,
  },
  url: {
    type: String,
    required: true,
  },
  pagesize: {
    type: Boolean,
    default: true,
  },
});
</script>
<template>
  <div class="container mt-sm-5">
    <div
      class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
      :style="{
        backgroundImage: 'url(' + url + ')',
      }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark"></span>
      <div class="container">
        <div class="row">
          <div class="col-lg-6 ms-lg-5">
            <h4 class="text-white">{{ subtitle }}</h4>
            <h1 class="text-white">{{ title }}</h1>
            <ul style="text-align: left" id="myList">
              <li
                v-for="item in displayedLinks"
                :key="item.id"
                class="text-view"
              >
                <div class="icon-move-right">
                  <router-link
                    v-if="pagesize === false"
                    style="font-size: 15px; color: white"
                    :to="{ path: '/article', query: { id: item.articleId } }"
                    >{{ item.title }}</router-link
                  >
                  <router-link
                    v-else
                    style="font-size: 15px; color: white"
                    :to="{ path: '/article', query: { id: item.id } }"
                    >{{ item.title }}</router-link
                  >
                  <i class="fas fa-arrow-right text-sm ms-1"></i>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  computed: {
    displayedLinks() {
      if (this.pagesize === false) return this.links;
      else return this.links.slice(0, 10);
    },
  },
};
</script>
