export type Status = {
    id: number;
    status: string;
}

export type Task = {
    id?: number;
    title: string;
    description: string;
    status: Status;
    due: string;
//    dueDate?: Date |  null;
}

export type Message = {
    message: string;
}