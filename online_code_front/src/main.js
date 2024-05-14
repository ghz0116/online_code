import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


//codemirror
import VueCodemirror from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
Vue.use(VueCodemirror)

// 全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)

Vue.config.productionTip = false

// 引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

// 引入axios
import axios from 'axios'

Vue.prototype.$axios = axios



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
