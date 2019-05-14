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
            <v-toolbar-side-icon @click.stop="leftDrawer = !leftDrawer"/>

            <v-spacer></v-spacer>

            <v-tooltip bottom>
                <v-btn icon @click.stop="rightDrawer = !rightDrawer" slot="activator">
                    <v-badge color="red" overlap>
                        <span slot="badge">21</span>
                        <v-icon>notifications</v-icon>
                    </v-badge>
                </v-btn>
                <span>21个通知</span>
            </v-tooltip>

            <v-menu offset-y>
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
        }
    }
</script>