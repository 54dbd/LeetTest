<script setup>
import { onMounted } from "vue";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

//Vue Material Kit 2 components
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialSwitch from "@/components/MaterialSwitch.vue";
import MaterialButton from "@/components/MaterialButton.vue";

// material-input
import setMaterialInput from "@/assets/js/material-input";
onMounted(() => {
  setMaterialInput();
});
</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header align-items-start min-vh-100"
      :style="{
        backgroundImage:
          'url(https://images.unsplash.com/photo-1497294815431-9365093b7331?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80)',
      }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container my-auto">
        <div class="row">
          <div class="col-lg-4 col-md-8 col-12 mx-auto">
            <div class="card z-index-0 fadeIn3 fadeInBottom">
              <div
                class="card-header p-0 position-relative mt-n4 mx-3 z-index-2"
              >
                <div
                  class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1"
                >
                  <h4
                    class="text-white font-weight-bolder text-center mt-2 mb-0"
                  >
                    注册
                  </h4>
                </div>
              </div>
              <div class="card-body">
                <form role="form" class="text-start">
                  <MaterialInput
                    class="input-group-static mb-4"
                    label="用户名称"
                    type="text"
                    placeholder="请输入你的用户名"
                  />
                  <div class="row">
                    <div class="col-8 text-center ms-auto">
                      <MaterialInput
                        class="input-group-static mb-4"
                        label="邮箱账号"
                        type="text"
                        placeholder="请输入你的邮箱账号"
                      />
                    </div>
                    <div class="col-4 text-center mt-4">
                      <MaterialButton
                        variant="gradient"
                        color="success"
                        size="sm"
                        style="width: 100%"
                      >发送验证码</MaterialButton
                      >
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-8 text-center ms-auto">
                      <MaterialInput
                        class="input-group-static mb-4"
                        label="验证码"
                        type="text"
                        placeholder="请输入你的邮箱验证码"
                      />
                    </div>
                    <div class="col-4 text-center mt-4">
                      <MaterialButton
                        variant="gradient"
                        color="success"
                        size="sm"
                        style="width: 100%"
                        >点击验证</MaterialButton
                      >
                    </div>
                  </div>

                  <MaterialInput
                    class="input-group-static mb-4"
                    label="登陆密码"
                    type="text"
                    placeholder="请输入你的密码"
                  />
                  <MaterialInput
                    class="input-group-static mb-4"
                    label="确认密码"
                    type="text"
                    placeholder="请输入你的密码"
                  />

                  <div class="text-center">
                    <MaterialButton
                      class="my-4 mb-2"
                      variant="gradient"
                      color="success"
                      fullWidth
                      >完成注册</MaterialButton
                    >
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
      <footer class="footer position-absolute bottom-2 py-2 w-100">
        <div class="container">
          <div class="row align-items-center justify-content-lg-between">
            <div class="col-12 col-md-6 my-auto">
              <div
                class="copyright text-center text-sm text-white text-lg-start"
              >
                © {{ new Date().getFullYear() }}, made for a JavaEE project with
                <i class="fa fa-heart" aria-hidden="true"></i>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </div>
  </Header>
</template>
