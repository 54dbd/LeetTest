<script setup>
import { onMounted } from "vue";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

//Vue Material Kit 2 components
import MaterialInput from "@/components/MaterialInput.vue";
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
                <el-form
                  role="form"
                  class="text-start"
                  :rules="rules"
                  :model="register"
                  ref="registerForm"
                >
                  <el-form-item prop="username">
                    <MaterialInput
                      class="input-group-static"
                      label="用户名称"
                      type="text"
                      placeholder="请输入你的用户名"
                      v-model="register.username"
                    />
                  </el-form-item>
                  <div class="row">
                    <div class="col-8 text-center ms-auto">
                      <el-form-item prop="email">
                        <MaterialInput
                          class="input-group-static"
                          label="邮箱账号"
                          type="text"
                          placeholder="请输入你的邮箱账号"
                          v-model="register.email"
                        />
                      </el-form-item>
                    </div>
                    <div class="col-4 text-center mt-4">
                      <MaterialButton
                        variant="gradient"
                        color="success"
                        size="sm"
                        style="width: 100%"
                        type="button"
                        @click="sendEmailCode"
                        >发送验证码</MaterialButton
                      >
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-8 text-center ms-auto">
                      <el-form-item prop="emailCode">
                        <MaterialInput
                          class="input-group-static"
                          label="验证码"
                          type="text"
                          placeholder="请输入你的邮箱验证码"
                          v-model="register.emailCode"
                        />
                      </el-form-item>
                    </div>
                    <div class="col-4 text-center mt-4">
                      <MaterialButton
                        variant="gradient"
                        color="success"
                        size="sm"
                        style="width: 100%"
                        @click="checkEmailCode"
                        type="button"
                        >点击验证</MaterialButton
                      >
                    </div>
                  </div>
                  <el-form-item prop="password1">
                    <MaterialInput
                      class="input-group-static"
                      label="登陆密码"
                      type="password"
                      placeholder="请输入你的密码"
                      v-model="register.password1"
                    />
                  </el-form-item>
                  <el-form-item prop="password2">
                    <MaterialInput
                      class="input-group-static"
                      label="确认密码"
                      type="password"
                      placeholder="请输入你的密码"
                      v-model="register.password2"
                    />
                  </el-form-item>

                  <div class="text-center">
                    <MaterialButton
                      class="my-4"
                      variant="gradient"
                      color="success"
                      fullWidth
                      @click="userRegister"
                      type="button"
                      >完成注册</MaterialButton
                    >
                  </div>
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
import { reqGetCode, reqCheckCode } from "@/api";
import store from "@/stores";
import useValidate from "@vuelidate/core";
import { required } from "@vuelidate/validators";
import { ElMessage } from "element-plus";

export default {
  name: "Register",
  data() {
    return {
      v$: useValidate(),
      register: {
        //用户名
        username: "",
        //密码
        password1: "",
        //确认密码
        password2: "",
        // 用户邮箱
        email: "",
        // 邮箱验证码
        emailCode: "",
      },
      // 是否已经发送验证码
      isClickGetCode: false,
      // 是否显示验证成功图标
      isShowIcon: true,
      // 点击获取验证码后的倒计时
      codeNum: 60,
      eid: "",
    };
  },
  computed: {},
  methods: {
    async sendEmailCode() {
      if (
        /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(
          this.register.email
        )
      ) {
        this.isClickGetCode = true;
        let interval = setInterval(() => {
          if (this.codeNum > 0) {
            this.codeNum--;
          } else {
            clearInterval(interval);
            this.isClickGetCode = false;
          }
        }, 1000);
        this.$notify.warning({
          title: "提示",
          message: "验证码发送中...",
          duration: 8000,
        });
        try {
          const result = await reqGetCode(this.register.email);
          if (result.data.code === 200) {
            await this.$notify.success({
              message: "验证码成功发送，请注意查收！",
              title: "提示",
              duration: 5000,
            });
            this.eid = result.data.data.eid;
            // 不能再被点击
            this.isClickGetCode = true;
          } else {
            this.$message.error("验证码发送失败！");
          }
        } catch (e) {
          this.$message.error(e.message);
        }
      } else {
        this.$message.warning("请先输入正确的邮箱账号");
      }
    },
    async checkEmailCode() {
      if (!this.isClickGetCode) {
        this.$message.warning("请先发送验证码~");
        return;
      }
      if (!/^[A-Za-z0-9]{6}$/.test(this.register.emailCode)) {
        this.$message.warning("验证码格式有误，请重新输入");
        return;
      }
      try {
        // 先校验验证码是否通过
        const result = await reqCheckCode(
          this.register.emailCode.toUpperCase(),
          this.eid
        );
        if (result.data.code !== 200) {
          this.$message.error(result.data.msg);
        } else {
          this.$message.success("验证通过~");
          this.isShowIcon = false;
        }
      } catch (e) {
        this.$message.error(e.message);
      }
    },
    async userRegister() {
      const isValid = await this.v$.$validate();
      console.log(isValid);
      if (this.isShowIcon) {
        ElMessage.warning("请正确填写表单信息");
        return;
      }
      if (isValid) {
        try {
          const result = await store.dispatch("user/register", {
            username: this.register.username,
            password: this.register.password1,
            userEmail: this.register.email,
          });
          if (result) {
            this.$confirm("注册成功，是否立即登录", "", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "success",
            })
              .then(() => {
                this.$router.push("/login");
              })
              .catch(() => {
                this.$router.push("/home");
              });
          }
        } catch (e) {
          ElMessage.error(e.message);
        }
      } else {
        ElMessage.warning("请检查输入是否有误");
      }
    },
  },
  validations() {
    return {
      register: {
        //用户名
        username: { required },
        //密码
        password1: { required },
        //确认密码
        password2: { required },
        // 用户邮箱
        email: { required },
        // 邮箱验证码
        emailCode: { required },
      },
    };
  },
};
</script>
