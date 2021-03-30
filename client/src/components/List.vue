<template>
  <div class="list">
    <div class="listToolbar">
      <div>
        <span class="todoInfo" v-if="data.length">{{data.length}} Todos</span>
      </div>
      <svg height="24" width="24" viewBox="0 0 24 24" class="icon" title="New todo" @click="newTodo"><path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"/></svg>
    </div>
    <div class="listItem" v-for="(item, index)  in data" :key="index" :class="index === activeItem ? 'isActive' : ''" @click="listItemSelect(index)">
      <div>
        <div class="title">{{item.title}}</div>
        <div class="desc">{{item.description}}</div>
      </div>
      <span class="todoComplete" v-if="item.completed">(completed)</span>
    </div>
    <div class="loading" v-if="!data.length">Loading....</div>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import { Todo } from '@/utils/declarations.ts';

@Component
export default class List extends Vue {
  @Prop() data!: Todo[];
  activeItem: number | null = null;

  listItemSelect(index: number) {
    this.activeItem = index;
    this.$emit('activeTodo', index);
  }
  newTodo() {
    this.$emit('activeTodo', null);
    this.$emit('newTodo');
  }
}
</script>

<style scoped lang="scss">
@import '../scss/variables';

.list {
  display: flex;
  flex-direction: column;
  flex: 1;
  max-width: 300px;
  border-right: 1px solid transparentize($primary, .8);
  user-select: none;
  .listToolbar {
    display: flex;
    box-sizing: border-box;
    border-top: 1px solid $action;
    background-color: $primary;
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
      margin: 4px;
      &:hover {
        background-color: lighten($primary, 10%);
        fill: lighten($action, 10%);
      }
    }
  }
  .loading{
    margin: 0 auto;
    padding: ($s + $xs) $m;
    position: relative;
    &:after {
      content: "";
      background-color: #fff;
      width: 0;
      height: 4px;
      display: block;
      position: absolute;
      right: $m;
      bottom: $m;
      animation: dots 1s steps(4, end) infinite;
    }

  }
}
.listItem {
  box-sizing: border-box;
  border-bottom: 1px solid transparentize($primary, .86);
  display: flex;
  justify-content: space-between;
  align-items: center; 
  padding: ($s + $xs) $m;
  &.isActive { background-color: transparentize($primary, .86) }
  &:hover:not(.isActive) {
    background-color: transparentize($primary, .96);
  }
  .title {
    font-weight: 500;
    margin-bottom: $xs;
  }
  .todoComplete  {
    font-size: 13px;
    font-weight: 500;
  }
  .desc {
    font-size: 13px;
    font-weight: 500;
    line-height: 1.3;
     display: -webkit-box;
     overflow: hidden;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    @supports not (-webkit-line-clamp: 2) {
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
}
@keyframes dots {
  from { width: 18px}
  to { width: 0px; }
}
</style>
