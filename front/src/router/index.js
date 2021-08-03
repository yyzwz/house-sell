import Vue from 'vue';
import ViewUI from 'view-design';
import util from '../libs/util';
import VueRouter from 'vue-router';
import Cookies from 'js-cookie';
import { routers } from './router';

Vue.use(VueRouter);

// 路由配置
const RouterConfig = {
    mode: 'history',
    routes: routers
};

export const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    ViewUI.LoadingBar.start();
    util.title(to.meta.title);
    var name = to.name;
    // 白名单
    var whiteList = ['login', 'regist', 'regist-result', 'relate', 'reset', 'authorize'];
    var isInWhiteList = util.oneOf(name, whiteList);
    if (!Cookies.get('userInfo') && !isInWhiteList) {
        // 判断是否已经登录且页面不在白名单
        next({
            name: 'login'
        });
    } else if (Cookies.get('userInfo') && name == 'login') {
        // 判断是否已经登录且前往的是登录页
        util.title();
        next({
            name: 'home_index'
        });
    } else {
        util.toDefaultPage([...routers], name, router, next);
    }
});

router.afterEach((to) => {
    util.openNewPage(router.app, to.name, to.params, to.query);
    ViewUI.LoadingBar.finish();
    window.scrollTo(0, 0);
});
