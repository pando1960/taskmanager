import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import CssBaseline from "@mui/material/CssBaseline";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

import TaskList from "./components/TaskList";
import { ServiceContext } from "./context/context";

const queryClient = new QueryClient();

const SERVICE_PORT = import.meta.env.VITE_SERVICE_PORT;

function App() {

    return (
            <ServiceContext.Provider value={SERVICE_PORT}>
                <Container maxWidth="xl">
                    <CssBaseline />
                    <AppBar position="static">
                        <Toolbar>
                            <Typography variant="h6">Task List</Typography>
                        </Toolbar>
                    </AppBar>
                    <QueryClientProvider client={queryClient}>
                        <TaskList />
                    </QueryClientProvider>
                </Container>
            </ServiceContext.Provider>
    );
}

export default App;
