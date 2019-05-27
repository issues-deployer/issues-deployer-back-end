import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import App from '@/App'
import router from './router'
import VueLogger from 'vuejs-logger';

Vue.config.productionTip = false;

const options = {
  isEnabled: true,
  logLevel: 'debug',
  stringifyArguments: false,
  showLogLevel: true,
  showMethodName: false,
  separator: '|',
  showConsoleColors: true
};

Vue.use(VueLogger, options);

new Vue({
  render: h => h(App),
  router,
  // template: '<App/>',
  // components: { App }
}).$mount('#app');
