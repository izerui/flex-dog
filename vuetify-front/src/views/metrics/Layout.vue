<template>
    <div>
        <v-toolbar
                dense
                tabs
                color="primary"
                dark
        >
            <v-tabs
                    v-model="tabs"
                    color="transparent"
                    slider-color="white"
            >
                <v-tabs-slider color="yellow"></v-tabs-slider>
                <v-tab
                        v-for="tabsItem in tabsItems"
                        :key="tabsItem.id"
                        :to="tabsItem.link"
                >
                    <span class="pl-2 pr-2">{{ tabsItem.title }}</span>
                </v-tab>
            </v-tabs>
        </v-toolbar>

        <v-flex xs12>
            <router-view/>
        </v-flex>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tabs: null,
                tabsItems: [
                    {id: 1, title: '服务器监控', link: '/metrics/ecs'},
                    {id: 2, title: '数据库监控', link: '/metrics/dbs'},
                    {id: 3, title: 'redis监控', link: '/metrics/redis'}
                ]
            }
        }
    }
</script>
