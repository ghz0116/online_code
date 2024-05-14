<style>
/* Consolidated styles */
.index {
  background-color: rgba(221, 234, 248, 0.55);
  color: #121238;
  margin: -20px;
}

.number {
  border: 1px solid #939393;
  font-size: 25px;
  background-color: rgba(163, 151, 239, 0.54);
  width: 30px;
  text-align: center;
  border-radius: 5px;
  padding: 2px;
}
</style>
<template>
  <div style="background-color: rgba(221,234,248,0.55);color: #121238;padding-bottom: 100px;margin: -20px">
    <!--    第一页-->
    <div style="padding-bottom: 50px">
      <el-row>
        <el-col :span="8" :offset="6">
          <p style="font-size: 60px">编程学习之路</p>
          <p style="margin-top: -30px;font-size: 18px">通过系统化的课程和实践,掌握编程技能,开启无限可能。</p>
          <el-button type="primary" style="width: 200px;height: 50px;font-size: 20px;margin-top: 30px"
                     @click="jumpPage('/course')">立即开始
          </el-button>
          <el-button plain style="width: 200px;height: 50px;font-size: 20px;margin-top: 30px">了解更多</el-button>
        </el-col>
        <el-col :span="8">
          <img style="height: 350px;width: 300px;border-radius: 10px;margin-top: 50px"
               :src="require('@/resource/img/welcome.webp')"
               alt="">
        </el-col>
      </el-row>
      <el-row style="margin-top: 30px">
        <el-col :offset="6" :span="8">
          <p style="font-size: 50px">编程入门课程</p>
          <p style="margin-top: -30px;font-size: 18px">从零基础到熟练掌握,循序渐进的课程设计助你快速入门编程。</p>
        </el-col>
      </el-row>
      <el-row style="margin-bottom: 50px">
        <el-col :offset="6" :span="4">
          <p style="font-size: 25px">基础编程</p>
          <span style="font-size: 18px">学习编程语法、变量、流程控制等基础知识。</span>
        </el-col>
        <el-col :span="4" :offset="1">
          <p style="font-size: 25px">项目实战</p>
          <span style="font-size: 18px">动手开发小型应用程序,将理论转化为实践。</span>

        </el-col>
        <el-col :span="4" :offset="1">
          <p style="font-size: 25px">学习路径</p>
          <span style="font-size: 18px">根据个人情况推荐最佳学习方案。</span>
        </el-col>
      </el-row>
    </div>
    <!--    第二页-->
    <div class="index" style="height: 600px;width: 100%">
      <div style="width: 100%;height: 600px;background-color: rgba(255,255,255,0.8)">
        <el-row>
          <el-col :span="8" :offset="6">
            <p style="font-size: 50px">算法与数据结构</p>
            <p style="margin-top: -30px;font-size: 18px">掌握计算机科学的核心知识,提升编程能力和解决问题的思维。</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="5" :span="5" style="text-align: right">
            <p style="font-size: 25px">算法基础</p>
            <span style="font-size: 18px">学习算法的时间复杂度、排序算法等。</span>
          </el-col>
          <el-col :span="2">
            <p style="font-size: 25px">-----------</p>
          </el-col>
          <el-col :span="1"><p class="number">1</p></el-col>
        </el-row>
        <el-row>
          <el-col :offset="12" :span="1"><p class="number">2</p></el-col>
          <el-col :span="2">
            <p style="font-size: 25px">-----------</p>
          </el-col>
          <el-col :span="5" style="text-align: left">
            <p style="font-size: 25px">数据结构</p>
            <span style="font-size: 18px">掌握链表、栈、队列、树等数据结构。</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="5" :span="5" style="text-align: right">
            <p style="font-size: 25px">算法实践</p>
            <span style="font-size: 18px">运用算法和数据结构解决实际问题。</span>
          </el-col>
          <el-col :span="2">
            <p style="font-size: 25px">-----------</p>
          </el-col>
          <el-col :span="1"><p class="number">3</p></el-col>
        </el-row>
      </div>
    </div>
    <!--    第三页-->
    <div>
      <el-row>
        <el-col :offset="6" :span="8">
          <p style="font-size: 50px">编程语言专栏</p>
          <p style="margin-top: -30px;font-size: 18px">深入学习各种编程语言,掌握不同语言的特点与应用场景。</p>
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="1">
          <p class="number">1 </p>
        </el-col>
        <el-col :span="5">
          <p style="font-size: 25px">python</p>
          简单易学,适用于数据分析、机器学习等。
        </el-col>
        <el-col :span="1" :offset="1">
          <p class="number">2</p>
        </el-col>
        <el-col :span="5">
          <p style="font-size: 25px">Java</p>
          稳健、高性能,广泛应用于企业级开发。
        </el-col>
      </el-row>
      <el-row>
        <el-col :offset="6" :span="1">
          <p class="number">3 </p>
        </el-col>
        <el-col :span="5">
          <p style="font-size: 25px">JavaScript</p>
          网页开发的标准语言,也适用于服务端和移动端。
        </el-col>
        <el-col :span="1" :offset="1">
          <p class="number">4</p>
        </el-col>
        <el-col :span="5">
          <p style="font-size: 25px">C++</p>
          底层系统开发和高性能计算的首选。
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>

export default {
  methods: {
    jumpPage (src) {
      this.$router.push(src)
    },
  }
}
</script>
