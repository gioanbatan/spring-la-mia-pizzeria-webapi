// Global Variables
const API_URL = 'http://localhost:8080/api/v1/pizzas';
// DOM elements
const pizzasList = document.getElementById('pizzas-list');




// DOM Manipulation
// Pizzas Cards List
const createPizzasList = (data) => {
    let listHtml = "";
    data.forEach(element => {
        listHtml += `<div class="row">
        <div class="col-12">
        ${element.name}
        </div>
        </div>`
    });
    console.log("ListHTML", listHtml);
    return listHtml;
}

// Pizzas Cards


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
loadData();
