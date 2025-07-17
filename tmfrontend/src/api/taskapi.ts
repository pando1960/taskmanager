import { type Task, type Message } from "../types/types";
import axios from "axios";

const SERVICE_PORT = import.meta.env.VITE_SERVICE_PORT;

export const getTasks = async (): Promise<Task[]> => {

    const response = await axios.get(`http://localhost:${SERVICE_PORT}/api/tasks/all`);

    return response.data;
};

export const removeTask = async (id : number): Promise<Message> => {

    const response = await axios.delete(`http://localhost:${SERVICE_PORT}/api/tasks/remove/${id}`);

    return response.data;
};

export const addTask = async (task : Task): Promise<Message> => {

    const response = await axios.post(`http://localhost:${SERVICE_PORT}/api/tasks/create`,
        task,
        {
            headers: {
                'Content-Type': 'application/json',
            },
        }
    );

    return response.data;
};

export const editTask = async (task : Task): Promise<Message> => {

    const response = await axios.put(`http://localhost:${SERVICE_PORT}/api/tasks/update`,
        task,
        {
            headers: {
                'Content-Type': 'application/json',
            },
        }
    );

    return response.data;
};
