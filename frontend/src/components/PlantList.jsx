import { useEffect, useState } from "react";
import { getAllPlants } from "../services/plantService";

function PlantList() {
    const [plants, setPlants] = useState([]);
    const [search, setSearch] = useState("");
    const [category, setCategory] = useState("All");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getAllPlants()
            .then(data => setPlants(data))
            .catch(error => console.error(error))
            .finally(() => setLoading(false));
    }, []);

    const categories = [
        "All",
        ...new Set(plants.map(plant => plant.category))
    ];

    const filteredPlants = plants.filter(plant => {
        const matchesSearch = plant.name
            .toLowerCase()
            .includes(search.toLowerCase());

        const matchesCategory =
            category === "All" || plant.category === category;

        return matchesSearch && matchesCategory;
    });

    if (loading) return <h2>Loading plants...</h2>;

    return (
        <div className="nursery-page">

            <h1>GreenRoots Nursery</h1>
            <p className="subtitle">
                Find the perfect plants for your home and garden.
            </p>

            <div className="filter-section">

                <input
                    type="text"
                    placeholder="Search plants..."
                    value={search}
                    onChange={e => setSearch(e.target.value)}
                />

                <select
                    value={category}
                    onChange={e => setCategory(e.target.value)}
                >
                    {categories.map(cat => (
                        <option key={cat} value={cat}>
                            {cat}
                        </option>
                    ))}
                </select>

            </div>

            <div className="plant-container">

                {filteredPlants.length > 0 ? (
                    filteredPlants.map(plant => (
                        <div className="plant-card" key={plant.id}>

                            <img
                                src={plant.imageUrl}
                                alt={plant.name}
                            />

                            <h2>{plant.name}</h2>

                            <p>{plant.category}</p>

                            <p>Price: ₹{plant.price}</p>

                            <p>Available: {plant.quantity}</p>

                            <p>{plant.description}</p>

                        </div>
                    ))
                ) : (
                    <h2>No plants found!</h2>
                )}

            </div>

        </div>
    );
}

export default PlantList;