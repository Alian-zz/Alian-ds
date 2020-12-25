import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    mode:"history",
    routes: [
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../views/login/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '/dashboard',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/pmsProduct',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/product/product.vue'),
                    meta: { title: '商品列表' }
                },
                {
                    path: '/pmsAddProduct',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/product/addProduct.vue'),
                    meta: { title: '添加商品' }
                },
                {
                    path: '/pmsUpdateProduct',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/product/updateProduct.vue'),
                    meta: { title: '添加商品' }
                },
                {
                    path: '/pmsBrand',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/brand/BrandMain.vue'),
                    meta: { title: '品牌管理' }
                },
                {
                    path: '/pmsCategory',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/category/CategoryMain.vue'),
                    meta: { title: '商品分类' }
                },
                {
                    path: '/attributeCategory',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/attribute/ProductAttr.vue'),
                    meta: { title: '商品类型' }
                },
                {
                    path: '/umsAdmin',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/admin/admin.vue'),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/umsRole',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/role/role.vue'),
                    meta: { title: '角色管理' }
                },
                {
                    path: '/umsMenu',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/menu/Menu.vue'),
                    meta: { title: '菜单管理' }
                },
                {
                    path: '/umsResource',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/resource/resource.vue'),
                    meta: { title: '资源列表' }
                },
                {
                    path: '/allocMenu',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/role/allocMenu.vue'),
                    meta: { title: '分配菜单' }
                },
                {
                    path: '/allocResource',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/role/allocResource.vue'),
                    meta: { title: '分配资源' }
                },
                {
                    path: '/resourceCategory',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/ums/resource/categoryList.vue'),
                    meta: { title: '资源分类' }
                },
                {
                    path: '/AttrList',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/attribute/AttrList.vue'),
                    meta: { title: '属性设置' }
                },
                {
                    path: '/ParamList',
                    component: () => import(/* webpackChunkName: "brand" */ '../views/pms/attribute/ParamList.vue'),
                    meta: { title: '参数设置' }
                },
                {
                    path: '/icon',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/Icon.vue'),
                    meta: { title: '自定义图标' }
                },
                {
                    path: '/table',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/BaseTable.vue'),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/Tabs.vue'),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/form',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/BaseForm.vue'),
                    meta: { title: '基本表单' }
                },
                {
                    // 富文本编辑器组件
                    path: '/editor',
                    component: () => import(/* webpackChunkName: "editor" */ '../components/page/VueEditor.vue'),
                    meta: { title: '富文本编辑器' }
                },
                {
                    // markdown组件
                    path: '/markdown',
                    component: () => import(/* webpackChunkName: "markdown" */ '../components/page/Markdown.vue'),
                    meta: { title: 'markdown编辑器' }
                },
                {
                    // 图片上传组件
                    path: '/upload',
                    component: () => import(/* webpackChunkName: "upload" */ '../components/page/Upload.vue'),
                    meta: { title: '文件上传' }
                },
                {
                    // vue-schart组件
                    path: '/charts',
                    component: () => import(/* webpackChunkName: "chart" */ '../components/page/BaseCharts.vue'),
                    meta: { title: 'schart图表' }
                },
                {
                    // 拖拽列表组件
                    path: '/drag',
                    component: () => import(/* webpackChunkName: "drag" */ '../components/page/DragList.vue'),
                    meta: { title: '拖拽列表' }
                },
                {
                    // 拖拽Dialog组件
                    path: '/dialog',
                    component: () => import(/* webpackChunkName: "dragdialog" */ '../components/page/DragDialog.vue'),
                    meta: { title: '拖拽弹框' }
                },
                {
                    // 国际化组件
                    path: '/i18n',
                    component: () => import(/* webpackChunkName: "i18n" */ '../components/page/I18n.vue'),
                    meta: { title: '国际化' }
                },
                {
                    // 权限页面
                    path: '/permission',
                    component: () => import(/* webpackChunkName: "permission" */ '../components/page/Permission.vue'),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
                {
                    path: '/donate',
                    component: () => import(/* webpackChunkName: "donate" */ '../components/page/Donate.vue'),
                    meta: { title: '支持作者' }
                }
            ]
        },

        {
            path: '*',
            redirect: '/404'
        }
    ]
});
