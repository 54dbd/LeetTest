<script setup>
import wavesWhite from "@/assets/img/waves-white.svg";
</script>
<template>
  <div>
    <el-row :gutter="20">
      <el-col class="elCol1" :span="16">
        <el-card shadow="always">
          <div v-if="question">
            <MarkDown
              :text="question"
              class="content"
              v-if="qtype !== '图片题'"
            />
            <el-image :src="question" fit="fill" v-else></el-image>
          </div>
        </el-card>
        <el-card class="choices" v-if="atype === 1">
          <el-checkbox-group v-model="choice" v-if="qchoicea != null">
            <el-checkbox label="A" class="choice">{{ qchoicea }}</el-checkbox>
            <el-checkbox label="B" class="choice">{{ qchoiceb }}</el-checkbox>
            <el-checkbox label="C" class="choice">{{ qchoicec }}</el-checkbox>
            <el-checkbox label="D" class="choice">{{ qchoiced }}</el-checkbox>
          </el-checkbox-group>
          <el-checkbox-group v-model="choice" v-else>
            <el-checkbox label="A" class="choice">选项A</el-checkbox>
            <el-checkbox label="B" class="choice">选项B</el-checkbox>
            <el-checkbox label="C" class="choice">选项C</el-checkbox>
            <el-checkbox label="D" class="choice">选项D</el-checkbox>
          </el-checkbox-group>
        </el-card>

        <div v-if="atype === 0" style="margin: 10px 0">
          <el-card class="input-group-static mb-4">
            <textarea
              name="message"
              class="form-control"
              id="message"
              :rows="4"
              v-model="textarea"
            />
          </el-card>
        </div>

        <el-row class="changePage">
          <router-link
            @click="flush(lastPage, false)"
            :to="{
              path: '/test',
              query: { tid: lastPage, corrected: corrected },
            }"
          >
            <MaterialButton
              variant="gradient"
              color="success"
              size="lg"
              style="width: 100%"
              type="button"
              >上一题
            </MaterialButton>
          </router-link>

          <router-link
            @click="flush(nextPage, true)"
            :to="{
              path: '/test',
              query: { tid: nextPage, corrected: corrected },
            }"
          >
            <MaterialButton
              variant="gradient"
              color="success"
              size="lg"
              style="width: 100%"
              type="button"
              >下一题
            </MaterialButton>
          </router-link>
        </el-row>
        <!--        用来把3d模型的位置固定在这里-->
        <div ref="target">
          <div ref="positioned" id="three" @click="touch"></div>
          <el-row
            class="analysis"
            :class="qtype === '简述题' ? '' : 'disappear'"
          >
            <div style="width: 100%">
              <div
                class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
                :style="{
                  backgroundImage: `url(${wavesWhite})`,
                }"
                loading="lazy"
              >
                <span class="mask bg-gradient-dark"></span>
                <div class="container">
                  <h2 style="color: white">
                    {{ openaiAnswer ? "分析" : "智能解题小助手" }}
                  </h2>
                  <MarkDown
                    :text="
                      waiting
                        ? '正在努力分析中...'
                        : openaiAnswer
                        ? openaiAnswer
                        : hint
                    "
                    class="content"
                    style="color: ghostwhite"
                    v-if="qtype !== '图片题' && openaiAnswer != null"
                  />
                </div>
              </div>
            </div>

            <el-card
              class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
              v-if="done"
            >
              <h2>解析:</h2>
              <div v-if="analysis">
                <MarkDown
                  :text="analysis"
                  class="content"
                  v-show="qtype !== '图片题'"
                />
                <el-image
                  :src="analysis"
                  fit="fill"
                  v-show="qtype === '图片题'"
                ></el-image>
              </div>
              <div v-else>
                {{ answer }}
              </div>
            </el-card>
          </el-row>
        </div>
      </el-col>
      <el-col class="elCol2" :span="6">
        <el-card shadow="always">
          <el-descriptions
            title="题目信息"
            direction="vertical"
            :column="1"
            size="medium"
          >
            <el-descriptions-item label="正确率"
              ><span style="color: #77b72c">{{
                accuracy + "%"
              }}</span></el-descriptions-item
            >
            <el-descriptions-item label="正确人数"
              ><span style="color: #77b72c">{{ correctnum }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="做题人数"
              ><a
                rel="nofollow"
                target="_blank"
                style="color: #4cb9fc; text-decoration: none"
                >{{ num }}</a
              >
            </el-descriptions-item>
            <el-descriptions-item v-if="points != null" label="知识点"
              ><a
                rel="nofollow"
                target="_blank"
                style="color: #4cb9fc; text-decoration: none"
                >{{ points }}</a
              >
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
        <div
          style="
            width: 80%;
            height: 60px;
            display: flex;
            justify-content: center;
            margin: 5px auto;
          "
        >
          <MaterialButton
            variant="gradient"
            color="success"
            size="sm"
            class="submit"
            type="button"
            @click="atype === 1 ? submitChoice() : submitText()"
            >提交
          </MaterialButton>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MarkDown from "@/components/MarkDown/MarkDown.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import * as api from "@/api";
import MaterialTextArea from "@/components/MaterialTextArea.vue";
import { Configuration, OpenAIApi } from "openai";
import * as THREE from "three";
var rotateSpeed = 0.01;
var coreColor = 0xffeb57;
// import { ref } from "vue";
// const target = ref(null);
// const positioned = ref(null);

export default {
  name: "index",
  props: ["testIntroduce", "corrected", "corrrectedNext", "corrrectedLast"],
  emits: ["flush"],
  components: {
    MaterialButton,
    MaterialTextArea,
    MarkDown,
  },
  data() {
    return {
      question: "",
      answer: "",
      points: "",
      difficulty: "",
      qchoicea: "",
      qchoiceb: "",
      qchoicec: "",
      qchoiced: "",
      accuracy: "",
      num: "",
      correctnum: "",
      analysis: "",
      qtype: "",
      atype: "",
      sname: "",
      radio: "",
      choice: [],
      done: false,
      tid: "",
      textarea: "",
      wronganswer: "",
      score: "",
      openaiAnswer: "",
      waiting: false,
      container: null,
      hint: "我将为你提供详细的错误分析，快来做题吧！",
    };
  },
  computed: {
    nextPage() {
      if (this.corrected === true) {
        return this.corrrectedNext;
      } else {
        let tid = parseInt(this.tid) + 1;
        if (tid <= 302) return tid;
        else return 302;
      }
    },
    lastPage() {
      if (this.corrected === true) {
        return this.corrrectedLast;
      } else {
        let tid = parseInt(this.tid) - 1;
        if (tid >= 1) return tid;
        else return 1;
      }
    },
  },
  watch: {
    testIntroduce(sc) {
      this.question = sc.question;
      this.answer = sc.answer;
      this.difficulty = sc.difficulty;
      this.qchoicea = sc.qchoicea;
      this.qchoiceb = sc.qchoiceb;
      this.qchoicec = sc.qchoicec;
      this.qchoiced = sc.qchoiced;
      this.accuracy = (sc.accuracy * 100).toFixed(2);
      this.num = sc.num;
      this.correctnum = sc.correctnum;
      this.points = sc.points;
      this.analysis = sc.analysis;
      this.sname = sc.sname;
      this.atype = sc.atype;
      this.qtype = sc.qtype;
      this.tid = sc.tid;
    },
  },
  methods: {
    //点击AI标志
    touch() {
      rotateSpeed = 0.1;
      this.hint = "别点了！快做题！";
      coreColor = 0xf36011;
      setTimeout(() => {
        rotateSpeed = 0.01;
        this.hint = "我将为你提供详细的错误分析，快来做题吧！";
        coreColor = 0xffeb57;
      }, 1000);
    },
    //提交答案
    async submitText() {
      const result = await api.reqSubmitText({
        tid: this.tid,
        sname: this.sname,
        userid: this.$store.state.user.userInfo.userId,
        wronganswer: this.textarea,
      });
      if (result.data.code === 200) {
        if (result.data.data.iscorrect === true) {
          const h = document.createElement("p");
          const newContent = document.createElement("div");
          const newContentText = document.createTextNode(
            `您的得分为：${result.data.data.score}`
          );
          newContent.appendChild(newContentText);
          h.appendChild(newContent);
          this.$notify({
            dangerouslyUseHTMLString: true,
            title: "回答正确！",
            message: h.innerHTML,
            type: "success",
          });
          this.done = true;
        } else {
          const h = document.createElement("p");
          const newContent = document.createElement("div");
          const newContentText = document.createTextNode(
            `您的得分为：${result.data.data.score}`
          );
          newContent.appendChild(newContentText);
          h.appendChild(newContent);
          this.done = true;
          this.$notify.error({
            dangerouslyUseHTMLString: true,
            title: "回答错误！",
            message: h.innerHTML,
          });
          this.waiting = true;
          rotateSpeed = 0.1;
          await this.getOpenAIAnswer();
        }
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
      this.$emit("flush", parseInt(this.tid), null);
    },
    //选择题判题系统
    async submitChoice() {
      this.wronganswer = "";
      for (let i = 0; i < this.choice.length; i++) {
        this.wronganswer += this.choice[i];
      }
      console.log("Before submit request" + this.wronganswer);
      const result = await api.reqSubmitChoice({
        tid: this.tid,
        sname: this.sname,
        userid: this.$store.state.user.userInfo.userId,
        wronganswer: this.wronganswer,
      });
      if (result.data.code === 200) {
        if (result.data.data.iscorrect === true) {
          this.$notify({
            title: "提示",
            message: "选择正确！",
            type: "success",
          });
          this.done = true;
        } else {
          this.$notify.error({
            title: "提示",
            message: "选择错误！",
          });
          this.done = true;
        }
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
      this.$emit("flush", parseInt(this.tid), null);
    },
    flush: function (num, forward = true) {
      this.done = false;
      this.choice = [];
      this.textarea = "";
      this.openaiAnswer = "";
      if (parseInt(this.tid) === num && forward === true) {
        this.$notify.warning({
          title: "提示",
          message: "已经是最后一题了！",
        });
      } else if (parseInt(this.tid) === num && forward === false) {
        this.$notify.warning({
          title: "提示",
          message: "已经是第一题了！",
        });
      }
      this.$emit("flush", num, forward);
    },
    async getOpenAIAnswer() {
      const configuration = new Configuration({
        apiKey: import.meta.env.VITE_OPENAI_API_KEY,
      });
      const openai = new OpenAIApi(configuration);

      const completion = await openai.createChatCompletion({
        model: "gpt-3.5-turbo",
        messages: [
          {
            role: "user",
            content: `我现在有一个道问答题，他的题目是"${this.question}"，现在他的标准答案是"${this.answer}"，我的答案是"${this.textarea}"，请分析一下我错在哪里了？`,
          },
        ],
      });
      this.openaiAnswer = completion.data.choices[0].message.content;
      this.waiting = false;
      rotateSpeed = 0.01;
    },
  },
  mounted() {
    this.container = document.getElementById("three");
    let scene = new THREE.Scene();

    // 创建一个相机
    let camera = new THREE.PerspectiveCamera(
      75,
      this.container.clientWidth / this.container.clientHeight,
      0.1,
      1000
    );
    camera.position.z = 5;

    // 创建一个渲染器
    let renderer = new THREE.WebGLRenderer({
      alpha: true,
      antialias: true,
      gammaFactor: 2.2, // 设置gammaFactor属性为2.2
    });

    renderer.setSize(this.container.clientWidth, this.container.clientHeight);
    this.container.appendChild(renderer.domElement);

    // 创建一个圆环
    let ring = new THREE.TorusGeometry(2, 0.1, 16, 100);
    let ring2 = new THREE.TorusGeometry(2, 0.1, 16, 100);
    let sphere = new THREE.SphereGeometry(0.5, 32, 16);

    // 创建一个材质
    let material = new THREE.MeshBasicMaterial({
      color: 0xf1c40f,
      metalness: 1,
      roughness: 0.2,
      transparent: true,
      opacity: 0.8,
    });
    let material2 = new THREE.MeshPhongMaterial({
      color: coreColor, // 红色
      roughness: 0.05,
    });
    let torus = new THREE.Mesh(ring, material);
    let torus2 = new THREE.Mesh(ring2, material);
    let torus3 = new THREE.Mesh(sphere, material2);
    let torus4 = new THREE.Mesh(ring2, material);
    let mesh = [torus, torus2, torus3, torus4];
    let light = new THREE.PointLight(0xffffff, 1, 100);
    light.position.set(0, 5, 0);
    let ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    scene.add(ambientLight);
    scene.add(light);
    scene.add(torus);
    scene.add(torus2);
    scene.add(torus3);
    scene.add(torus4);
    torus.rotation.y = 90;
    torus2.rotation.x = 90;
    let that = this;

    // 定义每帧渲染时的处理函数，让圆环自动旋转
    function animate() {
      requestAnimationFrame(animate);
      mesh.forEach((item) => {
        item.visible = that.qtype === "简述题";
      });
      torus.rotation.y += rotateSpeed;
      torus2.rotation.x += rotateSpeed;
      torus3.material.color.setHex(coreColor);
      renderer.render(scene, camera);
    }

    // 开始渲染动画
    animate();
  },
};
</script>

<style scoped>
canvas {
  display: block;
}
.elCol1 {
  margin: 14px 0;
  /*width: 700px;*/
  /*float: left;*/
}

.elCol2 {
  width: 348px;
  margin: 14px 6px;
  height: 400px;
}

h1 {
  font-size: 40px;
}

h3 {
  color: grey;
}

.choices {
  display: flex;

  flex-direction: column;
  margin: 20px 0;
  justify-content: space-between;
  align-items: flex-start;
}

.choice {
  display: flex;
  margin: 10px 0 !important;
  width: 100%;
  padding: 12px 20px 0 10px;
}

.submit {
  font-size: 20px;
  font-weight: bold;
  margin: 20px 0;
  height: 60px;
  width: 100%;
}

.changePage {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

.analysis {
  margin: 50px 0;
  width: 100%;
}

#three {
  cursor: pointer;
  width: 100px;
  height: 100px;
  position: fixed;
  top: 35rem;
  right: 30%;
  z-index: 1000;
}

.disappear {
  display: none;
}
</style>
