<template>
    <v-app>

        <!-- 左侧菜单栏 -->
        <left-layout :drawer.sync="leftDrawer"></left-layout>

        <v-toolbar
                app
                flat
                dense
                color="primary"
                dark
        >
            <v-toolbar-side-icon @click.stop="leftOps"/>
            <v-menu :nudge-width="100" :class="searching ? 'hidden-xs-only' : ''">
                <v-toolbar-title slot="activator" class="pl-2">
                    <span>{{ menuItems[0] }}</span>
                    <v-icon>arrow_drop_down</v-icon>
                </v-toolbar-title>
                <v-list light>
                    <v-list-tile v-for="item in menuItems" :key="item" @click="">
                        <v-list-tile-title v-text="item"></v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
            <v-spacer></v-spacer>

            <v-btn icon @click.native.stop="searchBegin">
                <v-icon>search</v-icon>
            </v-btn>
            <div :class="{'searching--closed': !searching}" class="searching">
                <v-text-field
                        id="search"
                        v-model="search"
                        append-icon="close"
                        @click:append="searchEnd"
                        label="Search"
                        hide-details
                        single-line
                        color="white"
                        @blur="onBlur"
                ></v-text-field>
            </div>

            <v-tooltip bottom>
                <v-btn icon @click.stop="rightDrawer = !rightDrawer" slot="activator">
                    <v-badge color="red" overlap>
                        <span slot="badge">2</span>
                        <v-icon>notifications</v-icon>
                    </v-badge>
                </v-btn>
                <span>2 unread notifications</span>
            </v-tooltip>

            <v-menu>
                <v-btn icon slot="activator">
                    <v-avatar class="white" size="32">
                        <v-icon color="primary">person</v-icon>
                    </v-avatar>
                </v-btn>
                <v-list class="pa-0" light>
                    <v-list-tile avatar>
                        <v-list-tile-avatar>
                            <v-avatar class="primary" size="48px">
                                <v-icon dark>person</v-icon>
                            </v-avatar>
                        </v-list-tile-avatar>
                        <v-list-tile-content>
                            <v-list-tile-title>John Doe</v-list-tile-title>
                            <v-list-tile-sub-title>Administrator</v-list-tile-sub-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <v-divider></v-divider>

                    <v-list-tile key="profile" @click="">
                        <v-list-tile-action>
                            <v-icon>person</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title>My Profile</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <v-divider></v-divider>

                    <v-list-tile key="lock_open" @click="">
                        <v-list-tile-action>
                            <v-icon>lock_open</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title>Logout</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-menu>
        </v-toolbar>

        <v-content>
            <router-view/>
        </v-content>

        <!-- 右侧通知栏 -->
        <RightNotification :drawer.sync="rightDrawer"></RightNotification>

    </v-app>
</template>

<script>
    import LeftLayout from "./LeftLayout";
    import RightNotification from "./RightNotification";

    export default {
        name: 'VuebaseLayout',
        components: {RightNotification, LeftLayout},
        data() {
            return {
                leftDrawer: true,
                rightDrawer: false,
                menuItems: ['Vue', 'NodeJS', 'Laravel'],
                searching: false,
                search: ''
            }
        },

        methods: {
            leftOps(){
                this.leftDrawer = !this.leftDrawer;
                console.log(this.leftDrawer);
            },
            onBlur() {
                this.searching = false
                this.search = ''
            },

            searchBegin() {
                this.searching = true
                setTimeout(() => document.querySelector('#search').focus(), 50)
            },

            searchEnd() {
                this.searching = false
                this.search = ''
                document.querySelector('#search').blur()
            }
        }
    }
</script>

<style scoped lang="stylus">
    @import '../../node_modules/vuetify/src/stylus/settings/_variables.styl';

    .bottom-menu {
        position: absolute;
        width: 100%;
        bottom: 0;
    }

    .searching {
        overflow: hidden;
        width: 208px;
        padding-left: 8px;
        transition: $primary-transition;
    }

    .searching--closed {
        padding-left: 0;
        width: 0;
    }

    .searching > * {
        right: 8px;
    }

    .searching--closed > * {
        display: none;
    }

    .hidden-searching {
        @media $display-breakpoints.sm-and-down {
            display: none !important;
        }
    }

    .list-border-bottom {
        border-bottom: 1px solid rgba(255, 255, 255, 0.12);
    }
</style>
