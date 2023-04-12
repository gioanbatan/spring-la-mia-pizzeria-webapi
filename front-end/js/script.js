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

// Pizzas Cards
const pizzaCard = (pizza) => {
    return `
    <div class="row fs-4 mb-3 ms_pizza-card">
    <div class="col-8">
    <div class="row">
    <div class="col-8">
    ${pizza.name}
    </div>
    <div class="col-4 text-end">
    ${pizza.price}&nbsp€
    </div>
    <div class="col-12">
    <h3>Descrizioone:</h3>
    <p>${pizza.description}</p>
    </div>
    </div>
    </div>
    <div class="col-4">
    IMAGE
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
    } else {
        console.log("Error");
    }
}

// APIs
// Get all pizzas
async function getPizzasList() {
    const response = await fetch(API_URL);
    return response;
}

const postPizza = async (jsonData) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData,
    };
    const response = await fetch(API_URL, fetchOptions);
    return response;
};

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

/* Script immediately executed */
pizzaForm.addEventListener('submit', savePizza);
loadData();
