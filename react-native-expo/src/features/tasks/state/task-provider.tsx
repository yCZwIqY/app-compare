import { createContext, useContext, useRef, useState, type PropsWithChildren } from 'react';

import { initialTasks, type Task } from '../model/task';

type TaskStore = {
  tasks: Task[];
  add: (title: string) => void;
  toggle: (id: number) => void;
  remove: (id: number) => void;
};
const TaskContext = createContext<TaskStore | null>(null);

export function TaskProvider({ children }: PropsWithChildren) {
  const [tasks, setTasks] = useState<Task[]>(initialTasks);
  const nextId = useRef(4);
  function add(title: string) {
    const trimmed = title.trim().slice(0, 100);
    if (!trimmed) return;
    const id = nextId.current++;
    setTasks(current => [...current, { id, title: trimmed, done: false }]);
  }
  function toggle(id: number) {
    setTasks(current => current.map(task => task.id === id ? { ...task, done: !task.done } : task));
  }
  function remove(id: number) {
    setTasks(current => current.filter(task => task.id !== id));
  }
  return <TaskContext.Provider value={{ tasks, add, toggle, remove }}>{children}</TaskContext.Provider>;
}

export function useTasks() {
  const store = useContext(TaskContext);
  if (!store) throw new Error('TaskProvider is required');
  return store;
}
