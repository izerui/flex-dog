<template>
    <v-app id="inspire">
        <v-navigation-drawer
                fixed
                :clipped="$vuetify.breakpoint.mdAndUp"
                app
                v-model="drawer"
                :mini-variant="mini"
        >
            <v-list dense>
                <template v-for="item in items">
                    <v-layout
                            row
                            v-if="item.heading"
                            align-center
                            :key="item.text"
                    >
                        <v-flex xs6>
                            <v-subheader v-if="item.heading">
                                {{ item.text }}
                            </v-subheader>
                        </v-flex>
                    </v-layout>
                    <!-- if -->
                    <v-list-group
                            v-else-if="item.children"
                            :key="item.text"
                            :prepend-icon="item.icon"
                            append-icon="keyboard_arrow_down">
                        <v-list-tile slot="activator">
                            <v-list-tile-content>
                                <v-list-tile-title>
                                    {{ item.text }}
                                </v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                        <v-list-tile
                                v-for="(child, i) in item.children"
                                :key="i"
                                @click="">
                            <v-list-tile-action v-if="child.icon">
                                <v-icon>{{ child.icon }}</v-icon>
                            </v-list-tile-action>
                            <v-list-tile-content>
                                <v-list-tile-title>
                                    {{ child.text }}
                                </v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list-group>

                    <!-- else -->
                    <v-list-tile v-else @click="" :key="item.text">
                        <v-list-tile-action>
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content>
                            <v-list-tile-title>
                                {{ item.text }}
                            </v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </template>
            </v-list>
        </v-navigation-drawer>
        <v-toolbar
                color="blue darken-3"
                dark
                app
                :clipped-left="$vuetify.breakpoint.mdAndUp"
                fixed
        >
            <v-toolbar-title style="width: 300px" class="ml-0 pl-3">
                <v-toolbar-side-icon @click.stop="mini = !mini"></v-toolbar-side-icon>
                <span class="hidden-sm-and-down">Google Contacts</span>
            </v-toolbar-title>
            <v-text-field
                    flat
                    solo-inverted
                    prepend-icon="search"
                    label="Search"
                    class="hidden-sm-and-down"
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-btn icon>
                <v-icon>apps</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>notifications</v-icon>
            </v-btn>
            <v-btn icon large>
                <v-avatar size="32px" tile>
                    <img
                            src="https://vuetifyjs.com/static/doc-images/logo.svg"
                            alt="Vuetify"
                    >
                </v-avatar>
            </v-btn>
        </v-toolbar>
        <v-content>
            <router-view/>
        </v-content>
        <v-btn
                fab
                bottom
                right
                color="pink"
                dark
                fixed
                @click.stop="dialog = !dialog"
        >
            <v-icon>add</v-icon>
        </v-btn>
        <v-dialog v-model="dialog" width="800px">
            <v-card>
                <v-card-title
                        class="grey lighten-4 py-4 title"
                >
                    Create contact
                </v-card-title>
                <v-container grid-list-sm class="pa-4">
                    <v-layout row wrap>
                        <v-flex xs12 align-center justify-space-between>
                            <v-layout align-center>
                                <v-avatar size="40px" class="mr-3">
                                    <img
                                            src="//ssl.gstatic.com/s2/oz/images/sge/grey_silhouette.png"
                                            alt=""
                                    >
                                </v-avatar>
                                <v-text-field
                                        placeholder="Name"
                                ></v-text-field>
                            </v-layout>
                        </v-flex>
                        <v-flex xs6>
                            <v-text-field
                                    prepend-icon="business"
                                    placeholder="Company"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs6>
                            <v-text-field
                                    placeholder="Job title"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field
                                    prepend-icon="mail"
                                    placeholder="Email"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field
                                    type="tel"
                                    prepend-icon="phone"
                                    placeholder="(000) 000 - 0000"
                                    mask="phone"
                            ></v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field
                                    prepend-icon="notes"
                                    placeholder="Notes"
                            ></v-text-field>
                        </v-flex>
                    </v-layout>
                </v-container>
                <v-card-actions>
                    <v-btn flat color="primary">More</v-btn>
                    <v-spacer></v-spacer>
                    <v-btn flat color="primary" @click="dialog = false">Cancel</v-btn>
                    <v-btn flat @click="dialog = false">Save</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-app>
</template>

<script>
    export default {
        name: "Main",
        data() {
            return {
                dialog: false,
                mini: false,
                drawer: null,
                items: [
                    {icon: 'contacts', text: '服务', heading: true},
                    {icon: 'contacts', text: '线上日志'},
                    {icon: 'history', text: '服务列表'},
                    {icon: 'content_copy', text: '服务器列表'},
                    {icon: 'content_copy', text: '监控', heading: true},
                    {icon: 'phonelink', text: '服务器监控'},
                    {icon: 'settings', text: '数据库监控'},
                    {icon: 'help', text: 'redis监控'},
                    {icon: 'chat_bubble', text: 'MRP', heading: true},
                    {icon: 'chat_bubble', text: 'MRP数据'},
                    {icon: 'keyboard', text: 'MRP异常'},
                ]
            }
        }
    }
</script>

<style scoped>

</style>