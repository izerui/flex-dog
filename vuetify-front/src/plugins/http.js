import router from '../router';
import flyIo from 'flyio';
import Vue from 'vue';

/**
 * Created by serv on 2017/8/4.
 */
const codeMessage = {
    200: '服务器成功返回请求的数据。',
    201: '新建或修改数据成功。',
    202: '一个请求已经进入后台排队（异步任务）。',
    204: '删除数据成功。',
    400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
    401: '用户没有权限（令牌、用户名、密码错误）。',
    403: '用户得到授权，但是访问是被禁止的。',
    404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
    406: '请求的格式不可得。',
    410: '请求的资源被永久删除，且不会再得到的。',
    422: '当创建一个对象时，发生一个验证错误。',
    500: '服务器发生错误，请检查服务器。',
    502: '网关错误。',
    503: '服务不可用，服务器暂时过载或维护。',
    504: '网关超时。'
};

const http = {};

http.install = function (Vue, fly) {

    if (http.installed) {
        return;
    }
    http.installed = true;
    if (!fly) {
        console.error('You have to install flyIo');
        return;
    }
    const checkStatus = (response) => {
        const {status, statusText} = response;
        if (status >= 200 && status < 300) {
            return response;
        }
        const errorText = codeMessage[status] || statusText;
        Vue.prototype.$message.error(errorText);
        const error = new Error(errorText);
        error.status = response.status;
        throw error;
    };

    //添加请求拦截器
    fly.interceptors.request.use((request)=>{
        //给所有请求添加自定义header
        request.headers["X-Tag"]="flyio";
        //打印出请求体
        // console.log(request.body)
        //终止请求
        //var err=new Error("xxx")
        //err.request=request
        //return Promise.reject(new Error(""))

        //可以显式返回request, 也可以不返回，没有返回值时拦截器中默认返回request
        return request;
    });

    //添加响应拦截器，响应拦截器会在then/catch处理之前执行
    fly.interceptors.response.use(
        (response) => {
            checkStatus(response);
            const data = response.data;
            // 对二进制文件进行处理。
            if (data instanceof Blob) {
                // NProgress.done()
                return data;
            }
            const {errMsg} = data;
            if (errMsg) {
                console.log(Vue.prototype.$message)
                Vue.prototype.$message.error(errMsg);
            }
            return data;
        },
        (error) => {
            //发生网络错误后会走到这里
            //return Promise.resolve("ssss")
            // NProgress.done()
            if (error.response) {
                const {status, statusText} = error.response;
                const errorText = codeMessage[status] || statusText;
                Vue.prototype.$message.error(errorText);
            }
            return Promise.reject(error);
        }
    )

    Vue.fly = fly;
    Object.defineProperties(Vue.prototype, {
        $fly: {
            get() {
                return fly;
            }
        },
    });
}

Vue.use(http,flyIo);
