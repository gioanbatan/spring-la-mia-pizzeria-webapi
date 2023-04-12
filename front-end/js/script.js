// Global Variables
const API_URL = 'http://localhost:8080/api/v1/pizzas';
// DOM elements
const pizzasList = document.getElementById('pizzas-list');
const pizzaForm = document.getElementById('pizza-form');


// DOM Manipulation
// Pizzas Cards List
const createPizzasList = (data) => {
    let listHtml = "";
    data.forEach(element => {
        listHtml += pizzaCard(element);
    });
    console.log("ListHTML", listHtml);
    return listHtml;
}

// Pizzas Card
const pizzaCard = (pizza) => {
    return `
    <div class="row mb-3 ms_pizza-card">
        <div class="col-8">
            <div class="row">
                <div class="col-8 fs-2">
                ${pizza.name}
                </div>

                <div class="col-4 fs-2 text-end">
                ${pizza.price}&nbsp€
                </div>

                <div class="col-12">
                    <h4>Descrizione:</h4>

                    <p class="fs-4">${pizza.description}</p>
                </div>
            </div>
        </div>

        <div class="col-4 d-flex justify-content-center align-items-center">
        IMAGE
        </div>

        <div class="col-12 text-end mb-3">
            <button data-id="${pizza.id}" class="btn btn-outline-warning fs-5">Elimina</button>
        </div>
    </div>`
}

// Load data function
async function loadData() {
    const response = await getPizzasList();
    if (response.ok) {
        const data = await response.json();
        console.log(createPizzasList(data));
        pizzasList.innerHTML = createPizzasList(data);

        const deleteBtns = document.querySelectorAll('button[data-id]');
        console.log(deleteBtns);
        for (let btn of deleteBtns) {
            btn.addEventListener('click', () => {
                deletePizza(btn.dataset.id);
            })
        }
    } else {
        console.log("Error");
    }
}

/* APIs */
// Get all pizzas
async function getPizzasList() {
    const response = await fetch(API_URL);
    return response;
}

// Post Pizza to API
const postPizza = async (jsonData) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData
    };
    const response = await fetch(API_URL, fetchOptions);
    return response;
};

// Delete pizza
const deletePizzaById = async (id) => {
    const response = await fetch(API_URL + '/' + id, { method: 'DELETE' });
    return response;
};

// Handle submit
const savePizza = async (event) => {
    // Stop normal submit method
    event.preventDefault();
    // Create new Object FormData from target of event
    const formData = new FormData(event.target);
    // Convert entries of FormData Object to common Object
    const formDataObj = Object.fromEntries(formData.entries());
    // Convert Object to a JSON string
    const formDataJson = JSON.stringify(formDataObj);

    // Wait response from API postPizza with parameter fromDataJson
    const response = await postPizza(formDataJson);

    // Parse of response (for console.log to eventually copy)
    const responseBody = await response.json();
    if (response.ok) {
        loadData();
        event.target.reset();
    } else {
        console.log('ERROR');
        console.log(response.status);
        console.log(responseBody);
    }
};

// Handle delete
const deletePizza = async (id) => {
    console.log("Test delete", id);
    const response = await deletePizzaById(id);
    if (response.ok) {
        loadData();
    } else {
        console.log("Error", response.status);
    }
}

/* Script immediately executed */
pizzaForm.addEventListener('submit', savePizza);
loadData();
