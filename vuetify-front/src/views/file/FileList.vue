<template>
    <v-card>
        <v-toolbar
                dense
                tabs
                color="primary"
                dark
        >
            <v-tabs
                    v-model="tabIndex"
                    color="transparent"
                    slider-color="white"
            >
                <v-tabs-slider color="yellow"></v-tabs-slider>
                <v-tab
                        v-for="label in tabsItems"
                        :key="label"
                        @click="loadData(label)"
                >
                    <span class="pr-2">{{ label }}</span>
                </v-tab>
            </v-tabs>
        </v-toolbar>
        <v-toolbar card prominent>
            <v-text-field
                    v-model="search"
                    append-icon="search"
                    label="Search"
                    single-line
                    hide-details
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="newFile">
                <v-icon dark>playlist_add</v-icon>
                新建服务
            </v-btn>
            <v-btn color="primary">
                <v-icon dark>cloud_upload</v-icon>
                上传文件
            </v-btn>
            <v-btn
                    :loading="loading"
                    color="primary"
                    @click="loadData(tabsItems[tabIndex])"
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
                item-key="id"
                fix-header
        >
            <template v-slot:items="props">
                <tr :class="props.item.status !== 'UP' ? 'disable-tr-class' : ''"
                    @click="props.expanded = !props.expanded">
                    <td class="text-xs-left">{{ props.item.server }}</td>
                    <td class="text-xs-left">{{ props.item.serverAddress }}</td>
                    <td class="text-xs-left">{{ props.item.fileName }}</td>
                    <td class="text-xs-left">{{ props.item.size }}</td>
                    <td class="text-xs-left">{{ props.item.owner }}</td>
                    <td class="text-xs-left">{{ props.item.port }}</td>
                    <td class="text-xs-left">{{ props.item.url }}</td>
                    <td class="text-xs-left">{{ props.item.uploadTimeStr }}</td>
                    <td class="text-xs-left">{{ props.item.deployTimeStr }}</td>
                    <td class="text-xs-left">{{ props.item.status }}</td>
                </tr>
            </template>
            <template v-slot:expand="props">
                <v-card flat>
                    <v-btn small color="grey" v-if="props.item.status === 'UP'">
                        <v-icon dark>airplanemode_inactive</v-icon>
                        下线
                    </v-btn>
                    <v-btn small color="success" v-else>
                        <v-icon dark>airplanemode_active</v-icon>
                        上线
                    </v-btn>
                    <v-btn small color="primary">
                        <v-icon dark>cloud_done</v-icon>
                        发布
                    </v-btn>
                    <v-btn small color="info">
                        <v-icon dark>dvr</v-icon>
                        日志
                    </v-btn>
                </v-card>
            </template>
            <template v-slot:no-results>
                <v-alert :value="true" color="error" icon="warning">
                    未找到包含 "{{ search }}" 的结果.
                </v-alert>
            </template>
        </v-data-table>

        <v-dialog v-model="dialog" persistent max-width="600px">
            <NewFileDialog @close="cancelFile" @save="saveFile"></NewFileDialog>
        </v-dialog>

    </v-card>
</template>

<script>
    import NewFileDialog from "./NewFileDialog";

    export default {
        components: {NewFileDialog},
        data() {
            return {
                search: '',
                headers: [
                    {text: '服务器', align: 'left', value: 'server'},
                    {text: 'IP地址', value: 'serverAddress'},
                    {text: '文件名称', value: 'fileName', width: '300'},
                    {text: '文件大小', value: 'size'},
                    {text: '拥有者', value: 'owner'},
                    {text: '端口', value: 'port'},
                    {text: '服务地址', value: 'url'},
                    {text: '上传时间', value: 'uploadTimeStr'},
                    {text: '最后发布时间', value: 'deployTimeStr'},
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
                tabsItems: [],
                tabIndex: null,
                dialog: false
            }
        },
        created() {
            this.loadData("全部");
        },
        methods: {
            cancelFile(){
              this.dialog = false
            },
            saveFile(file) {
              console.log(file);
              this.dialog = false;
              this.loadData(this.tabsItems[this.tabIndex])
            },
            newFile() {
                this.dialog = true;
            },
            async loadData(server) {
                this.loading = true;
                const result = await this.$fly.get('/api/v1/files', {server: server});
                this.dataList = result.data;
                if (server === '全部') {
                    this.fillTabItems();
                }
                this.loading = false;
            },
            fillTabItems() {
                this.tabsItems = []
                this.dataList.forEach(s => {
                    if (this.tabsItems.indexOf(s.server) < 0) {
                        this.tabsItems.push(s.server);
                    }
                })
                this.tabsItems.sort();
                this.tabsItems.unshift("全部")
            }
        }
    }
</script>

<style>
    .disable-tr-class {
        background-color: lightgrey;
    }
</style>