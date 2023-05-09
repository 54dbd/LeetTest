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
              size="sm"
              style="width: 100%"
              type="button"
              >上一题</MaterialButton
            >
          </router-link>
          <router-link
            @click="flush(nextPage)"
            :to="{ path: '/test', query: { tid: nextPage } }"
          >
            <MaterialButton
              variant="gradient"
              color="success"
              size="sm"
              style="width: 100%"
              type="button"
              >下一题</MaterialButton
            >
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
        <MaterialButton
          v-if="atype === 1"
          variant="gradient"
          color="success"
          size="sm"
          class="submit"
          style="width: 100%; height: 60px"
          type="button"
          @click="submitChoice"
          >提交</MaterialButton
        >
        <MaterialButton
          v-if="atype === 0"
          variant="gradient"
          color="success"
          size="sm"
          class="submit"
          style="width: 100%; height: 60px"
          type="button"
          @click="submitText"
          >提交</MaterialButton
        >
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
    async saveHistory() {
      this.wronganswer = "";
      console.log("iscorrect:" + this.correct);
      if (this.atype === 1)
        for (let i = 0; i < this.choice.length; i++) {
          this.wronganswer += this.choice[i];
        }
      else if (this.atype === 0) {
        this.wronganswer = this.textarea;
      }
      console.log("before SAVE request" + this.wronganswer);
      const result = await api.reqSaveHistory({
        tid: this.tid,
        sname: this.sname,
        userid: this.$store.state.user.userInfo.userId,
        wronganswer: this.wronganswer,
        score: this.score,
        iscorrect: this.correct,
      });
      console.log("after request");
      if (result.data.code === 200) {
        // this.$notify.success("发布成功~");
      } else {
        this.$message.error("系统异常~ " + result.data.data.msg);
      }
    },

    //提交答案
    async submitText() {
      this.num += 1;
      console.log(this.num);

      //获得答案与回答的匹配程度,30%权重
      var weight_1 =
        this.strSimilarity2Percent(this.textarea, this.answer) * 0.3;
      console.log("答案字符串匹配：" + weight_1 * 2.0 * 100.0 + "%");
      console.log("weight_1：" + weight_1);
      //获得答案与回答的模糊程度,70%权重
      var weight_2 = this.fuzzyMatch(this.textarea, this.answer) * 0.7;
      console.log("答案模糊匹配：" + weight_2 * 2.0 * 100.0 + "%");
      console.log("weight_2：" + weight_2);
      var weight = weight_1 + weight_2;
      var sigmoid = 1 / (1 + Math.exp(-8 * (weight - 0.5)));
      console.log("综合打分weight：" + weight * 100.0 + "%");
      console.log("综合打分sigmoid：" + sigmoid * 100.0 + "%");
      var score = ((sigmoid * 100) / 20).toFixed(0);
      //最终成绩0~5分
      console.log("最终得分：" + score);
      //大于等于4分则判定为正确
      if (score >= 4) {
        this.correct = true;
        this.correctnum += 1;
        const h = document.createElement("p");
        const newContent = document.createElement("div");
        const newContentText = document.createTextNode(`您的得分为：${score}`);
        newContent.appendChild(newContentText);
        h.appendChild(newContent);
        console.log(h);
        this.$notify({
          dangerouslyUseHTMLString: true,
          title: "回答正确！",
          message: h.innerHTML,
          type: "success",
        });
      } else {
        const h = document.createElement("p");
        const newContent = document.createElement("div");
        const newContentText = document.createTextNode(`您的得分为：${score}`);
        newContent.appendChild(newContentText);
        h.appendChild(newContent);
        this.correct = false;
        this.$notify.error({
          dangerouslyUseHTMLString: true,
          title: "回答错误！",
          message: h.innerHTML,
        });
      }
      this.score = score;
      await this.saveHistory();
      this.accuracy = ((this.correctnum * 100) / this.num).toFixed(2);
      const result = await api.setNumByAid({
        num: this.num,
        sname: this.sname,
        correctnum: this.correctnum,
        accuracy: this.accuracy / 100,
      });
      if (result.data.code === 200) {
        console.log("提交成功~");
        console.log(this.textarea);
      } else {
        console.log("系统异常~ ");
      }
    },
    //选择题判题系统
    async submitChoice() {
      this.num += 1;
      console.log(this.num);
      const result = await api.setNumByAid({
        num: this.num,
        sname: this.sname,
      });
      if (result.data.code === 200) {
        console.log("提交成功~");
      } else {
        console.log("系统异常~ ");
      }
      var choice = "";
      for (let i = 0; i < this.choice.length; i++) {
        choice += this.choice[i];
      }
      console.log(choice);
      console.log(this.answer);
      if (this.answer.charAt(this.answer.length - 1) === " ") {
        this.answer = this.answer.slice(0, this.answer.length - 1);
      }
      if (choice.length !== this.answer.length) {
        this.correct = false;
        this.$notify.error({
          title: "提示",
          message: "选项个数不符！错误！",
        });
      } else {
        var j = 0;
        for (let i = 0; i < choice.length; i++) {
          for (j = 0; j < choice.length; j++) {
            if (choice[i] === this.answer[j]) {
              break;
            }
          }
        }
        if (j === choice.length) {
          //发现answer中不存在
          this.correct = false;

          this.$notify.error({
            title: "提示",
            message: "选择错误！",
          });
        } else {
          this.correct = true;
          this.correctnum += 1;
          const result = await api.setNumByAid({
            correctnum: this.correctnum,
            sname: this.sname,
          });
          this.$notify({
            title: "提示",
            message: "选择正确！",
            type: "success",
          });
        }
        await this.saveHistory();
      }
    },
    select: function () {
      console.log("您的选项是" + this.choice);
    },
    flush: function (num) {
      this.correct = false;
      this.$emit("flush", num);
    },
    //相似度匹配
    strSimilarity2Number(s, t) {
      var n = s.length,
        m = t.length,
        d = [];
      var i, j, s_i, t_j, cost;
      if (n === 0) return m;
      if (m === 0) return n;
      for (i = 0; i <= n; i++) {
        d[i] = [];
        d[i][0] = i;
      }
      for (j = 0; j <= m; j++) {
        d[0][j] = j;
      }
      for (i = 1; i <= n; i++) {
        s_i = s.charAt(i - 1);
        for (j = 1; j <= m; j++) {
          t_j = t.charAt(j - 1);
          if (s_i === t_j) {
            cost = 0;
          } else {
            cost = 1;
          }
          d[i][j] = this.Minimum(
            d[i - 1][j] + 1,
            d[i][j - 1] + 1,
            d[i - 1][j - 1] + cost
          );
        }
      }
      return d[n][m];
    },
    //两个字符串的相似程度，并返回相似度百分比
    strSimilarity2Percent(s, t) {
      var l = s.length > t.length ? s.length : t.length;
      var d = this.strSimilarity2Number(s, t);
      return (1 - d / l).toFixed(4);
    },
    Minimum(a, b, c) {
      return a < b ? (a < c ? a : c) : b < c ? b : c;
    },
    //关键词模糊匹配
    fuzzyMatch(str, key) {
      let index = -1,
        flag = false,
        count = 0;

      for (var i = 0, arr = key.split(""); i < arr.length; i++) {
        //有一个关键字都没匹配到，则没有匹配到数据
        if (str.indexOf(arr[i]) < 0) {
          //count为在答案中未找到的知识点字符个数
          count++;
          //break;
        } else {
          let match = str.matchAll(arr[i]);
          let next = match.next();
          while (!next.done) {
            if (next.value.index > index) {
              index = next.value.index;
              if (i === arr.length - 1) {
                flag = true;
              }
              break;
            }
            next = match.next();
          }
        }
      }
      return (1 - count / key.length).toFixed(4);
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
