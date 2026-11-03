export type Task = { id: number; title: string; done: boolean };
export const taskFilters = ['전체', '진행 중', '완료'] as const;
export type TaskFilter = typeof taskFilters[number];
export function initialTasks(): Task[] {
  return [
    { id: 1, title: '물 한 잔 마시기', done: true },
    { id: 2, title: '20분 산책하기', done: false },
    { id: 3, title: '새로운 아이디어 기록하기', done: false },
  ];
}
