(function(e){function t(t){for(var r,a,c=t[0],l=t[1],s=t[2],u=0,d=[];u<c.length;u++)a=c[u],i[a]&&d.push(i[a][0]),i[a]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(e[r]=l[r]);f&&f(t);while(d.length)d.shift()();return o.push.apply(o,s||[]),n()}function n(){for(var e,t=0;t<o.length;t++){for(var n=o[t],r=!0,a=1;a<n.length;a++){var c=n[a];0!==i[c]&&(r=!1)}r&&(o.splice(t--,1),e=l(l.s=n[0]))}return e}var r={},a={app:0},i={app:0},o=[];function c(e){return l.p+"js/"+({}[e]||e)+"."+{"chunk-53ed305c":"4265eff0","chunk-5fe2968d":"960a3c18","chunk-55832c04":"20c935da","chunk-339b7ff4":"3bd5bb71","chunk-20117c93":"f1a5f872","chunk-3749715c":"4fa15d2c","chunk-9ebac69a":"7031a203","chunk-571a59a9":"02e1eeec","chunk-6f54fc0a":"2965efd6","chunk-8d14ce94":"143081df","chunk-d6bf482e":"bbe0dc0b","chunk-37de1a9e":"31e881b0"}[e]+".js"}function l(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,l),n.l=!0,n.exports}l.e=function(e){var t=[],n={"chunk-53ed305c":1,"chunk-55832c04":1,"chunk-339b7ff4":1,"chunk-20117c93":1,"chunk-3749715c":1,"chunk-9ebac69a":1,"chunk-571a59a9":1,"chunk-6f54fc0a":1,"chunk-8d14ce94":1,"chunk-d6bf482e":1};a[e]?t.push(a[e]):0!==a[e]&&n[e]&&t.push(a[e]=new Promise(function(t,n){for(var r="css/"+({}[e]||e)+"."+{"chunk-53ed305c":"f1ecd91b","chunk-5fe2968d":"31d6cfe0","chunk-55832c04":"f5f2ced5","chunk-339b7ff4":"dc91b004","chunk-20117c93":"47a22f58","chunk-3749715c":"b1ecc611","chunk-9ebac69a":"31f407ae","chunk-571a59a9":"b1ecc611","chunk-6f54fc0a":"b1ecc611","chunk-8d14ce94":"57cdb1c1","chunk-d6bf482e":"b1ecc611","chunk-37de1a9e":"31d6cfe0"}[e]+".css",i=l.p+r,o=document.getElementsByTagName("link"),c=0;c<o.length;c++){var s=o[c],u=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(u===r||u===i))return t()}var d=document.getElementsByTagName("style");for(c=0;c<d.length;c++){s=d[c],u=s.getAttribute("data-href");if(u===r||u===i)return t()}var f=document.createElement("link");f.rel="stylesheet",f.type="text/css",f.onload=t,f.onerror=function(t){var r=t&&t.target&&t.target.src||i,o=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");o.code="CSS_CHUNK_LOAD_FAILED",o.request=r,delete a[e],f.parentNode.removeChild(f),n(o)},f.href=i;var h=document.getElementsByTagName("head")[0];h.appendChild(f)}).then(function(){a[e]=0}));var r=i[e];if(0!==r)if(r)t.push(r[2]);else{var o=new Promise(function(t,n){r=i[e]=[t,n]});t.push(r[2]=o);var s,u=document.createElement("script");u.charset="utf-8",u.timeout=120,l.nc&&u.setAttribute("nonce",l.nc),u.src=c(e),s=function(t){u.onerror=u.onload=null,clearTimeout(d);var n=i[e];if(0!==n){if(n){var r=t&&("load"===t.type?"missing":t.type),a=t&&t.target&&t.target.src,o=new Error("Loading chunk "+e+" failed.\n("+r+": "+a+")");o.type=r,o.request=a,n[1](o)}i[e]=void 0}};var d=setTimeout(function(){s({type:"timeout",target:u})},12e4);u.onerror=u.onload=s,document.head.appendChild(u)}return Promise.all(t)},l.m=e,l.c=r,l.d=function(e,t,n){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(l.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)l.d(n,r,function(t){return e[t]}.bind(null,r));return n},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="/",l.oe=function(e){throw console.error(e),e};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],u=s.push.bind(s);s.push=t,s=s.slice();for(var d=0;d<s.length;d++)t(s[d]);var f=u;o.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"56d7":function(e,t,n){"use strict";n.r(t);n("cadf"),n("551c"),n("f751"),n("097d");var r=n("2b0e"),a=n("bb71");n("bf40");r["default"].use(a["a"],{iconfont:"md"});var i=n("a691");r["default"].use(i["a"]);var o=n("8c4f");r["default"].use(o["a"]);var c=[{path:"/",name:"home",meta:{title:"主页"},redirect:"/console"},{path:"/console",name:"console",meta:{title:"访问日志"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-37de1a9e")]).then(n.bind(null,"7475"))}},{path:"/files",name:"files",meta:{title:"服务列表"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-53ed305c"),n.e("chunk-9ebac69a")]).then(n.bind(null,"b234"))}},{path:"/ecs",name:"ecs",meta:{title:"服务器列表"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-d6bf482e")]).then(n.bind(null,"99d97"))}},{path:"/metrics",redirect:"/metrics/ecs",name:"metrics",meta:{title:"数据监控"},component:function(){return Promise.all([n.e("chunk-53ed305c"),n.e("chunk-5fe2968d")]).then(n.bind(null,"ad2a"))},children:[{path:"/metrics/ecs",name:"ecs",meta:{title:"服务器监控"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-3749715c")]).then(n.bind(null,"4b53"))}},{path:"/metrics/dbs",name:"dbs",meta:{title:"数据库监控"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-571a59a9")]).then(n.bind(null,"bff7"))}},{path:"/metrics/redis",name:"redis",meta:{title:"redis监控"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-6f54fc0a")]).then(n.bind(null,"56ce"))}}]},{path:"/onlines",name:"onlines",meta:{title:"在线用户"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-20117c93")]).then(n.bind(null,"db93"))}},{path:"/mrp",name:"mrp",meta:{title:"MRP数据"},component:function(){return Promise.all([n.e("chunk-55832c04"),n.e("chunk-339b7ff4"),n.e("chunk-8d14ce94")]).then(n.bind(null,"d085"))}}],l=new o["a"]({mode:"hash",base:"/",routes:c});l.afterEach(function(e,t,n){var a={to:e,from:t,next:n};r["default"].prototype.$events.dispatch(Object({NODE_ENV:"production",VUE_APP_APP_NAME:"网狗盘",BASE_URL:"/"}).TYPE_ROUTER,a)});var s=l,u=n("6829"),d=n.n(u),f={200:"服务器成功返回请求的数据。",201:"新建或修改数据成功。",202:"一个请求已经进入后台排队（异步任务）。",204:"删除数据成功。",400:"发出的请求有错误，服务器没有进行新建或修改数据的操作。",401:"用户没有权限（令牌、用户名、密码错误）。",403:"用户得到授权，但是访问是被禁止的。",404:"发出的请求针对的是不存在的记录，服务器没有进行操作。",406:"请求的格式不可得。",410:"请求的资源被永久删除，且不会再得到的。",422:"当创建一个对象时，发生一个验证错误。",500:"服务器发生错误，请检查服务器。",502:"网关错误。",503:"服务不可用，服务器暂时过载或维护。",504:"网关超时。"},h={install:function(e,t){if(!h.installed)if(h.installed=!0,t){var n=function(t){var n=t.status,r=t.statusText;if(n>=200&&n<300)return t;var a=f[n]||r;e.prototype.$message.error(a);var i=new Error(a);throw i.status=t.status,i};t.interceptors.request.use(function(e){return e.headers["X-Tag"]="flyio",e}),t.interceptors.response.use(function(t){n(t);var r=t.data;if(r instanceof Blob)return r;var a=r.errMsg;return a&&e.prototype.$message.error(a),r},function(t){if(t.response){var n=t.response,r=n.status,a=n.statusText,i=f[r]||a;e.prototype.$message.error(i)}return Promise.reject(t)}),e.fly=t,Object.defineProperties(e.prototype,{$fly:{get:function(){return t}}})}else console.error("You have to install flyIo")}};r["default"].use(h,d.a);var p={listeners:{},dispatch:function(e,t){this.listeners[e]&&this.listeners[e](t)},listener:function(e,t){this.listeners[e]=t},remove:function(e){this.listeners[e]&&(this.listeners[e]=null)}},v={install:function(e,t){v.installed||(v.installed=!0,e.events=t,Object.defineProperties(e.prototype,{$events:{get:function(){return t}}}))}};r["default"].use(v,p);var m=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n(e.component,{tag:"component"},[e._t("default")],2)},b=[],k=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-app",[n("left-layout",{attrs:{drawer:e.leftDrawer},on:{"update:drawer":function(t){e.leftDrawer=t}}}),n("v-content",[n("router-view")],1),n("RightNotification",{attrs:{drawer:e.rightDrawer},on:{"update:drawer":function(t){e.rightDrawer=t}}})],1)},_=[],g=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-navigation-drawer",{attrs:{width:"220",dark:"",persistent:"","mini-variant":e.miniVariant,value:e.drawer,fixed:"",app:""},on:{input:e.drawerUpdate}},[n("v-toolbar",{staticClass:"transparent",attrs:{flat:"",dense:""}},[n("v-list",{staticClass:"pa-0",class:{"list-border-bottom":e.miniVariant}},[n("v-list-tile",[e.miniVariant?e._e():n("v-list-tile-action",[n("v-icon",{attrs:{large:"",color:"orange"}},[e._v("invert_colors")])],1),e.miniVariant?e._e():n("v-list-tile-content",[n("v-list-tile-title",[n("h2",[e._v(e._s(e.appName))])])],1),n("v-list-tile-action",[n("v-btn",{attrs:{icon:""},on:{click:function(t){t.stopPropagation(),e.miniVariant=!e.miniVariant}}},[n("v-icon",[e._v(e._s(e.miniVariant?"chevron_right":"chevron_left"))])],1)],1)],1)],1)],1),n("v-divider"),e._l(e.menus,function(t){return n("v-list",{class:{"list-border-bottom":e.miniVariant},attrs:{subheader:""}},[n("v-subheader",[e._v(e._s(t.header))]),e._l(t.children,function(t){return[n("v-tooltip",{attrs:{right:"",disabled:!e.miniVariant}},[n("v-list-tile",{key:t.icon,attrs:{slot:"activator",to:t.link,ripple:""},slot:"activator"},[n("v-list-tile-action",[n("v-icon",{attrs:{color:t.color}},[e._v(e._s(t.icon))])],1),n("v-list-tile-content",[n("v-list-tile-title",{domProps:{textContent:e._s(t.title)}})],1)],1),n("span",[e._v(e._s(t.title))])],1)]})],2)}),n("v-divider")],2)},y=[],w={name:"LeftLayout",props:{drawer:{type:Boolean,default:!0}},data:function(){return{appName:"网狗盘",miniVariant:!1,menus:[{header:"服务",children:[{icon:"query_builder",title:"访问日志",link:"/console"},{icon:"dashboard",title:"服务列表",link:"/files"},{icon:"computer",title:"服务器列表",link:"/ecs"}]},{header:"监控",children:[{icon:"storage",title:"数据监控",link:"/metrics"},{icon:"airline_seat_recline_normal",title:"在线用户",link:"/onlines"}]},{header:"MRP",children:[{icon:"extension",title:"MRP数据",link:"/mrp"}]}]}},created:function(){},methods:{registerEvents:function(){var e=this;this.$events.listener(Object({NODE_ENV:"production",VUE_APP_APP_NAME:"网狗盘",BASE_URL:"/"}).TYPE_ROUTER,function(t){"/"!==t.to&&(e.miniVariant=!0)})},drawerUpdate:function(e){this.$emit("update:drawer",e)}}},V=w,P=n("2877"),E=n("6544"),T=n.n(E),L=n("8336"),O=n("ce7e"),N=n("132d"),j=n("8860"),A=n("ba95"),x=n("40fe"),D=n("5d23"),S=n("f774"),$=n("e0c7"),C=n("71d9"),R=n("3a2f"),U=Object(P["a"])(V,g,y,!1,null,"7b059ec1",null),B=U.exports;T()(U,{VBtn:L["a"],VDivider:O["a"],VIcon:N["a"],VList:j["a"],VListTile:A["a"],VListTileAction:x["a"],VListTileContent:D["a"],VListTileTitle:D["b"],VNavigationDrawer:S["a"],VSubheader:$["a"],VToolbar:C["a"],VTooltip:R["a"]});var M=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-navigation-drawer",{attrs:{temporary:"",right:!0,value:e.drawer,fixed:"",app:""},on:{input:e.drawerUpdate}},[n("v-toolbar",{staticClass:"primary",attrs:{flat:"",prominent:"",dark:""}},[n("v-toolbar-title",[e._v("Notifications")]),n("v-spacer"),n("v-btn",{attrs:{icon:""},on:{click:function(t){return t.stopPropagation(),e.drawerUpdate(!1)}}},[n("v-icon",[e._v("close")])],1)],1),n("v-list",{attrs:{subheader:"",dense:""}},[n("v-subheader",[e._v("All notifications")]),n("v-list-tile",{on:{click:function(e){}}},[n("v-list-tile-action",[n("v-icon",[e._v("person_add")])],1),n("v-list-tile-title",[e._v("\n                12 new users registered\n            ")])],1),n("v-divider"),n("v-list-tile",{on:{click:function(e){}}},[n("v-list-tile-action",[n("v-icon",[e._v("data_usage")])],1),n("v-list-tile-title",[e._v("\n                DB overloaded 80%\n            ")])],1)],1)],1)},I=[],q={name:"RightNotification",props:{drawer:{type:Boolean,default:!1}},methods:{drawerUpdate:function(e){this.$emit("update:drawer",e)}}},J=q,Y=n("9910"),F=n("2a7f"),H=Object(P["a"])(J,M,I,!1,null,"6eb98142",null),K=H.exports;T()(H,{VBtn:L["a"],VDivider:O["a"],VIcon:N["a"],VList:j["a"],VListTile:A["a"],VListTileAction:x["a"],VListTileTitle:D["b"],VNavigationDrawer:S["a"],VSpacer:Y["a"],VSubheader:$["a"],VToolbar:C["a"],VToolbarTitle:F["b"]});var X={name:"VuebaseLayout",components:{RightNotification:K,LeftLayout:B},data:function(){return{leftDrawer:!0,rightDrawer:!1,menuItems:["Vue","NodeJS","Laravel"],searching:!1,search:""}},created:function(){},methods:{}},z=X,G=n("7496"),Q=n("549c"),W=Object(P["a"])(z,k,_,!1,null,null,null),Z=W.exports;T()(W,{VApp:G["a"],VContent:Q["a"]});var ee={name:"App",components:{MainLayout:Z},computed:{component:function(){return Z}}},te=ee,ne=Object(P["a"])(te,m,b,!1,null,null,null),re=ne.exports;r["default"].config.productionTip=!1,new r["default"]({router:s,render:function(e){return e(re)}}).$mount("#app")}});
//# sourceMappingURL=app.501440bb.js.map