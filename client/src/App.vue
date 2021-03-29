<template>
  <div id="app">
    <div id="header">
      <img class="logo" src="@/assets/sloth.svg" />
      <div class="logoTxt">Sloth Todos</div>
    </div>
    <div class="lowerSection">
      <List :data="listData" v-on:activeTodo="getActiveTodo($event)" v-on:newTodo="newTodo"/>
      <InputSection v-if="currentTodo" :item="currentTodo" v-on:update="inputSectionUpdate" v-on:taskDelete="setTask('DELETE', $event)"/>
      <div class="placeholder" v-if="!currentTodo">
        <img class="logo" src="@/assets/sloth.svg" />
        <div class="logoTxt">Sloth Todos</div>
      </div>
    </div>
    <div :class="{'actionBtn delete': true, 'isActive': deleteActive }" @click="setTodo('DELETE', currentTodo)" title="Delete todo">
      <svg height="24" width="24" viewBox="0 0 24 24"><path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z" fill="#fff"/></svg>
    </div>
    <div :class="{'actionBtn save': true, 'isActive': saveActive }" @click="setTodo(currentTodo.id < 0 ? 'POST' : 'PUT', currentTodo)" title="Save todo">
      <svg height="24" width="24" viewBox="0 0 24 24"><path d="M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z" fill="#fff"/></svg>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import { Todo } from '@/utils/declarations.ts';
import { Task } from '@/utils/declarations.ts';
import  List from '@/components/List.vue'
import  InputSection from '@/components/Todo.vue'
@Component({
  components: {
    List,
    InputSection
  },
})
export default class App extends Vue {
  url = `http://localhost:8088`;
  listData: Todo[] = [];
  currentTodo: Todo | null = null;
  saveActive = false;
  deleteActive = false;

  created() {
    this.callAPI(`${this.url}/todos`).then((data: Todo[]) => this.listData = data);
  }

  async callAPI(goToURL: string): Promise<Todo[]> {
    return await fetch(goToURL, {mode: 'cors'})
      .then((resp) => resp.json())
      .catch(function(error) { console.log(error); }
    );
  }

  setTodo(request: string, todo: Todo) {
    console.log(request, todo);
    const xhr = new XMLHttpRequest();
    const data = {
        'title': todo.title,
        'description': todo.description,
        'tasks': todo.tasks,
    }
    const setURL = todo.id < 0 ? `${this.url}/todos` : `${this.url}/todos/${todo.id}`;

    if(request === 'DELETE') {
      this.currentTodo = null;
      this.saveActive = false;
      this.deleteActive = false;
    }
    xhr.addEventListener('load', this.endload);
    xhr.open(request, setURL);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));
  }

  setTask(request: string, task: Task) {
    console.log(request, task);
    const xhr = new XMLHttpRequest();
    const data = {
        'name': task.name,
        'completed': task.completed,
    }
    const setURL = task.id < 0 ? `${this.url}/tasks` : `${this.url}/tasks/${task.id}`;

    if(request === 'DELETE') {
      this.saveActive = false;
      this.deleteActive = false;
    }
    xhr.addEventListener('load', this.endload);
    xhr.open(request, setURL);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));
  }

  endload() {
    console.log('end load');
    this.callAPI(`${this.url}/todos`).then((data: Todo[]) => this.listData = data);
  }

  getActiveTodo(item: number) {
    console.log('Get active');
    this.currentTodo = this.listData[item];
    this.deleteActive = this.currentTodo?.id ? true : false;
  }

  inputSectionUpdate() {
    this.saveActive = true;
  }

  newTodo() {
    console.log('new');
    this.currentTodo = {
        id: -1,
        title: "",
        description: "",
        tasks: [],
    };
  } 
}
</script>

<style lang="scss">
@import './scss/variables';
body, input, textarea, button { font-family: Avenir, Helvetica, Arial, sans-serif; }
body { margin: 0; }
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: $txtColor;
  height: 100vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
#header {
  background: $primary;
  display: flex;
  align-items: center;
  height: 64px;
  padding: 0 $m;
  .logo {
    width: 48px;
    margin-right: $m;
  }

}
.logoTxt {
  font-size: 32px;
  font-weight: 900;
  color: $action;
  letter-spacing: -.08333rem;
}
.lowerSection {
  flex: 1;
  display: flex;
}
.placeholder {
  align-items: center;
  justify-content: center;
  display: flex;
  flex-direction: column;
  background-color: lighten($action, 40%);
  flex: 1;
  img {
    width: 128px;
  }
}
.actionBtn {
  border-radius: 50%;
  box-shadow:  0 3px 5px -1px rgba(0,0,0,.2), 0 6px 10px 0 rgba(0,0,0,.14), 0 1px 18px 0 rgba(0,0,0,.12), 0 0 2px hsla(50, 100%, 50%, 0.5) inset;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: $action;
  width: 48px;
  height: 48px;
  transform: translateY(200%);
  font-weight: 900;
  color: #fff;
  user-select: none;
  transition-duration: .5s;
  transition-timing-function: cubic-bezier(0.075, 0.82, 0.165, 1);
  position: fixed;
  bottom: $xl;
  &.save { right: $xl; }
  &.delete { right: $xl + 48px + $l; }
  &.isActive {
    transform: translateY(0);
  }
}
</style>
