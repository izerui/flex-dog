<template>
    <v-card tile>
        <v-toolbar dense card dark color="primary">
            <v-btn icon dark @click="cancel">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>指定操作人</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                        <v-btn @click="confirm" icon v-on="on">
                            <v-icon>check_circle_outline</v-icon>
                        </v-btn>
                    </template>
                    <span>确认</span>
                </v-tooltip>
            </v-toolbar-items>
        </v-toolbar>

        <v-card
                class="d-flex flex-row mb-2"
                color="grey lighten-2"
                flat
                tile
        >
            <v-card
                    class="pa-2"
                    outlined
                    tile
            >
                <v-list>
                    <v-list-tile
                            v-for="item in roles"
                            :key="item.code"
                            @click="loadUsers(item)"
                    >
                        <v-list-tile-content>
                            <v-list-tile-title v-html="item.name"></v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-card>
            <v-card
                    class="pa-2"
                    outlined
                    tile
            >
                <v-data-table
                        :headers="headers"
                        :items="users"
                        :hide-actions="true"
                        :pagination.sync="pagination"
                        fix-header>

                    <template v-slot:items="props">
                        <tr>
                            <td>{{ props.item.name }}</td>
                            <td>{{ props.item.createDate }}</td>
                        </tr>
                    </template>

                </v-data-table>
            </v-card>
        </v-card>

    </v-card>
</template>

<script>
    export default {
        name: "VerificationDialog",
        props: {
            entCode: {
                type: String,
                default: '',
            },
        },
        data() {
            return {
                roles: [],
                users: [],
                headers: [
                    {text: '名称', value: 'name'},
                    {text: '创建时间', value: 'createDate'},
                ],
                pagination: {
                    descending: false,
                    page: 1,
                    rowsPerPage: -1,
                    sortBy: null,
                    totalItems: 0
                },
            }
        },
        created() {
            // this.initData();
        },
        methods: {
            confirm() {
                this.$emit("confirm", this.phone, this.code);
            },
            cancel() {
                this.$emit("close");
            },
            initData(entCode) {
                this.roles = [];
                this.users = [];
                this.$fly.get("/api/v1/roles", {entCode}).then(result => {
                    this.roles = result.data;
                });
            },
            loadUsers(role){
                this.users = [];
                this.$fly.get("/api/v1/users",{
                    entCode: role.entCode,
                    roleCode: role.code,
                }).then(result => {
                    this.users = result.data;
                })
            }
        }
    }
</script>

<style scoped>

</style>