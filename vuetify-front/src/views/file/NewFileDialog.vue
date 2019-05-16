<template>
    <v-card>
        <v-card-title>
            <span class="headline">User Profile</span>
        </v-card-title>
        <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                    <v-flex xs12 sm6 md4>
                        <v-text-field label="Legal first name*" required></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-text-field label="Legal middle name"
                                      hint="example of helper text only on focus"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-text-field
                                label="Legal last name*"
                                hint="example of persistent helper text"
                                persistent-hint
                                required
                        ></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field label="Email*" required></v-text-field>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field label="Password*" type="password" required></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6>
                        <v-select
                                :items="['0-17', '18-29', '30-54', '54+']"
                                label="Age*"
                                required
                        ></v-select>
                    </v-flex>
                    <v-flex xs12 sm6>
                        <v-autocomplete
                                :items="['Skiing', 'Ice hockey', 'Soccer', 'Basketball', 'Hockey', 'Reading', 'Writing', 'Coding', 'Basejump']"
                                label="Interests"
                                multiple
                        ></v-autocomplete>
                    </v-flex>
                </v-layout>
            </v-container>
            <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click="$emit('close')">Close</v-btn>
            <v-btn color="blue darken-1" flat @click="$emit('save',formData)">Save</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "NewFileDialog",
        data() {
            return {
                formData: {
                    a: 'b'
                }
            }
        }
    }
</script>

<style scoped>

</style>