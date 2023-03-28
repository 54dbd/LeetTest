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
                    登录
                  </h4>
                  <div class="row mt-3">
                    <div class="col-2 text-center ms-auto">
                      <a class="btn btn-link px-3" href="javascript:;">
                        <i class="fa fa-facebook text-white text-lg"></i>
                      </a>
                    </div>
                    <div class="col-2 text-center px-1">
                      <a class="btn btn-link px-3" href="javascript:;">
                        <i class="fa fa-github text-white text-lg"></i>
                      </a>
                    </div>
                    <div class="col-2 text-center me-auto">
                      <a class="btn btn-link px-3" href="javascript:;">
                        <i class="fa fa-google text-white text-lg"></i>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card-body">
                <el-form
                  role="form"
                  class="text-start"
                  :model="loginForm"
                  ref="loginForm"
                >
                  <!--                  <el-form-item prop="username">-->
                  <MaterialInput
                    id="email"
                    class="input-group-outline my-3"
                    v-model="loginForm.username"
                    :label="{ text: '用户名', class: 'form-label' }"
                  />
                  <!--                  </el-form-item>-->
                  <!--                  <el-form-item prop="password">-->
                  <MaterialInput
                    id="password"
                    class="input-group-outline mb-3"
                    v-model="loginForm.password"
                    :label="{ text: '密码', class: 'form-label' }"
                    @keyup.enter="userLogin"
                    type="password"
                  />
                  <!--                  </el-form-item>-->
                  <MaterialSwitch
                    class="d-flex align-items-center mb-3"
                    id="rememberMe"
                    labelClass="mb-0 ms-3"
                    checked
                    >记住我</MaterialSwitch
                  >

                  <div class="text-center">
                    <MaterialButton
                      class="my-4 mb-2"
                      variant="gradient"
                      color="success"
                      fullWidth
                      @click.prevent="userLogin"
                      >登录</MaterialButton
                    >
                  </div>
                  <p class="mt-4 text-sm text-center">
                    还没有账号?
                    <a
                      href="/register"
                      class="text-success text-gradient font-weight-bold"
                      >注册</a
                    >
                  </p>
                </el-form>
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

<script>
import { useAppStore } from "@/stores";
export default {
  name: "Login",
  setup() {
    const piniaStore = useAppStore();
    console.log(piniaStore);
  },
  data() {
    // 自定义校验规则
    const validateUsername = (rule, value, callback) => {
      if (!/^\w{5,15}$/.test(value)) {
        callback(new Error("请检查用户名格式是否正确"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (!/^\w{5,17}$/.test(value)) {
        callback(new Error("密码只能6~15位包含数字、字母和下划线"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
      },

      rules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
    };
  },
  methods: {
    userLogin() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          try {
            const piniaStore = useAppStore();
            const result = await piniaStore.dispatch("user/login", {
              username: this.loginForm.username,
              password: this.loginForm.password,
            });
            if (result) {
              await this.$message({
                duration: 1500,
                message: "登录成功！",
                type: "success",
              });
              await this.$router.push("/");
              const toPath = this.$route.query.redirect || "/";
              await this.$router.push(toPath);
            } else {
              this.$message.error(result.data.msg);
              this.loginForm.username = "";
              this.loginForm.password = "";
            }
          } catch (e) {
            this.$message.error(e.message);
            this.loginForm.username = "";
            this.loginForm.password = "";
          }
        } else {
          this.$message.warning("请检查输入是否正确");
        }
      });
    },
  },
};
</script>
