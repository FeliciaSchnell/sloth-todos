export type Todo = {
    description: string;
    id: number;
    title: string;
    tasks: Task[];
}
export type Task = {
    id: number;
    todoId: number;
    memberId: number;
    name: string;
    completed: boolean;
}