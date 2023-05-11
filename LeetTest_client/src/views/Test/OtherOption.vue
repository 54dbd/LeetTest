<script setup>
const emit = defineEmits(["comment", "checkComment", "checkHistory"]);
import MaterialButton from "@/components/MaterialButton.vue";
const commentEmit = () => {
  emit("comment");
};
const checkCommentEmit = () => {
  emit("checkComment");
};
const checkHistoryEmit = () => {
  emit("checkHistory");
};
</script>

<template>
  <div class="dropdown" style="margin-top: 10px">
    <MaterialButton
      variant="gradient"
      color="success"
      class="dropdown-toggle"
      :class="{ show: showDropdown }"
      @focusout="showDropdown = false"
      id="dropdownMenuButton"
      data-bs-toggle="dropdown"
      :area-expanded="showDropdown"
      @click="showDropdown = !showDropdown"
    >
      更多功能
    </MaterialButton>
    <ul
      class="dropdown-menu px-2 py-3"
      :class="{ show: showDropdown }"
      aria-labelledby="dropdownMenuButton"
    >
      <li @click="commentEmit">
        <a class="dropdown-item border-radius-md" href="javascript:;"
          >发表心得</a
        >
      </li>
      <li @click="checkCommentEmit">
        <a class="dropdown-item border-radius-md" href="javascript:;"
          >查看心得</a
        >
      </li>
      <li @click="checkHistoryEmit">
        <a class="dropdown-item border-radius-md" href="javascript:;"
          >错题记录</a
        >
      </li>
    </ul>
  </div>
</template>

<style scoped></style>
