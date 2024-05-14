<style>
.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

</style>

<template>
  <div>
    <page-title :title="type"></page-title>
    <el-form :model="question" label-position="top">
      <el-form-item label="题目名称">
        <el-input v-model="question.questionName" @blur="getTags"></el-input>
      </el-form-item>
      <el-form-item label="问题标签">
        <el-select v-model="question.tags"
                   allow-create
                   filterable
                   multiple
                   :remote-method="remoteMethod"
                   :loading="loading"
                   placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="题目描述">
        <mavon-editor ref="mdedit" v-model="markdownContent" @imgAdd="imgAdd" @change="edit" style="height: 80vh">
        </mavon-editor>
      </el-form-item>
    </el-form>
    <el-container>
      <el-aside style="width: 50%">
        <el-form>
          <el-form-item label="编程语言">
            <el-select v-model="chooseLanguages" @change="chooseChanges" @remove-tag="removeEditor" multiple
                       placeholder="请选择">
              <el-option
                v-for="item in codeLanguages"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="方法参数">
            <el-button type="primary" icon="el-icon-plus" round size="mini" @click="addParameter"></el-button>
            <el-row v-for="(item,index) in parameters" :key="index">
              <el-col :span="3">
                参数名称：
              </el-col>
              <el-col :span="8">
                <el-input v-model="parameters[index]"></el-input>
              </el-col>
              <el-col :offset="1" :span="4">
                <el-button type="danger" icon="el-icon-delete" @click="deleteParameter(index)">
                </el-button>
              </el-col>
            </el-row>

          </el-form-item>
          <el-form-item label="测试用例">
            <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{ tag }}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              style="width: 90px"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button style="margin-left: 10px" v-else class="button-new-tag" size="small" @click="showInput">添加用例
            </el-button>
            <br>
            <div v-for="item in testCases" :key="item.name"
                 style="width: 30%;background-color:rgba(64,158,255,0.09);border-radius: 5px;padding: 5px;margin-right: 5px;float: left;margin-bottom: 5px">
              <el-row style="text-align: center">
                用例{{ item.name }}
              </el-row>
              <el-row v-for="para in parameters" :key="para">
                <el-col :span="10">
                  {{ para }}:
                </el-col>
                <el-col :span="14">
                  <el-input v-model="item.parameter[para]">
                  </el-input>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="10">
                  result:
                </el-col>
                <el-col :span="14">
                  <el-input v-model="item['result']"></el-input>
                </el-col>
              </el-row>

            </div>
          </el-form-item>
        </el-form>
      </el-aside>
      <el-main style="width: 50%">
        <el-tabs type="border-card">
          <el-tab-pane :label="item" :key="item" v-for="(item,index) in chooseLanguages">
            <codemirror :ref="item" v-model="editors[index].curCode" :options="cmOptions"
                        class="code"></codemirror>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>

    <el-button @click="addQuestion()" type="primary" style="width: 100%">保存问题</el-button>
  </div>

</template>
<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/theme/ambiance.css'
import 'codemirror/mode/javascript/javascript'
import 'codemirror/mode/clike/clike'
import {
  addQuestion,
  uploadImage,
  predictTags,
  fetchQuestion,
  fetchCodeType,
  fetchPreCode,
  fetchTestCase
} from '@/api/question'
import { baseURL } from '@/utils/request'
import PageTitle from '@/components/PageTitle.vue'

export default {
  components: { PageTitle },
  data () {
    return {
      type: '',
      question: {
        questionId: 0,
        questionName: '',
        questionDetail: '',
        tags: [],
        md: '',
        courseId: 0
      },
      loading: false,
      options: [],
      courseId: 0,
      markdownContent: '',
      chooseLanguages: [],
      codeLanguages: ['Java', 'Python', 'JavaScript', 'C++'],
      editors: [],
      cmOptions: {
        lineNumbers: true,
        mode: 'javascript', // 设置为 JavaScript 模式
        theme: 'default',
        gutters: ['CodeMirror-lint-markers'],
        lint: true,
      },
      parameters: [],
      dynamicTags: [],
      testCases: [],
      inputVisible: false,
      inputValue: ''
    }
  },
  created () {
    this.courseId = this.$route.query.courseId
    const type = this.$route.query.type
    if (type === 'edit') {
      this.type = '编辑问题'
      this.renderQuestion(this.$route.query.questionId)
    } else if (type === 'add') {
      this.type = '添加问题'
    }
  },
  methods: {
    getTags () {
      predictTags(this.question.questionName).then(res => {
        const tags = res.data
        tags.forEach(tag => {
          if (tag !== 'None') {
            this.question.tags.push(tag)
          }
        })
      })
    },
    remoteMethod (query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.list.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    renderQuestion (questionId) {
      fetchQuestion(questionId).then(res => {
        this.question = res.data
        this.markdownContent = this.question.md
        const parameterStr = this.question.parameterStr
        if (parameterStr !== '' && parameterStr !== null) {
          this.parameters = JSON.parse(parameterStr)
        }
        const tagsJson = this.question.tagsJson
        if (tagsJson !== '' && tagsJson !== null) {
          this.question.tags = JSON.parse(tagsJson)
        } else {
          this.question.tags = []
        }

      })
      fetchCodeType(questionId).then(res => {
        const chooseLanguages = res.data
        chooseLanguages.forEach(item => {
          this.chooseLanguages.push(item)
          const editor = {
            type: item,
            curCode: '',
            dark: false,
            languageType: 'javascript',
          }
          this.editors.push(editor)
          fetchPreCode(questionId, item).then(res => {
            editor.curCode = res.data.code
          })
        })
      })
      fetchTestCase(questionId).then(res => {
        const testCases = res.data
        for (let i = 0; i < testCases.length; i++) {
          const testCase = testCases[i]
          this.dynamicTags.push(testCase.name)
          const testCaseEntity = {
            name: testCase.name,
            parameter: JSON.parse(testCase.parameterMap),
            result: testCase.result
          }
          this.testCases.push(testCaseEntity)
        }
      })
    },

    handleClose (tag) {
      const index = this.dynamicTags.indexOf(tag)
      this.dynamicTags.splice(index, 1)
      this.testCases.splice(index, 1)
    },
    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm () {
      let inputValue = this.inputValue
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
      const testCase = {
        name: inputValue,
        parameter: {},
        result: ''
      }
      this.parameters.forEach(item => {
        testCase.parameter[item] = ''
      })
      this.testCases.push(testCase)
    },
    deleteParameter (index) {
      this.parameters.splice(index, 1)
    },

    addParameter () {
      this.parameters.push('')
    },
    removeEditor (ele) {
      this.editors = this.editors.filter(function (element) {
        return element.type !== ele // 返回 true 表示保留元素，返回 false 表示移除元素
      })
    },
    chooseChanges () {
      if (this.chooseLanguages.length > this.editors.length) {
        let lastElement = this.chooseLanguages[this.chooseLanguages.length - 1]
        const editor = {
          type: lastElement,
          curCode: '',
          dark: false,
          languageType: 'javascript',
        }
        this.editors.push(editor)
      }
    },
    imgAdd (position, file) {
      let imgData = new FormData()
      imgData.append('file', file)
      uploadImage(imgData).then(res => {
        const imgName = res.data
        const url = baseURL + '/question/fetch_img/' + imgName
        this.$refs.mdedit.$img2Url(position, url)
      })
    },
    addQuestion () {
      const preCodes = []
      this.editors.forEach(item => {
        const preCode = {
          type: item.type,
          code: item.curCode
        }
        preCodes.push(preCode)
      })
      this.question.parameters = this.parameters
      this.question.testCases = this.testCases
      this.question.preCodes = preCodes
      if (this.question.courseId === 0) {
        this.question.courseId = this.courseId
      }
      addQuestion(this.question).then(res => {
        this.$message({
          message: '保存成功',
          type: 'success'
        })
        this.$router.go(-1)
      })
    },
    edit (value, render) {
      this.question.md = value
      this.question.questionDetail = render
    }
  }
}
</script>
