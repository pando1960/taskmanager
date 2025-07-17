import { type Task } from "../types/types";

export const validateTask = (task: Task) => {

    let valid = false;

    if (task.title.trim().length !== 0 && task.status) {
        valid = true;
    }

    return valid;
}

