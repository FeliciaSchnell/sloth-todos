<template>
  <div class="inputsection">
      <input class="title" type="text" v-model="item.title" placeholder="Title" @keypress="$emit('update')" />
      <textarea class="description" v-model="item.description" placeholder="Description" @keypress="$emit('update')"></textarea>
      <TaskList :data="item.tasks" v-on:newTask="newTask" v-on:taskUpdate="$emit('update')" v-on:taskDelete="deleteTask($event)" />
  </div>
</template>

<script lang="ts">
import { Todo } from '@/utils/declarations';
import { Component, Prop, Vue } from 'vue-property-decorator';
import TaskList from '@/components/TaskList.vue';
@Component({
  components: {
    TaskList
  },
})
export default class InputSection extends Vue {
    @Prop() item!: Todo;

    newTask() {
        console.log('newTask');
        this.item.tasks.push(
        {
            id: -1,
            todoId: -1,
            memberId: -1,
            name: "",
            completed: false,
        });
    }
    deleteTask(index: number) {
        console.log('deleteTask');
        this.$emit('taskDelete', this.item.tasks[index]);
        this.item.tasks.splice(index, 1);
    }
}   
</script>

<style scoped lang="scss">
@import '../scss/variables';
    .inputsection {
        display: flex;
        flex-direction: column;
        flex:1;
        padding: $xl 0 0 $xxl;
    }
    .title {
        height: 64px;
        border: none;
        border-bottom: 1px solid $action;
        font-size: 36px;
        margin-right: 100px;
    }
    .description {
        border: 0,5px solid;
        padding: $xs $xxl ($xxl * 2) $xs ;
        margin: 30px 150px 0 0;
    }
    .title:focus,
    .description:focus {
        outline: none;
    }
</style>
