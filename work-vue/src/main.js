import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import commonUtil from "@/utils/commonUtil";
import http_util from "@/utils/http_util";

Vue.config.productionTip = false;
Vue.use(Vuex)
Vue.use(ElementUI);
Vue.use(VueAxios, axios)

Vue.prototype.$reqUtil = http_util;
Vue.prototype.$cmUtil = commonUtil;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
