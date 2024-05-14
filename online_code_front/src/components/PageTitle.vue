<style>
.back {
  float: left;
  margin-right: 10px;
  cursor: pointer;
  color: #909399;
}

.back:hover {
  color: black;
}
</style>

<template>
  <div style="font-size: 20px;margin-bottom: 20px">
    <div class="back" @click="goBack">
      <el-icon class="el-icon-back"></el-icon>
      返回
    </div>
    <el-divider direction="vertical"></el-divider>
    <span style="margin-left: 10px">{{ title }}</span>
  </div>
</template>

<script>
export default {
  name: 'PageTitle',
  props: {
    title: {
      type: String,
      default: ''
    }
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
  }
}
</script>
