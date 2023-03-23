<script setup>
defineProps({
  image: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  action: {
    type: Array,
    route: String,
    label: String,
    color: String,
    required: true,
  },
  links: {
    type: Array,
    required: false,
  },
});
</script>
<template>
  <div
    class="back back-background"
    :style="{
      backgroundImage: `url(${image})`,
      backgroundSize: 'cover',
    }"
  >
    <!--    正常内容-->
    <div v-if="links == null" class="card-body col-12">
      <div>
        <h3 class="text-white" v-html="title"></h3>
        <p class="text-white opacity-8">
          {{ description }}
        </p>
      </div>

      <div class="buttons-group">
        <a
          v-for="({ route, color, label }, index) of action"
          :key="index"
          :href="route"
          target="_blank"
          class="btn btn-sm mt-3 inline-block ms-1"
          :class="`${color ? `btn-${color}` : 'btn-white'}`"
          >{{ label }}</a
        >
      </div>
    </div>
    <!--    链接列表-->
    <div
      v-else
      class="card-body col-12 scrollable"
      style="margin-left: -5%; overflow: hidden"
    >
      <div>
        <ul style="text-align: left; padding-top: 10%">
          <li v-for="item in links" :key="item.id" class="text-view icon-move-right">
            <i class="fas fa-star" style="color: #f1cd1e;"></i>
            <router-link
              style="font-size: 15px; color: white"
              :to="{ path: '/article', query: { id: item.id } }"
              >{{ item.title }}</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style>
.text-view {
  display: inline-block;
  white-space: nowrap;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
}
.scrollable {
  width: 100%;
  height: 200px;
  overflow: auto;
}

.scrollable::-webkit-scrollbar {
  width: 0.5em;
  padding: 50%;
}

.scrollable::-webkit-scrollbar-track {
  background-color: rgba(0, 0, 0, 0.05);
  opacity: 20%;
}

.scrollable::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.1);
  opacity: 20%;
}
</style>
