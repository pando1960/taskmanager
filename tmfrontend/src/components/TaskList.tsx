import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { DataGrid, type GridColDef, type GridCellParams } from "@mui/x-data-grid";
import Snackbar from "@mui/material/Snackbar";
import IconButton from "@mui/material/IconButton";
import DeleteIcon from "@mui/icons-material/Delete";
import Tooltip from "@mui/material/Tooltip";

import { useState } from "react";
import AddTask from "./AddTask";
import EditTask from "./EditTask";

import { getTasks, removeTask } from "../api/taskapi";
import { getStatuses } from "../api/statusapi";

function TaskList() {
    const [open, setOpen] = useState(false);

    const [paginationModel, setPaginationModel] = useState({ pageSize: 10, page: 0 });

    const queryClient = useQueryClient();

    // get tasks and statuses.
    const {
        data: taskData,
        error: taskError,
        isSuccess: taskIsSuccess,
    } = useQuery({
        queryKey: ["tasksQuery"],
        queryFn: getTasks,
    });

    const {
        data: statusData,
        error: statusError,
        isSuccess: statusIsSuccess,
    } = useQuery({
        queryKey: ["statusesQuery"],
        queryFn: getStatuses,
    });

    const { mutate } = useMutation({
        mutationFn: removeTask,
        onSuccess: () => {
            setOpen(true);
            queryClient.invalidateQueries({ queryKey: ["tasksQuery"] });
        },
        onError: (err: string) => {
            console.error(err);
        },
    });

    if (!taskIsSuccess || !statusIsSuccess) {
        return <span>Loading...</span>;
    } else if (taskError || statusError) {
        return <span>Error when fetching tasks...</span>;
    } else {
        // define the grid columns after the data is fetched successfully as the edit column requires the status data to exist.
        const columns: GridColDef[] = [
            { field: "id", headerName: "ID", width: 50 },
            { field: "title", headerName: "Title", width: 300 },
            { field: "description", headerName: "Description", width: 300 },
            {
                field: "status",
                headerName: "Status",
                width: 200,
                valueGetter: (_value, row) => {
                    return row.status.status;
                },
            },
            { field: "due", headerName: "Due", width: 200 },
            {
                field: "edit",
                headerName: "",
                width: 90,
                sortable: false,
                filterable: false,
                disableColumnMenu: true,
                renderCell: (params: GridCellParams) => <EditTask editTask={params.row} statuses={statusData} />,
            },
            {
                field: "delete",
                headerName: "",
                width: 90,
                sortable: false,
                filterable: false,
                disableColumnMenu: true,
                renderCell: (params: GridCellParams) => (
                    <Tooltip title="Delete car">
                        <IconButton
                            aria-label="delete"
                            size="small"
                            onClick={() => {
                                if (window.confirm("Are you sure you want to delete Task: " + params.row.id + "?")) {
                                    mutate(params.row.id);
                                }
                            }}
                        >
                            <DeleteIcon fontSize="small" />
                        </IconButton>
                    </Tooltip>
                ),
            },
        ];

        return (
            <>
                <br /> 

                <AddTask statuses={statusData} />

                <DataGrid
                    rows={taskData}
                    columns={columns}
                    disableRowSelectionOnClick={true}
                    getRowId={(row) => row.id}
                    showToolbar={true}
                    paginationModel={paginationModel}
                    onPaginationModelChange={setPaginationModel}
                />
                <Snackbar open={open} autoHideDuration={2000} onClose={() => setOpen(false)} message="Task deleted" />
            </>
        );
    }
}

export default TaskList;
