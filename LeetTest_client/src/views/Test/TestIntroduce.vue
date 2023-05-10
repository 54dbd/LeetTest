<template>
  <div>
    <el-row :gutter="20">
      <el-col class="elCol1" :span="16">
        <el-card shadow="always">
          <div>
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
            @click="flush(lastPage)"
            :to="{ path: '/test', query: { tid: lastPage } }"
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
            @click="flush(nextPage)"
            :to="{ path: '/test', query: { tid: nextPage } }"
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
        <el-row class="analysis">
          <el-card v-if="correct === true">
            <h2>解析:</h2>

            <div v-if="analysis">
              <MarkDown
                :text="analysis"
                class="content"
                v-if="qtype !== '图片题'"
              />
              <el-image :src="analysis" fit="fill" v-else></el-image>
            </div>
            <div v-else>
              {{ answer }}
            </div>
          </el-card>
        </el-row>
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
            v-if="atype === 1"
            variant="gradient"
            color="success"
            size="sm"
            class="submit"
            type="button"
            @click="submitChoice"
            >提交
          </MaterialButton>
        </div>
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
            v-if="atype === 0"
            variant="gradient"
            color="success"
            size="sm"
            class="submit"
            style="width: 80%; height: 60px"
            type="button"
            @click="submitText"
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

export default {
  name: "index",
  props: ["testIntroduce"],
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
      correct: "",
      tid: "",
      textarea: "",
      wronganswer: "",
      score: "",
    };
  },
  computed: {
    nextPage() {
      let tid = parseInt(this.tid) + 1;
      if (tid <= 302) return tid;
      else return 302;
    },
    lastPage() {
      let tid = parseInt(this.tid) - 1;
      if (tid >= 1) return tid;
      else return 1;
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
          console.log(h);
          this.$notify({
            dangerouslyUseHTMLString: true,
            title: "回答正确！",
            message: h.innerHTML,
            type: "success",
          });
          this.correct = true;
        } else {
          const h = document.createElement("p");
          const newContent = document.createElement("div");
          const newContentText = document.createTextNode(
            `您的得分为：${result.data.data.score}`
          );
          newContent.appendChild(newContentText);
          h.appendChild(newContent);
          this.correct = false;
          this.$notify.error({
            dangerouslyUseHTMLString: true,
            title: "回答错误！",
            message: h.innerHTML,
          });
        }
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
      this.$emit("flush", parseInt(this.tid));
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
          this.correct = true;
        } else {
          this.$notify.error({
            title: "提示",
            message: "选择错误！",
          });
        }
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
      this.$emit("flush", parseInt(this.tid));
    },
    select: function () {
      console.log("您的选项是" + this.choice);
    },
    flush: function (num) {
      this.correct = false;
      this.choice = [];
      this.textarea = "";
      this.$emit("flush", num);
    },
  },
};
</script>

<style scoped>
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
</style>
