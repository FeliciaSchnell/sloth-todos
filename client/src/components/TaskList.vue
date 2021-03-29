<template>
  <div class="taskList">
    <div class="listToolbar">
      <div>
        <span class="taskInfo">Tasks</span>
        <svg height="24" width="24" viewBox="0 0 24 24" class="newIcon" title="New Task" @click="$emit('newTask')"><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>
      </div>
    </div>
    <div class="listItem" v-for="(item, index) in data" :key="index">
      <input class="checkbox" type="checkbox" v-model="item.completed" :checked="item.completed">
      <input class="taskName" type="text" v-model="item.name" placeholder="Title">
      <svg height="24" width="24" viewBox="0 0 24 24" class="deleteIcon" title="Delete task" @click="$emit('taskDelete', index)"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" fill="#000"/></svg>
    </div>
  </div>
</template>

<script lang="ts">
import { Task } from '@/utils/declarations';
import { Component, Prop, Vue } from 'vue-property-decorator';
@Component
export default class TaskList extends Vue {
    @Prop() data!: Task[];
}
</script>

<style scoped lang="scss">
@import '../scss/variables';
.list {
  display: flex;
  flex-direction: column;
  flex: 1;
  .listToolbar {
    display: flex;
    box-sizing: border-box;
    border-top: 1px solid $action;
    justify-content: space-between;
    align-items: center;
    height: 36px;
    padding: 0 $s 0 $m;
    font-weight: 600;
    color: $action;
    .icon {
      border-radius: 4px;
      fill: $action;
      width: 24px;
      height: 24px;
      padding: 2px;
      margin: 50px;
    }
  }
}
.listItem {
  box-sizing: border-box;
  border-bottom: 1px solid transparentize($primary, .86);
  padding: ($s + $xs) 0;
  .title {
    font-weight: 500;
    margin-bottom: $xs;
  }
}
</style>