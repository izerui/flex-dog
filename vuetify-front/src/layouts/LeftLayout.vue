<template>
    <v-navigation-drawer
            width="220"
            dark
            persistent
            :mini-variant="miniVariant"
            :value="drawer"
            fixed
            app
            @input="drawerUpdate"
    >
        <!-- logo -->
        <v-toolbar flat class="transparent" dense>
            <v-list class="pa-0" :class="{'list-border-bottom' : miniVariant}">
                <v-list-tile>
                    <v-list-tile-action v-if="!miniVariant">
                        <v-icon large color="orange">invert_colors</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content v-if="!miniVariant">
                        <v-list-tile-title>
                            <h2>{{appName}}</h2>
                        </v-list-tile-title>
                    </v-list-tile-content>
                    <v-list-tile-action>
                        <v-btn icon @click.stop="miniVariant = !miniVariant">
                            <v-icon>{{miniVariant ? 'chevron_right' : 'chevron_left'}}</v-icon>
                        </v-btn>
                    </v-list-tile-action>
                </v-list-tile>
            </v-list>
        </v-toolbar>
        <v-divider></v-divider>

        <!-- 主页 -->
        <v-tooltip right :disabled="!miniVariant">
            <v-toolbar flat class="transparent" dense slot="activator">
                <v-list class="pa-0" :class="{'list-border-bottom' : miniVariant}">
                    <v-list-tile to="/">
                        <v-list-tile-action>
                            <v-icon>home</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title>欢迎页</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-toolbar>
            <span>欢迎页</span>
        </v-tooltip>
        <v-divider></v-divider>

        <!-- 菜单 -->
        <v-list subheader :class="{'list-border-bottom' : miniVariant}" v-for="menu in menus">
            <v-subheader>{{menu.header}}</v-subheader>
            <template v-for="item in menu.children">
                <v-tooltip right :disabled="!miniVariant">
                    <v-list-tile
                            :key="item.icon"
                            :to="item.link"
                            slot="activator"
                            ripple>
                        <v-list-tile-action>
                            <v-icon :color="item.color">{{item.icon}}</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title v-text="item.title"></v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <span>{{item.title}}</span>
                </v-tooltip>
            </template>
        </v-list>
        <v-divider></v-divider>
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: "LeftLayout",
        props: {
            drawer: {
                type: Boolean,
                default: true
            }
        },
        data() {
            return {
                appName: process.env.VUE_APP_APP_NAME,
                miniVariant: false,
                menus: [
                    {
                        header: "服务",
                        children: [
                            {
                                icon: 'query_builder',
                                title: '访问日志',
                                link: '/console'
                            },
                            {
                                icon: 'dashboard',
                                title: '服务列表',
                                link: '/files'
                            },
                            {
                                icon: 'computer',
                                title: '服务器列表',
                                link: '/ecs'
                            }
                        ],
                    },
                    {
                        header: "监控",
                        children: [
                            {
                                icon: 'storage',
                                title: '数据监控',
                                link: '/metrics'
                            },
                            {
                                icon: 'airline_seat_recline_normal',
                                title: '在线用户',
                                link: '/onlines'
                            },
                        ]
                    },
                    {
                        header: "MRP",
                        children: [
                            {
                                icon: 'person_add_disabled',
                                title: 'MRP数据',
                                link: '/weeeef'
                            },
                            {
                                icon: 'contact_support',
                                title: 'MRP异常',
                                link: '/sdffff'
                            }
                        ]
                    }
                ]
            }
        },
        created() {
            // this.registerEvents()
        },
        methods: {
            registerEvents() {
                this.$events.listener(process.env.TYPE_ROUTER, (data) => {
                    if (data.to !== '/') {
                        this.miniVariant = true;
                    }
                })
            },
            drawerUpdate(val) {
                this.$emit("update:drawer", val);
            }
        }
    }
</script>

<style scoped>

</style>