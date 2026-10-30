const API_URL = "http://localhost:8081/api/plants";

export const getAllPlants = async () => {
    const response = await fetch(API_URL);

    if (!response.ok) {
        throw new Error("Failed to fetch plants");
    }

    return await response.json();
};