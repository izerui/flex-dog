<template>
    <v-navigation-drawer
            width="250"
            dark
            persistent
            :mini-variant="miniVariant"
            :value="drawer"
            fixed
            app
            @input="drawerUpdate"
    >
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

        <v-tooltip right :disabled="!miniVariant">
            <v-toolbar flat class="transparent" dense slot="activator">
                <v-list class="pa-0" :class="{'list-border-bottom' : miniVariant}">
                    <v-list-tile to="/">
                        <v-list-tile-action>
                            <v-icon>home</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title>Project Overview</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-toolbar>
            <span>Project Overview</span>
        </v-tooltip>
        <v-divider></v-divider>

        <v-list subheader :class="{'list-border-bottom' : miniVariant}">
            <v-subheader>ANALYTICS</v-subheader>
            <template v-for="item in analyticsItems">
                <v-tooltip right :disabled="!miniVariant">
                    <v-list-tile
                            :key="item.icon"
                            :to="item.link"
                            slot="activator"
                            ripple
                    >
                        <v-list-tile-action>
                            <v-icon>{{item.icon}}</v-icon>
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

        <v-list subheader>
            <v-subheader>DEVELOP</v-subheader>
            <template v-for="item in developItems">
                <v-tooltip right :disabled="!miniVariant">
                    <v-list-tile
                            :key="item.icon"
                            :to="item.link"
                            slot="activator"
                    >
                        <v-list-tile-action>
                            <v-icon v-html="item.icon"></v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title v-text="item.title"></v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                    <span v-text="item.title"></span>
                </v-tooltip>
            </template>
        </v-list>
        <!--<v-divider></v-divider>-->
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: "LeftLayout",
        props: {
            drawer:{
                type: Boolean,
                default: true
            }
        },
        data() {
            return {
                appName: process.env.VUE_APP_APP_NAME,
                miniVariant: false,
                tabs: null,
                tabsItems: [
                    {
                        id: 1,
                        title: 'Indicators',
                        link: 'indicators'
                    },
                    {
                        id: 2,
                        title: 'Backup',
                        link: 'backup'
                    },
                    {
                        id: 3,
                        title: 'Logs',
                        link: 'logs'
                    }
                ],
                analyticsItems: [
                    {
                        icon: 'dashboard',
                        title: 'Dashboard',
                        link: '/dashboard'
                    },
                    {
                        icon: 'event',
                        title: 'Events',
                        link: '/fff'
                    },
                    {
                        icon: 'comment',
                        title: 'Notifications',
                        link: '/df'
                    }
                ],
                developItems: [
                    {
                        icon: 'supervisor_account',
                        title: 'Authentification',
                        link: '/wef'
                    },
                    {
                        icon: 'storage',
                        title: 'Database',
                        link: '/sdf'
                    },
                    {
                        icon: 'perm_media',
                        title: 'Storage',
                        link: '/few'
                    },
                    {
                        icon: 'public',
                        title: 'Hosting',
                        link: '/dfdf'
                    },
                    {
                        icon: 'functions',
                        title: 'Functions',
                        link: '/ffff'
                    }
                ],
            }
        },
        methods: {
            drawerUpdate(val){
                this.$emit("update:drawer",val);
            }
        }
    }
</script>

<style scoped>

</style>