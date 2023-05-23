<template>
  <div>
    <el-row :gutter="20">
      <el-col class="elCol1" :span="14" :push="2">
        <div>
          <div>
            <MarkDown :text="introduce" class="content" />
          </div>
        </div>
      </el-col>
      <el-col class="elCol2" :span="5" :push="2">
        <div>
          <div title="学校信息" direction="vertical" :column="1" size="medium">
            <div style="margin: 15px auto">
              <div>学校地址</div>
              <div style="color: #77b72c">{{ address }}</div>
            </div>
            <div style="margin: 15px auto">
              <div>联系电话</div>
              <div style="color: #77b72c">{{ phone }}</div>
            </div>
            <div style="margin: 15px auto">
              <div>学校官网</div>
              <a
                rel="nofollow"
                :href="officialSite"
                target="_blank"
                style="color: #4cb9fc; text-decoration: none"
                >{{ sname }}</a
              >
            </div>
            <div style="margin: 15px auto">
              <div>研究生院</div>
              <a
                rel="nofollow"
                :href="postgraduateSite"
                target="_blank"
                style="color: #4cb9fc; text-decoration: none"
                >{{ sname }}研究生院官网</a
              >
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MarkDown from "@/components/MarkDown/MarkDown.vue";
export default {
  name: "index",
  props: ["schoolIntroduce"],
  components: {
    MarkDown,
  },
  data() {
    return {
      introduce: "",
      postgraduateSite: "",
      address: "",
      phone: "",
      officialSite: "",
      sname: "",
    };
  },
  watch: {
    schoolIntroduce(sc) {
      this.introduce = sc.introduction;
      this.postgraduateSite = sc.postgraduateSite;
      this.address = sc.address;
      this.phone = sc.phone;
      this.officialSite = sc.officialSite;
      this.sname = sc.sname;
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
  font-size: 22px;
}
</style>
