import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import { type PickerValue } from "@mui/x-date-pickers/internals";
import { type SelectChangeEvent } from "@mui/material/Select";
import Button from "@mui/material/Button";

import { format } from "date-fns";

import "dayjs/locale/en-gb";
import dayjs, { Dayjs } from "dayjs";

import { type Task, type Status } from "../types/types";
import { useState } from "react";
import { addTask } from "../api/taskapi";
import { useMutation, useQueryClient } from "@tanstack/react-query";

import TaskCommonContent from "./TaskCommonContent";

import { validateTask } from "../validator/validateTask";

const AddTask = (props: { statuses: Status[] }) => {
    const queryClient = useQueryClient();

    const { mutate } = useMutation({
        mutationFn: addTask,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["tasksQuery"] });
        },
        onError: (err: string) => {
            console.error(err);
        },
    });

    const [open, setOpen] = useState(false);

    const [task, setTask] = useState<Task>({
        title: "",
        description: "",
        status: props.statuses.slice(0, 1)[0],
        due: format(dayjs().toDate(), "EEE dd MMM yyyy HH:mm"),
    });

    const [titleError, setTitleError] = useState(false);

    const [dueDate, setDueDate] = useState<Dayjs | null>(dayjs(task.due.substring(4), "DD MMM YYYY HH:mm"));

    // open the modal form
    const handleClickOpen = () => {
        setOpen(true);
    };

    // close the modal form
    const handleClose = () => {
        setTitleError(false);
        setOpen(false);
    };

    // handle user entered data changes.
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setTask({ ...task, [event.target.name]: event.target.value });

        if (event.target.validity.valid) {
            setTitleError(false);
        } else {
            setTitleError(true);
        }
    };

    const handleOnBlur = (event: React.FocusEvent<HTMLInputElement>) => {
        if (event.target.validity.valid) {
            setTitleError(false);
        } else {
            setTitleError(true);
        }
    };

    // handle date picker selection.
    const handleDateChange = (newValue: PickerValue) => {
        setDueDate(newValue);
        setTask({ ...task, due: format(dayjs(newValue).toDate(), "EEE dd MMM yyyy HH:mm") });
        console.log("New date and time: " + format(dayjs(newValue).toDate(), "EEE dd MMM yyyy HH:mm"));
    };

    // handle status drop-down selection.
    const handleStatusChange = (event: SelectChangeEvent<HTMLSelectElement>) => {
        const chosenStatus = props.statuses.filter((status: Status) => status.id === Number(event.target.value))[0];
        setTask({ ...task, status: chosenStatus });
        console.log("Chosen status is: " + chosenStatus.id + " - " + chosenStatus.status);
    };

    // validate, save task and close form.
    const handleSave = () => {
        if (validateTask(task)) {
            mutate(task);

            setTask({
                title: "",
                description: "",
                status: props.statuses.slice(0, 1)[0],
                due: format(dayjs().toDate(), "EEE dd MMM yyyy HH:mm"),
            });

            handleClose();
        }
    };

    return (
        <>
            <Button onClick={handleClickOpen}>Add Task</Button>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>New Task</DialogTitle>

                <TaskCommonContent
                    task={task}
                    statuses={props.statuses}
                    dueDate={dueDate}
                    handleChange={handleChange}
                    handleDateChange={handleDateChange}
                    handleStatusChange={handleStatusChange}
                    titleError={titleError}
                    handleOnBlur={handleOnBlur}
                />

                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSave}>Save</Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default AddTask;
