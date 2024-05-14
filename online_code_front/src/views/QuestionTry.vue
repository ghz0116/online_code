<style>
.code {
  font-family: Arial, monospace;
  font-size: 16px;
}

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

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.el-table .error-row {
  background: #fde6e6;
}

.el-table .right-row {
  background: #f0f9eb;
}

</style>
<style>
.speech-bubble {
  position: fixed;
  bottom: 450px; /* 距离底部的距离 */
  right: 20px; /* 距离右侧的距离 */
  background-color: #fff;
  border: 2px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  max-width: 400px; /* 调整气泡最大宽度 */
}

.speech-bubble::after {
  content: '';
  position: absolute;
  border-style: solid;
  border-width: 10px 10px 0;
  border-color: #ccc transparent;
  bottom: -10px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
<template>
  <el-container>
    <el-aside width="30%" style="overflow-y:hidden;height: 800px">
      <page-title title="问题挑战"></page-title>
      <h3>{{ question.questionName }}</h3>
      <el-row>
        <el-col :span="4">
          难度：
        </el-col>
        <el-col :span="8">
          <el-rate
            v-model="question.degree"
            disabled
            text-color="#ff9900"
            score-template="{value}">
          </el-rate>
        </el-col>
      </el-row>
      <el-tag :key="item" v-for="item in question.tags" style="margin-right: 3px;margin-top: 5px">{{ item }}</el-tag>
      <div style="max-height:700px;overflow-y: scroll;overflow-x:hidden;margin-top: 10px"
           v-html="question.questionDetail"></div>
    </el-aside>
    <el-main>
      <div>
        <el-row :gutter="20">
          <el-col :span="4">编程语言：</el-col>

          <el-col :span="4" :offset="12">
            字体大小：
          </el-col>

          <el-col :span="4">主题风格：</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-select v-model="editor.languageType" placeholder="请选择" @change="getPreCode">
              <el-option
                v-for="item in codeLanguages"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
          </el-col>

          <el-col :span="1">
            <el-button type="success" icon="el-icon-caret-right" circle @click="runCode()"></el-button>
          </el-col>
          <el-col :span="1">
            <el-button type="danger" icon="el-icon-refresh-right" circle></el-button>
          </el-col>
          <el-col :span="1">
            <el-popover
              placement="right"
              width="400"
              trigger="click">
              <el-table @current-change="handleCurrentChange"
                        ref="singleTable"
                        highlight-current-row
                        :data="submitRecords" height="400">
                <el-table-column width="200" :formatter="formatDate" property="submitTime"
                                 label="日期"></el-table-column>
                <el-table-column width="100" property="language" label="语言"></el-table-column>
                <el-table-column width="100" label="结果">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.correct" type="success">已通过</el-tag>
                    <el-tag v-else type="danger">未通过</el-tag>
                  </template>
                </el-table-column>
              </el-table>
              <el-button slot="reference" type="primary" icon="el-icon-time" circle></el-button>
            </el-popover>
          </el-col>
          <el-col :span="4" :offset="9">
            <el-slider v-model="editor.fontSize" :min="16" :max="50" @change="changeFontSize"></el-slider>
          </el-col>

          <el-col :span="4" style="padding-top: 7px">
            浅色
            <el-switch
              v-model="editor.dark"
              active-color="#303133"
              @change="changeTheme"
              inactive-color="#C0C4CC">
            </el-switch>
            深色
          </el-col>
        </el-row>
      </div>
      <div style="border: #DCDFE6 3px solid;border-radius: 5px">
        <codemirror id="editor" ref="mycode" v-model="editor.curCode" :options="cmOptions" class="code"></codemirror>
      </div>
      <el-divider></el-divider>
      <div style="border: #DCDFE6 3px solid;border-radius: 5px">
        <template>
          <el-tabs v-model="showPage" type="border-card">
            <el-tab-pane label="测试用例" name="testCase">
              <el-tag
                style="cursor: pointer"
                :key="testcase.name"
                :effect="testcase.showType"
                v-for="testcase in testCases"
                @click="changeShowCase(testcase.name)"
              >
                {{ testcase.name }}
              </el-tag>
              <div>
                <div :key="item" v-for="item in question.parameters">
                  {{ item }}
                  <el-input v-model="showCase.parameter[item]" disabled></el-input>
                </div>
                result:
                <el-input v-model="showCase['result']" disabled></el-input>
              </div>


            </el-tab-pane>
            <el-tab-pane label="测试结果" name="testResult" ref="resultPane">
              <template>
                <el-table
                  :data="runResult"
                  style="width: 100%"
                  :row-class-name="tableRowClassName">
                  <el-table-column
                    prop="exitCode"
                    label="进程结束代码"
                    width="180">
                  </el-table-column>
                  <el-table-column
                    prop="caseName"
                    label="测试用例名称"
                    width="180">
                  </el-table-column>
                  <el-table-column
                    prop="runResult"
                    label="输出结果">
                  </el-table-column>
                  <el-table-column
                    label="是否正确">
                    <template v-slot:default="scope">
                      <el-tag effect="dark" v-if="scope.row.right" type="success">√</el-tag>
                      <el-tag effect="dark" v-else type="danger">×</el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="错误信息">
                    <template v-slot:default="scope">
                      <el-popover
                        placement="left-end"
                        title="错误信息"
                        width="400"
                        trigger="hover"
                        :content="scope.row.errorInfo">
                        <el-icon style="font-size: 20px" circle slot="reference"
                                 class="el-icon-warning-outline"></el-icon>
                      </el-popover>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="durationInMillis"
                    label="耗时(ms)">
                  </el-table-column>
                </el-table>
              </template>
            </el-tab-pane>
          </el-tabs>
        </template>
      </div>
      <div v-if="showAsk" class="speech-bubble">
        <p>出错了吗？别着急，让我来为你解答。</p>
        <el-row>
          <el-col :span="12">
            <el-button type="text" @click="askAI()">好的，请告诉我</el-button>
          </el-col>
          <el-button type="text" @click="showAsk=false">不用了，谢谢</el-button>
          <el-col :span="12"></el-col>
        </el-row>
      </div>
      <div v-loading="loading" v-if="showReply" class="speech-bubble">
        <p>{{ replyContent }}</p>
        <el-button type="text" @click="showReply=false">知道了，谢谢</el-button>
      </div>
    </el-main>
  </el-container>
</template>


<script>  // 如果你使用了NPM安装L2Dwidget，就可以使用import语句导入
import { codemirror } from 'vue-codemirror'
import 'codemirror/theme/ambiance.css'
import 'codemirror/mode/javascript/javascript'
import 'codemirror/mode/clike/clike'

import { fetchCodeType, fetchPreCode, fetchQuestion, fetchTestCase } from '@/api/question'
import { askAI, getAnswerCode, getRecords, submitAnswer } from '@/api/questionAnswer'
import { L2Dwidget } from 'live2d-widget'
import PageTitle from '@/components/PageTitle.vue'

export default {
  name: 'ExecuteAction',
  data () {
    return {
      showCase: {},
      testCases: [],
      showPage: 'testCase',
      markdownContent: '',
      question: {
        questionId: 0,
        title: '',
        describe: '',
        degree: 1,
        tags: [],
        parameters: []
      },
      codeLanguages: [],
      editor: {
        curCode: '',
        dark: false,
        fontSize: 16,
      },
      cmOptions: {
        lineNumbers: true,
        mode: 'javascript', // 设置为 JavaScript 模式
        theme: 'default',
        gutters: ['CodeMirror-lint-markers'],
        lint: true,
      },
      runResult: [],
      showAsk: false,
      showReply: false,
      replyContent: '',
      loading: false,
      submitRecords: [],
    }
  },
  components: {
    PageTitle,
    codemirror,
  },

  beforeDestroy () {
    document.getElementById('live2d-widget').remove()
  },
  mounted () {
    // 设定题目Id
    this.question.questionId = this.$route.params.questionId
    // 初始化编辑器
    this.initEditor()
    // 初始化卡通人物
    this.init2d()
    // 获取题目信息
    this.fetchCodeType()
    // 获取测试用例
    this.fetchTestCase()
    this.getSubmitRecords()
  },
  methods: {
    handleCurrentChange (val) {
      const answerId = val.answerId
      getAnswerCode(answerId).then(res => {
        this.editor.curCode = res.data
        this.editor.languageType = val.language
      })
    },
    formatDate (row, column) {
      // 获取提交时间
      const submitTime = row[column.property]
      // 如果提交时间存在且为有效的日期格式，则进行格式化
      if (submitTime) {
        return new Date(submitTime).toLocaleString() // 使用toLocaleString()进行本地化的日期格式化
      } else {
        return '' // 如果提交时间为空，则返回空字符串
      }
    },
    getSubmitRecords () {
      const questionId = this.$route.query.questionId
      getRecords(questionId).then(res => {
        this.submitRecords = res.data
        console.log(res.data)
      })
    },
    generateAnswer () {
      return {
        language: this.editor.languageType,
        questionId: this.question.questionId,
        code: this.editor.curCode
      }
    },
    askAI () {
      this.showAsk = false
      this.showReply = true
      this.loading = true
      askAI(this.generateAnswer()).then(res => {
        this.loading = false
        this.replyContent = res.data
      }).catch(err => {
        this.loading = false
        this.replyContent = 'AI回答失败'
      })
    },
    initEditor () {
      // // 获取 CodeMirror 实例
      const editor = this.$refs.mycode.codemirror
      editor.setSize('auto', 350)
    },
    changeTheme () {
      const editor = this.$refs.mycode.codemirror
      if (this.editor.dark) {
        editor.setOption('theme', 'ambiance') // 设置主题
      } else {
        editor.setOption('theme', 'default') // 设置主题
      }
    },
    changeFontSize () {
      document.getElementById('editor').style.fontSize = this.editor.fontSize + 'px'
    },
    init2d () {
      L2Dwidget.init({
        'model': {
          'jsonPath': 'https://unpkg.com/live2d-widget-model-haruto@1.0.5/assets/haruto.model.json',
          'scale': 1
        },
        'display': {
          'position': 'right',
          'width': 250,
          'height': 600,
          'hOffset': 0,
          'vOffset': -20
        },
        'mobile': {
          'show': true,
          'scale': 0.5
        },
        'react': {
          'opacityDefault': 0.8,
          'opacityOnHover': 1
        }
      })
    },
    changeShowCase (caseName) {
      this.testCases.forEach(item => {
        this.$set(item, 'showType', 'plain')
        if (item.name === caseName) {
          this.showCase = item
          this.$set(item, 'showType', 'dark')
        }
      })
    },
    getPreCode (value) {
      const questionId = this.$route.query.questionId
      fetchPreCode(questionId, value).then(res => {
        this.editor.curCode = res.data.code
      })
    },
    tableRowClassName ({ row }) {
      return row.right ? 'right-row' : 'error-row'
    },
    fetchTestCase () {
      const questionId = this.$route.query.questionId
      fetchTestCase(questionId)
        .then(res => {
          const testCases = res.data.map(testCase => ({
            ...testCase,
            parameter: JSON.parse(testCase.parameterMap)
          }))
          // Update the first testCase directly, no need for $set
          if (testCases.length > 0) {
            testCases[0].showType = 'dark'
          }
          this.testCases = testCases
          this.showCase = testCases.length > 0 ? testCases[0] : null
          this.fetchQuestion()
        })
        .catch(error => {
          this.$message.error('Error fetching test case:' + error)
        })
    },
    fetchQuestion () {
      const questionId = this.$route.query.questionId
      fetchQuestion(questionId).then(res => {
        this.question = res.data
        this.question.parameters = JSON.parse(this.question.parameterStr)
        this.question.tags = JSON.parse(this.question.tagsJson)
      })
    },
    fetchCodeType () {
      const questionId = this.$route.query.questionId
      fetchCodeType(questionId).then(res => {
          this.codeLanguages = res.data
        }
      )
    },
    runCode () {
      submitAnswer(this.generateAnswer()).then(res => {
        this.runResult = res.data
        this.runResult.forEach(result => {
          if (!result.right) {
            this.showAsk = true
          }
        })
        this.showPage = 'testResult'
      })
    },
  }
}
</script>


<style scoped>
.information-box >>> .CodeMirror {
  font-family: monospace;
  height: 71vh;
  direction: ltr;
}
</style>
