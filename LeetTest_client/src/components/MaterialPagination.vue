<script setup>
defineProps({
  color: {
    type: String,
    default: "success",
  },
  size: {
    type: String,
    default: "",
  },
});
function getClasses(color, size) {
  let colorValue, sizeValue;

  colorValue = color && `pagination-${color}`;
  sizeValue = size && `pagination-${size}`;

  return `${colorValue} ${sizeValue}`;
}
</script>
<template>
  <ul
    class="pagination pagination-primary m-4 mx-auto"
    :class="getClasses(color, size)"
  >
    <li class="page-item">
      <a class="page-link" href="javascript:;" aria-label="Previous">
        <span aria-hidden="true"
          ><i class="material-icons" aria-hidden="true">chevron_left</i></span
        >
      </a>
    </li>
    <li class="page-item active">
      <a class="page-link" href="javascript:;">1</a>
    </li>
    <li class="page-item">
      <a class="page-link" href="javascript:;">2</a>
    </li>
    <li class="page-item">
      <a class="page-link" href="javascript:;">3</a>
    </li>
    <li class="page-item">
      <a class="page-link" href="javascript:;">4</a>
    </li>
    <li class="page-item">
      <a class="page-link" href="javascript:;">5</a>
    </li>
    <li class="page-item">
      <a class="page-link" href="javascript:;" aria-label="Next">
        <span aria-hidden="true"
          ><i class="material-icons" aria-hidden="true">chevron_right</i></span
        >
      </a>
    </li>
  </ul>
</template>
