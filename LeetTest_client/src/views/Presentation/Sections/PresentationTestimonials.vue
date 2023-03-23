<script setup>
import DefaultReviewCard from "@/examples/cards/reviewCards/DefaultReviewCard.vue";
</script>
<template>
  <section class="py-7">
    <div class="container">
      <div class="row">
        <div class="col-lg-6 mx-auto text-center">
          <h2 class="mb-0">被众多学生评价</h2>
          <h2 class="text-gradient text-success mb-3">
            好评如潮
          </h2>
          <p class="lead">
            经过同寝室以及隔壁寝室同学体验过后，大家都说好！
          </p>
        </div>
      </div>
      <div class="row mt-6">
        <DefaultReviewCard
          name="黄某某"
          date="1 day ago"
          review='"使用LeetTest考研刷题网站，我发现刷题可以变得更加有趣了。平台提供了丰富的题目和多样化的练习模式，我可以选择自己感兴趣的题目和难度级别进行练习。此外，平台还设置了不同的考试模拟，让我更好地模拟实际考试场景，提高自己的应试能力。我在使用LeetTest的过程中，除了能够巩固自己的知识点，还能够感受到挑战和成就感，让我更加喜欢学习和考研。"'
          :rating="5.0"
        />
        <DefaultReviewCard
          class="ms-md-auto"
          color="bg-gradient-success"
          name="刘某某"
          date="1 week ago"
          review='"我一直觉得自己的学习进度不够快，很难在短时间内提高考研成绩。但是，使用LeetTest考研刷题网站后，我发现我的学习变得更加高效和个性化了。平台为我推荐了适合我水平和目标的练习题目，并根据我的答题情况智能调整难度，让我能够更加轻松地掌握知识点。此外，智能错题本功能也让我能够更好地记录和分析错题，及时调整学习计划，提高自己的复习效率。"'
          :rating="5.0"
        />

        <DefaultReviewCard
          name="徐某某"
          date="3 weeks ago"
          review='"LeetTest考研刷题网站不仅提供了丰富的题目和练习模式，还为我提供了多元化的学习资源。平台为我提供了名师课程和解析视频，让我更好地理解知识点和解题思路。同时，平台还提供了研究生考试动态和复习资料下载等多个板块，让我能够更全面地了解考研动态和获取复习资料。总的来说，LeetTest考研刷题网站为我提供了全方位的学习支持，让我在备战考研的路上更加自信和有序。"'
          :rating="5.0"
        />
      </div>

    </div>
  </section>
</template>
