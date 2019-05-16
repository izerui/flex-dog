<template>
    <v-card>
        <v-toolbar card prominent>
            <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
            <v-badge
                    :value="true"
                    color="blue"
            >
                <template v-slot:badge>
                    <span>{{userCount}}</span>
                </template>
                <span>授权数</span>
            </v-badge>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <v-badge
                    :value="true"
                    color="green"
            >
                <template v-slot:badge>
                    <span>{{onlineCount}}</span>
                </template>
                <span>在线数</span>
            </v-badge>
            <v-spacer></v-spacer>
            <v-btn
                    :loading="loading"
                    color="primary"
                    @click="loadData"
            >
                <v-icon left dark>refresh</v-icon>
                重新加载
            </v-btn>
        </v-toolbar>
        <v-data-table
                :headers="headers"
                :items="dataList"
                :search="search"
                :loading="loading"
                :pagination.sync="pagination"
                :expand="expand"
                item-key="code"
        >
            <template v-slot:items="props">
                <tr @click="props.expanded = !props.expanded">
                    <td class="text-xs-left">{{ props.item.code }}</td>
                    <td class="text-xs-left">{{ props.item.name }}</td>
                    <td class="text-xs-left">{{ props.item.relatedCount }}</td>
                    <td class="text-xs-left">{{ props.item.postCount }}</td>
                    <td class="text-xs-left">{{ props.item.count }}</td>
                    <td class="text-xs-left">{{ props.item.date }}</td>
                    <td class="text-xs-left">{{ props.item.type }}</td>
                    <td class="text-xs-left">{{ props.item.status }}</td>
                </tr>
            </template>
            <template v-slot:expand="props">
                <v-card flat>
                    <v-chip v-if="props.item.users" v-for="it in props.item.users.split(',')">{{it}}</v-chip>
                    <!--<v-card-text>{{props.item.users}}</v-card-text>-->
                </v-card>
            </template>
            <template v-slot:no-results>
                <v-alert :value="true" color="error" icon="warning">
                    未找到包含 "{{ search }}" 的结果.
                </v-alert>
            </template>
        </v-data-table>
    </v-card>
</template>

<script>
    export default {
        data() {
            return {
                search: '',
                headers: [
                    {text: '账套编号', align: 'left', value: 'code'},
                    {text: '企业名称', value: 'name'},
                    {text: '员工数', value: 'relatedCount'},
                    {text: '授权数', value: 'postCount'},
                    {text: '在线数', value: 'count'},
                    {text: '开户日期', value: 'date'},
                    {text: '类型', value: 'type'},
                    {text: '状态', value: 'status'},
                ],
                dataList: [],
                loading: false,
                pagination: {
                    descending: false,
                    page: 1,
                    rowsPerPage: 10,
                    sortBy: null,
                    totalItems: 0
                },
                expand: false,
                userCount: 0,
                onlineCount: 0,
            }
        },
        created() {
            this.loadData();
        },
        methods: {
            async loadData() {
                this.loading = true;
                const result = await this.$fly.get('/api/v1/onlines');
                this.dataList = result.data;
                this.loading = false;
                this.fillCount();
            },
            fillCount() {
                this.userCount = 0;
                this.onlineCount = 0;
                this.dataList.forEach(s => {
                    this.onlineCount += s.count;
                    this.userCount += s.postCount;
                })
            }
        }
    }
</script>

<style scoped>

</style>