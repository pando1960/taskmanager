import { type Task, type Status } from "../types/types";
import { type PickerValue } from "@mui/x-date-pickers/internals";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import DialogContent from "@mui/material/DialogContent";
import Select, { type SelectChangeEvent } from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';

import "dayjs/locale/en-gb";
import dayjs from "dayjs";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';



type DialogFormProps = {
    task: Task;
    statuses: Status[];
    dueDate: dayjs.Dayjs | null;
    handleChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleDateChange: (newValue: PickerValue) => void;
    handleStatusChange: (event: SelectChangeEvent<HTMLSelectElement>) => void;
    titleError: boolean;
    handleOnBlur: (event: React.FocusEvent<HTMLInputElement>) => void;
};

function TaskCommonContent({ task, statuses, dueDate, handleChange, handleDateChange, handleStatusChange, titleError, handleOnBlur }: DialogFormProps) {

    return (
        <>
            <DialogContent>
                <Stack spacing={2} mt={1}>
                <TextField  label="Title" 
                            name="title" 
                            value={task.title} 
                            required 
                            error={titleError} 
                            helperText={titleError ? "Please enter Title" : ""} 
                            onChange={handleChange} 
                            onBlur={handleOnBlur} 
                />
                <TextField label="Description" name="description" value={task.description} onChange={handleChange} />
  
                <FormControl fullWidth>
                    <InputLabel id="select-status-label">Status</InputLabel>
                    <Select
                        labelId="Statuses"
                        id="statuses"
                        name="statuses"
                        value={task.status.id}
                        label="Status"
                        onChange={handleStatusChange}
                    >
                        {statuses.map((status: Status) => (
                            <MenuItem value={status.id}>{status.status}</MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="en-gb">
                    <DateTimePicker
                        label="Due date and time"
                        views={["year", "month", "day", "hours", "minutes"]}
                        value={dueDate}
                        defaultValue={dayjs()}
                        onChange={(newValue) => handleDateChange(newValue)}
                    />
                </LocalizationProvider>
                </Stack>
            </DialogContent>
        </>
    );
}

export default TaskCommonContent;
