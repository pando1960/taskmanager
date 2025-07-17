import { type Status } from "../types/types";
import axios from "axios";

export const getStatuses = async (): Promise<Status[]> => {

    const SERVICE_PORT = import.meta.env.VITE_SERVICE_PORT;

    const response = await axios.get(`http://localhost:${SERVICE_PORT}/api/statuses/all`);

    return response.data;
};