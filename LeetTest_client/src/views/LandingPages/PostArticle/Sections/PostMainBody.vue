<script setup>
import { onMounted } from "vue";

//Vue Material Kit 2 components
import MaterialAvatar from "@/components/MaterialAvatar.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import MaterialInput from "@/components/MaterialInput.vue";

// image
import profilePic from "@/assets/img/bruce-mars.jpg";

// material-input
import setMaterialInput from "@/assets/js/material-input";

onMounted(() => {
  setMaterialInput();
});
</script>
<template>
  <section class="py-sm-7 py-5 position-relative">
    <div class="container">
      <div class="row">
        <div class="col-12 mx-auto">
          <div class="mt-n8 mt-md-n9 text-center">
            <div class="blur-shadow-avatar">
              <MaterialAvatar
                size="xxl"
                class="shadow-xl position-relative z-index-2"
                :image="profilePic"
                alt="Avatar"
              />
            </div>
          </div>
          <div class="d-flex justify-content-between align-items-center mb-2">
            <div class="col-md-10 ps-md-2">
              <MaterialInput
                class="input-group-static mb-4"
                type="text"
                label="文章标题"
                placeholder="请输入内容"
              />
            </div>
            <MaterialButton variant="gradient" color="success" class="mt-3 mb-0"
              >发布文章</MaterialButton
            >
          </div>
          <div>
            <mavon-editor
              v-model="mdText"
              class="me-editor"
              ref="md"
              @imgAdd="$imgAdd"
            ></mavon-editor>
          </div>
        </div>
      </div>
    </div>
    <!--    <MaterialButton-->
    <!--      variant="gradient"-->
    <!--      color="success"-->
    <!--      data-bs-toggle="modal"-->
    <!--      data-bs-target="#exampleModal"-->
    <!--    >-->
    <!--      Launch demo modal-->
    <!--    </MaterialButton>-->
    <!--    &lt;!&ndash; Modal &ndash;&gt;-->
    <!--    <div-->
    <!--      class="modal fade"-->
    <!--      id="exampleModal"-->
    <!--      tabindex="-1"-->
    <!--      aria-labelledby="exampleModalLabel"-->
    <!--      aria-hidden="true"-->
    <!--    >-->
    <!--      <div class="modal-dialog">-->
    <!--        <div class="modal-content">-->
    <!--          <div class="modal-header">-->
    <!--            <h5 class="modal-title" id="exampleModalLabel">Your modal title</h5>-->
    <!--            <MaterialButton-->
    <!--              color="none"-->
    <!--              class="btn-close"-->
    <!--              data-bs-dismiss="modal"-->
    <!--              aria-label="Close"-->
    <!--            >-->
    <!--            </MaterialButton>-->
    <!--          </div>-->
    <!--          <div class="modal-body">-->
    <!--            Society has put up so many boundaries, so many limitations on what’s-->
    <!--            right and wrong that it’s almost impossible to get a pure thought-->
    <!--            out.-->
    <!--            <br /><br />-->
    <!--            It’s like a little kid, a little boy, looking at colors, and no one-->
    <!--            told him what colors are good, before somebody tells you you-->
    <!--            shouldn’t like pink because that’s for girls, or you’d instantly-->
    <!--            become a gay two-year-old.-->
    <!--          </div>-->
    <!--          <div class="modal-footer justify-content-between">-->
    <!--            <MaterialButton-->
    <!--              variant="gradient"-->
    <!--              color="dark"-->
    <!--              data-bs-dismiss="modal"-->
    <!--            >-->
    <!--              Close-->
    <!--            </MaterialButton>-->
    <!--            <MaterialButton variant="gradient" color="success" class="mb-0">-->
    <!--              Save changes-->
    <!--            </MaterialButton>-->
    <!--          </div>-->
    <!--        </div>-->
    <!--      </div>-->
    <!--    </div>-->
  </section>
</template>
