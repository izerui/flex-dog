/**
 * Created by serv on 2017/3/6.
 */
import Vue from 'vue';

const Events = {
    listeners: {},
    /**
     * 触发事件
     * @param event 事件
     * @param data 传参
     */
    dispatch(event, result) {
        if (this.listeners[event]) {
            this.listeners[event](result);
        }
    },
    /**
     * 事件监听
     * @param event 事件
     * @param listener 监听方法
     */
    listener(event, listener) {
        this.listeners[event] = listener;
    },
    /**
     * 移除事件监听
     * @param event 事件
     */
    remove(event) {
        if (!this.listeners[event]) {
            return;
        }
        this.listeners[event] = null;
    },
};

const plugins = {};

plugins.install = function (Vue, events) {

    if (plugins.installed) {
        return;
    }
    plugins.installed = true;

    Vue.events = events;
    Object.defineProperties(Vue.prototype, {
        $events: {
            get() {
                return events;
            }
        },
    });
}

Vue.use(plugins,Events);

export default Events;
